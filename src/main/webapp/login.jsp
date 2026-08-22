<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container" style="max-width: 400px; margin-top: 100px;">
    <div class="card p-4 shadow-sm">
        <h3 class="mb-3 text-center">Event Booking System - Login</h3>
        <%
            if (request.getParameter("error") != null) {
        %>
            <div class="alert alert-danger">Invalid username or password</div>
        <%
            }
        %>
        <form action="login" method="post">
            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" class="form-control" name="username" required/>
            </div>
            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" class="form-control" name="password" required/>
            </div>
            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>
    </div>
</div>
</body>
</html>