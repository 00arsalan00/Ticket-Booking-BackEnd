package com.irctc.entities;

import java.util.List;

public class UserDetails {

    private String userId;
    private String name;
    private String password;
    private String hashedPassword;
    private List<TicketDetails> ticketsBooked;

    // REQUIRED by Jackson
    public UserDetails() {
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<TicketDetails> getTicketsBooked() {
        return ticketsBooked;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setTicketsBooked(List<TicketDetails> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }
}
