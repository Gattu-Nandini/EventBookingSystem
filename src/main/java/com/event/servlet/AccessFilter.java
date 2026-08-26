package com.event.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public class AccessFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        boolean isLoginPage = uri.endsWith("login.jsp") || uri.endsWith("/login")
                || uri.endsWith("signup.jsp") || uri.endsWith("/signup");
        boolean isAdminPage = uri.contains("adminDashboard.jsp") || uri.contains("/addEvent");
        boolean isStudentBookingPage = uri.contains("/bookEvent") || uri.contains("/cancelBooking") || uri.contains("myBookings.jsp");

        boolean loggedIn = (session != null && session.getAttribute("username") != null);
        String role = loggedIn ? (String) session.getAttribute("role") : null;

        if (isLoginPage) {
            chain.doFilter(req, res); // always allow login page
            return;
        }

        if (!loggedIn) {
            response.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        if (isAdminPage && !"admin".equals(role)) {
            response.sendRedirect(contextPath + "/eventList.jsp?msg=Access denied");
            return;
        }

        if (isStudentBookingPage && !"student".equals(role)) {
            response.sendRedirect(contextPath + "/adminDashboard.jsp?msg=Access denied");
            return;
        }

        chain.doFilter(req, res); // allowed, continue normally
    }

    public void init(FilterConfig config) {}
    public void destroy() {}
}