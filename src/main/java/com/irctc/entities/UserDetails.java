package com.irctc.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {

    private String userId;
    private String name;

    @JsonIgnore // NEVER persist raw password
    private String password;

    private String hashedPassword;
    private List<TicketDetails> ticketsBooked;

    public UserDetails() {}

    public UserDetails(String userId, String name, String hashedPassword, List<TicketDetails> ticketsBooked) {
        this.userId = userId;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
    }

    public void printTickets() {
        if (ticketsBooked == null || ticketsBooked.isEmpty()) {
            System.out.println("No tickets booked.");
            return;
        }
        ticketsBooked.forEach(t -> System.out.println(t.getTicketInfo()));
    }
}
