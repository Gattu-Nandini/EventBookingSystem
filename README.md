# Event Booking System

A web app for managing seminar/event registrations. Students can browse events and book seats, admins can create and manage events. Built with Java Servlets, JSP, and MySQL.

## Why this project

Colleges/departments usually track event registrations through Google Forms or a physical sign-up sheet, which makes it hard to know in real time how many seats are left, and it's easy to double-book a seat by accident. This app handles that with a simple seat-counter that's checked at the database level before confirming a booking, so two people can't take the same last seat at the same time.

## Features

**Student**
- Sign up / log in (passwords hashed with BCrypt, never stored in plain text)
- View all upcoming events with live seat availability
- Book a seat (blocked once an event is full)
- Cannot book the same event twice
- Join a waitlist once an event is full — if someone with a booking cancels, the seat is transferred automatically to whoever's been waiting longest
- View and cancel their own bookings

**Admin**
- Log in with a separate admin account
- View all events with total/booked/available seat counts
- Add new events
- Delete events (also removes any bookings and waitlist entries tied to that event)

**General**
- Role-based access — students can't reach admin pages and vice versa, enforced by a servlet filter, not just hidden links
- Session-based login

## Tech stack

- Java (Servlets + JSP)
- Apache Tomcat 9
- MySQL (via XAMPP)
- JDBC (PreparedStatement for all queries)

## How it's structured

```
com.event.model      - Event, Booking, User (plain data classes)
com.event.dao        - EventDAO, BookingDAO, UserDAO (all SQL lives here)
com.event.util       - DBConnection (single shared connection method)
com.event.servlet    - one servlet per action (login, signup, book, cancel, etc.)
webapp/*.jsp         - pages (login, signup, eventList, myBookings, adminDashboard, addEvent)
```

The DAO layer is the only place that talks to the database. Servlets call the DAO and decide what page to send the user to next; JSPs just display data, they don't run business logic.

### Seat booking logic

The part worth explaining if anyone asks about it: booking a seat isn't a "check available seats, then insert" — that has a race condition if two requests hit at the same time. Instead, the seat count update is one SQL statement:

```sql
UPDATE events SET booked_seats = booked_seats + 1
WHERE id = ? AND booked_seats < total_seats
```

If the event is already full, this query updates zero rows, and the code checks that before recording the booking. So the seat limit is enforced by the database itself, not by application logic that could get raced.

### Waitlist + cancellation

When a full event has a cancellation, the freed seat isn't just added back to the available count — it's handed directly to whoever joined the waitlist first (checked by earliest `joined_at` timestamp). The booking is created for that student and they're removed from the waitlist, all before the seat count is touched. This avoids a window where someone else browsing the page could grab the seat ahead of the person who'd actually been waiting for it.

If nobody is on the waitlist, the seat count is simply decremented and the seat becomes bookable again.

## Database schema

```sql
CREATE DATABASE eventdb;
USE eventdb;

CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE,
  password VARCHAR(50),
  role VARCHAR(20)
);

CREATE TABLE events (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(150),
  description VARCHAR(500),
  event_date DATE,
  venue VARCHAR(100),
  total_seats INT,
  booked_seats INT DEFAULT 0
);

CREATE TABLE bookings (
  id INT PRIMARY KEY AUTO_INCREMENT,
  event_id INT,
  username VARCHAR(50),
  booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE waitlist (
  id INT PRIMARY KEY AUTO_INCREMENT,
  event_id INT,
  username VARCHAR(50),
  joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (event_id) REFERENCES events(id)
);
```

The admin account isn't created through the signup form (signup always assigns the `student` role), so insert it manually. Since passwords are hashed with BCrypt, you can't just type `admin123` directly into the `password` column — generate a hash first (see note below) and insert that:

```sql
INSERT INTO users (username, password, role) VALUES ('admin', '<bcrypt-hash-here>', 'admin');
```

A quick way to generate a hash: run `BCrypt.hashpw("admin123", BCrypt.gensalt())` from a throwaway `main()` method once, copy the output, and use it in the query above.

## Running it locally

1. Import the project into Eclipse as a Dynamic Web Project, target Tomcat 9
2. Start MySQL (via XAMPP or standalone) and run the schema above in phpMyAdmin
3. Add the MySQL Connector/J jar to `WEB-INF/lib`, then add it to the project's Build Path
4. Update the DB credentials in `com.event.util.DBConnection` if yours differ from `root` / no password
5. Run on Tomcat
6. Log in as `admin` / `admin123`, or sign up as a new student

## Known limitations

- No pagination — the event list just loads everything, would need work if there were hundreds of events.
- No email/notification when a booking is confirmed, cancelled, or when a waitlisted seat is transferred.
- Admin accounts have to be inserted directly into the database — there's no admin-creation flow in the app itself, which is intentional (self-service admin signup would be a security problem) but worth knowing before you look for one.
