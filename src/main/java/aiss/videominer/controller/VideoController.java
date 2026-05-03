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

import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    VideoRepository repository;

    // GET http://localhost:8080/api/videos
    // Devuelve todos los vídeos

    @GetMapping
    public List<Video> findAll() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/videos/{id}
    // Devuelve un vídeo por id

    @GetMapping("/{id}")
    public Video findOne(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Video not found"));
    }

    // POST http://localhost:8080/api/videos
    // Crea un vídeo nuevo

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Video create(@Valid @RequestBody Video video) {

        return repository.save(
                new Video(
                        video.getId(),
                        video.getName(),
                        video.getDescription(),
                        video.getReleaseTime(),
                        video.getAuthor(),
                        video.getComments(),
                        video.getCaptions()
                )
        );
    }
}