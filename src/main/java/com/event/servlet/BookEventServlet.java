package com.event.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.event.dao.EventDAO;
import com.event.dao.BookingDAO;

@WebServlet("/bookEvent")
public class BookEventServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        BookingDAO bookingDao = new BookingDAO();
        EventDAO eventDao = new EventDAO();

        // 1. Check if this user already booked this event
        if (bookingDao.hasUserBooked(eventId, username)) {
            response.sendRedirect("eventList.jsp?msg=You have already booked this event");
            return;
        }

        // 2. Try to reserve a seat (atomic check-and-update in the DB)
        boolean seatReserved = eventDao.incrementBookedSeats(eventId);

        if (seatReserved) {
            // 3. Only record the booking if a seat was actually reserved
            bookingDao.addBooking(eventId, username);
            response.sendRedirect("eventList.jsp?msg=Booking confirmed!");
        } else {
            response.sendRedirect("eventList.jsp?msg=Sorry, this event is now full");
        }
    }
}