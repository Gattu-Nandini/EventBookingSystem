package com.event.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.event.dao.EventDAO;
import com.event.model.Event;

@WebServlet("/addEvent")
public class AddEventServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Event e = new Event();
        e.setTitle(request.getParameter("title"));
        e.setDescription(request.getParameter("description"));
        e.setEventDate(request.getParameter("eventDate"));
        e.setVenue(request.getParameter("venue"));
        e.setTotalSeats(Integer.parseInt(request.getParameter("totalSeats")));

        EventDAO dao = new EventDAO();
        dao.addEvent(e);

        response.sendRedirect("adminDashboard.jsp");
    }
}