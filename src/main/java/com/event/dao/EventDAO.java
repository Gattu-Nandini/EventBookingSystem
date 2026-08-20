package com.event.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.event.model.Event;

public class EventDAO {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/eventdb", "root", "");
    }

    // Admin: create a new event
    public void addEvent(Event e) {
        String sql = "INSERT INTO events (title, description, event_date, venue, total_seats) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getTitle());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getEventDate());
            ps.setString(4, e.getVenue());
            ps.setInt(5, e.getTotalSeats());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("DB error: " + ex.getMessage(), ex);
        }
    }

    // Everyone: list all events
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("DB error: " + ex.getMessage(), ex);
        }
        return list;
    }

    // Get one event by id (needed before booking, to check seats)
    public Event getEventById(int id) {
        String sql = "SELECT * FROM events WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("DB error: " + ex.getMessage(), ex);
        }
    }

    // Increments booked_seats by 1 — only if seats are still available
    public boolean incrementBookedSeats(int eventId) {
        String sql = "UPDATE events SET booked_seats = booked_seats + 1 WHERE id = ? AND booked_seats < total_seats";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // true only if the seat was actually available and updated
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("DB error: " + ex.getMessage(), ex);
        }
    }

    private Event mapRow(ResultSet rs) throws SQLException {
        Event e = new Event();
        e.setId(rs.getInt("id"));
        e.setTitle(rs.getString("title"));
        e.setDescription(rs.getString("description"));
        e.setEventDate(rs.getString("event_date"));
        e.setVenue(rs.getString("venue"));
        e.setTotalSeats(rs.getInt("total_seats"));
        e.setBookedSeats(rs.getInt("booked_seats"));
        return e;
    }
}