package com.irctc.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming (PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {

    private String userId;
    private String name;
    private String password;
    private String hashedPassword;
    private List<TicketDetails> ticketsBooked;

    public UserDetails(String name, String password, String hashedPassword, List<TicketDetails> ticketsBooked, String userId) {
    	this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;
    }
    
    public UserDetails() {
    }

	public void printTickets() {
		for(int i=0; i<ticketsBooked.size(); i++) {
			System.out.println(ticketsBooked.get(i).getTicketInfo());
		}
		
	}
}
