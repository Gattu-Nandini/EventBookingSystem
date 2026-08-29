package com.event.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.event.model.Booking;
import com.event.util.DBConnection;

public class BookingDAO {

    // Record that a user booked an event
    public void addBooking(int eventId, String username) {
        String sql = "INSERT INTO bookings (event_id, username) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB error: " + e.getMessage(), e);
        }
    }

    // Check if a user already booked a specific event (prevent double booking)
    public boolean hasUserBooked(int eventId, String username) {
        String sql = "SELECT * FROM bookings WHERE event_id=? AND username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB error: " + e.getMessage(), e);
        }
    }

    // Get all bookings made by a specific user (for "My Bookings" page)
    public List<Booking> getBookingsByUser(String username) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setEventId(rs.getInt("event_id"));
                b.setUsername(rs.getString("username"));
                b.setBookedAt(rs.getString("booked_at"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB error: " + e.getMessage(), e);
        }
        return list;
    }
    //delete the booking record:
    public boolean deleteBooking(int eventId, String username) {
        String sql = "DELETE FROM bookings WHERE event_id=? AND username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setString(2, username);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB error: " + e.getMessage(), e);
        }
    }
    //delete event by admin
    public void deleteBookingsByEvent(int eventId) {
        String sql = "DELETE FROM bookings WHERE event_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB error: " + e.getMessage(), e);
        }
    }
}