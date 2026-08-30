package com.event.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.event.dao.BookingDAO;
import com.event.dao.EventDAO;
import com.event.dao.WaitlistDAO;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        BookingDAO bookingDao = new BookingDAO();
        EventDAO eventDao = new EventDAO();
        WaitlistDAO waitlistDao = new WaitlistDAO();

        boolean deleted = bookingDao.deleteBooking(eventId, username);

        if (deleted) {
            // Check if someone is waiting for this exact seat
            String nextInLine = waitlistDao.getFirstInLine(eventId);

            if (nextInLine != null) {
                // Hand the freed seat straight to them — booked_seats count stays the same
                bookingDao.addBooking(eventId, nextInLine);
                waitlistDao.removeFromWaitlist(eventId, nextInLine);
            } else {
                // Nobody waiting — actually free up the seat count
                eventDao.decrementBookedSeats(eventId);
            }

            response.sendRedirect("myBookings.jsp?msg=Booking cancelled");
        } else {
            response.sendRedirect("myBookings.jsp?msg=Booking not found");
        }
    }
}