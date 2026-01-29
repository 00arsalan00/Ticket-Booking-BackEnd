package com.irctc.entities;

import java.util.*;

import org.springframework.boot.autoconfigure.jmx.ParentAwareNamingStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(ParentAwareNamingStrategy.SnakeCaseStrategy.class)
@Data
public class TicketDetails {
	
	 private String ticketId;
	 private String userId;
	 private String source;
	 private String destination;
	 private Date dateOfTravel;
	 private TrainDetails train;
	 
	 public String getTicketInfo(){
	        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, destination, dateOfTravel);
	    }
}
