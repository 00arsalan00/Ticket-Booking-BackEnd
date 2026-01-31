package com.irctc.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketDetails {

    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private Date dateOfTravel;
    private TrainDetails train;

    public String getTicketInfo() {
        return String.format(
            "Ticket ID: %s | User: %s | %s -> %s | Date: %s",
            ticketId, userId, source, destination, dateOfTravel
        );
    }
}
