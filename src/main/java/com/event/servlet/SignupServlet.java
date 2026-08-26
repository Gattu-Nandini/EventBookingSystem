package com.event.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.event.dao.UserDAO;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        boolean success = dao.registerUser(username, password);

        if (success) {
            response.sendRedirect("login.jsp?msg=Account created! Please log in.");
        } else {
            response.sendRedirect("signup.jsp?error=Username already taken");
        }
    }
}