package com.example.cinemaproject.web;

import com.example.cinemaproject.entities.Film;
import com.example.cinemaproject.entities.Ticket;
import com.example.cinemaproject.repositories.FilmRepository;
import com.example.cinemaproject.repositories.TicketRepository;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {

    final String baseURL = System.getProperty("user.home")+"/cinema/films/images/";
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;


    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadImage(@RequestParam("photo")MultipartFile file) throws IOException {
        System.out.println(file.getBytes().length);
        System.out.println(file.getOriginalFilename());
        Path path = Paths.get(baseURL+file.getOriginalFilename().trim());

        Files.write(path,file.getBytes());
        return ResponseEntity.status(HttpStatus.OK);
    }
    @SneakyThrows
    @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable(name = "id") Long id){
        Film film = filmRepository.findById(id).get();
        String nomPhoto = film.getPhoto();
        File file = new File(baseURL+nomPhoto);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketsForm ticketsForm){
        List<Ticket> tickets = new ArrayList<Ticket>();
        ticketsForm.getTicketsId().forEach(id ->{
            Ticket ticket = ticketRepository.findById(id).get();
            ticket.setReserve(true);
            ticket.setCodePaiment(ticketsForm.getCodePaiment());
            ticket.setNomClient(ticketsForm.getNomClient());
            ticketRepository.save(ticket);
            tickets.add(ticket);
        });
        return tickets;
    }
}

@Data
class TicketsForm{
    private List<Long> ticketsId = new ArrayList<>();
    private String nomClient;
    private int codePaiment;
}
