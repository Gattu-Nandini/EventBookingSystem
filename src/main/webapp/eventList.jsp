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
        <h2>Available Events</h2>
        <div>
            <span class="me-2">Welcome, <%= session.getAttribute("username") %></span>
            <a href="logout" class="btn btn-outline-danger btn-sm">Logout</a>
        </div>
    </div>

    <%
        if (request.getParameter("msg") != null) {
            String msg = request.getParameter("msg");
    %>
        <div class="alert alert-info"><%= msg %></div>
    <%
        }
    %>

    <table class="table table-bordered table-hover bg-white">
        <thead class="table-dark">
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Date</th>
                <th>Venue</th>
                <th>Seats Available</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            EventDAO dao = new EventDAO();
            List<Event> events = dao.getAllEvents();
            for (Event e : events) {
                int available = e.getAvailableSeats();
        %>
            <tr>
                <td><%= e.getTitle() %></td>
                <td><%= e.getDescription() %></td>
                <td><%= e.getEventDate() %></td>
                <td><%= e.getVenue() %></td>
                <td><%= available %> / <%= e.getTotalSeats() %></td>
                <td>
                    <% if (available > 0) { %>
                        <a href="bookEvent?id=<%= e.getId() %>" class="btn btn-sm btn-success">Book</a>
                    <% } else { %>
                        <span class="badge bg-secondary">Full</span>
                    <% } %>
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