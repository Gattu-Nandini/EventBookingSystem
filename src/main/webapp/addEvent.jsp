<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container" style="max-width: 500px; margin-top: 60px;">
    <div class="card p-4 shadow-sm">
        <h3 class="mb-3">Add New Event</h3>
        <form action="addEvent" method="post">
            <div class="mb-3">
                <label class="form-label">Title</label>
                <input type="text" class="form-control" name="title" required/>
            </div>
            <div class="mb-3">
                <label class="form-label">Description</label>
                <textarea class="form-control" name="description" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Date</label>
                <input type="date" class="form-control" name="eventDate" required/>
            </div>
            <div class="mb-3">
                <label class="form-label">Venue</label>
                <input type="text" class="form-control" name="venue" required/>
            </div>
            <div class="mb-3">
                <label class="form-label">Total Seats</label>
                <input type="number" class="form-control" name="totalSeats" min="1" required/>
            </div>
            <button type="submit" class="btn btn-success w-100">Add Event</button>
            <a href="adminDashboard.jsp" class="btn btn-secondary w-100 mt-2">Cancel</a>
        </form>
    </div>
</div>
</body>
</html>