<%@ page import="java.util.List" %>
<%@ page import="com.event.model.Event" %>
<%@ page import="com.event.dao.EventDAO" %>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Admin Dashboard</h2>
        <div>
            <span class="me-2">Welcome, <%= session.getAttribute("username") %></span>
            <a href="logout" class="btn btn-outline-danger btn-sm">Logout</a>
        </div>
        <%
    if (request.getParameter("msg") != null) {
%>
    <div class="alert alert-info"><%= request.getParameter("msg") %></div>
<%
    }
%>
    </div>

    <a href="addEvent.jsp" class="btn btn-success mb-3">+ Add New Event</a>

    <table class="table table-bordered table-hover bg-white">
        <thead class="table-dark">
            <tr>
                <tr>
    <th>Title</th>
    <th>Date</th>
    <th>Venue</th>
    <th>Total Seats</th>
    <th>Booked</th>
    <th>Available</th>
    <th>Action</th>
</tr>
            </tr>
        </thead>
        <tbody>
        <%
            EventDAO dao = new EventDAO();
            List<Event> events = dao.getAllEvents();
            for (Event e : events) {
        %>
            <tr>
    <td><%= e.getTitle() %></td>
    <td><%= e.getEventDate() %></td>
    <td><%= e.getVenue() %></td>
    <td><%= e.getTotalSeats() %></td>
    <td><%= e.getBookedSeats() %></td>
    <td><%= e.getAvailableSeats() %></td>
    <td>
        <a href="deleteEvent?id=<%= e.getId() %>" class="btn btn-sm btn-danger"
           onclick="return confirm('Delete this event? All bookings for it will also be removed.');">Delete</a>
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