package aiss.videominer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    // GET http://localhost:8080/api/channels
    // Devuelve todos los canales

    @GetMapping
    public List<Channel> findAll() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/channels/{id}
    // Devuelve un canal por id

    @GetMapping("/{id}")
    public Channel findOne(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Channel not found"));
    }

    // POST http://localhost:8080/api/channels
    // Crea un canal nuevo
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel) {

        return repository.save(
                new Channel(
                        channel.getId(),
                        channel.getName(),
                        channel.getDescription(),
                        channel.getCreatedTime(),
                        channel.getVideos()
                )
        );
    }
}