package com.example.cinemaproject;

import com.example.cinemaproject.entities.Film;
import com.example.cinemaproject.entities.Salle;
import com.example.cinemaproject.entities.Seance;
import com.example.cinemaproject.entities.Ticket;
import com.example.cinemaproject.services.ICinemaInitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaProjectApplication implements CommandLineRunner {

    @Autowired
    private ICinemaInitServices cinemaInitServices;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CinemaProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class, Seance.class);
        cinemaInitServices.initVilles();
        cinemaInitServices.initCinemas();
        cinemaInitServices.initSalles();
        cinemaInitServices.initPlaces();
        cinemaInitServices.initSeances();
        cinemaInitServices.initCategories();
        cinemaInitServices.initFilms();
        cinemaInitServices.initProjections();
        cinemaInitServices.initTickets();
    }
}
