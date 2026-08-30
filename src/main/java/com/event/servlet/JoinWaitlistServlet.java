package com.event.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.event.dao.WaitlistDAO;

@WebServlet("/joinWaitlist")
public class JoinWaitlistServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        WaitlistDAO waitlistDao = new WaitlistDAO();

        if (!waitlistDao.isUserOnWaitlist(eventId, username)) {
            waitlistDao.addToWaitlist(eventId, username);
            response.sendRedirect("eventList.jsp?msg=Added to waitlist");
        } else {
            response.sendRedirect("eventList.jsp?msg=You are already on the waitlist");
        }
    }
}