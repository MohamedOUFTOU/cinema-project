package com.example.cinemaproject.entities;

import com.example.cinemaproject.entities.Place;
import com.example.cinemaproject.entities.Ticket;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj",types = Ticket.class)
public interface TicketProjection {
    public Long getId();
    public String getNomClient();
    public double getPrix();
    public  int getCodePaiment();
    public  boolean getReserve();
    public Place getPlace();
}
