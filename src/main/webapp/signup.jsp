<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container" style="max-width: 400px; margin-top: 100px;">
    <div class="card p-4 shadow-sm">
        <h3 class="mb-3 text-center">Student Signup</h3>
        <%
            if (request.getParameter("error") != null) {
        %>
            <div class="alert alert-danger"><%= request.getParameter("error") %></div>
        <%
            }
        %>
        <form action="signup" method="post">
            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" class="form-control" name="username" required/>
            </div>
            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" class="form-control" name="password" required/>
            </div>
            <button type="submit" class="btn btn-primary w-100">Sign Up</button>
        </form>
        <a href="login.jsp" class="btn btn-link mt-2">Already have an account? Login</a>
    </div>
</div>
</body>
</html>