<%@ page import="java.util.List" %>
<%@ page import="com.event.model.Booking" %>
<%@ page import="com.event.model.Event" %>
<%@ page import="com.event.dao.BookingDAO" %>
<%@ page import="com.event.dao.EventDAO" %>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>My Bookings</h2>
        <a href="eventList.jsp" class="btn btn-outline-secondary btn-sm">Back to Events</a>
    </div>

    <%
        if (request.getParameter("msg") != null) {
    %>
        <div class="alert alert-info"><%= request.getParameter("msg") %></div>
    <%
        }
    %>

    <table class="table table-bordered bg-white">
        <thead class="table-dark">
            <tr>
                <th>Event</th>
                <th>Venue</th>
                <th>Date</th>
                <th>Booked At</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            String username = (String) session.getAttribute("username");
            BookingDAO bookingDao = new BookingDAO();
            EventDAO eventDao = new EventDAO();
            List<Booking> bookings = bookingDao.getBookingsByUser(username);

            for (Booking b : bookings) {
                Event e = eventDao.getEventById(b.getEventId());
        %>
            <tr>
                <td><%= e.getTitle() %></td>
                <td><%= e.getVenue() %></td>
                <td><%= e.getEventDate() %></td>
                <td><%= b.getBookedAt() %></td>
                <td>
                    <a href="cancelBooking?eventId=<%= e.getId() %>" class="btn btn-sm btn-danger"
                       onclick="return confirm('Cancel this booking?');">Cancel</a>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>