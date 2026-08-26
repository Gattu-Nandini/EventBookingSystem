package com.event.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.event.dao.BookingDAO;
import com.event.dao.EventDAO;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        BookingDAO bookingDao = new BookingDAO();
        EventDAO eventDao = new EventDAO();

        boolean deleted = bookingDao.deleteBooking(eventId, username);

        if (deleted) {
            eventDao.decrementBookedSeats(eventId); // free up the seat
            response.sendRedirect("myBookings.jsp?msg=Booking cancelled");
        } else {
            response.sendRedirect("myBookings.jsp?msg=Booking not found");
        }
    }
}