package com.event.servlet;

import java.io.IOException;
import com.event.dao.WaitlistDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.event.dao.EventDAO;
import com.event.dao.BookingDAO;

@WebServlet("/deleteEvent")
public class DeleteEventServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("id"));

        BookingDAO bookingDao = new BookingDAO();
        EventDAO eventDao = new EventDAO();

        bookingDao.deleteBookingsByEvent(eventId); // clear related bookings first
        WaitlistDAO waitlistDao = new WaitlistDAO();
        waitlistDao.deleteWaitlistByEvent(eventId);
        eventDao.deleteEvent(eventId); // then delete the event itself

        response.sendRedirect("adminDashboard.jsp?msg=Event deleted");
    }
}