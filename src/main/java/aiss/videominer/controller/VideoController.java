package aiss.videominer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aiss.videominer.model.Caption;
import aiss.videominer.model.Comment;
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
    public Page<Video> findAll(

            @RequestParam(required = false) String name,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String order

    ) {

        Pageable paging = PageRequest.of(
                page,
                size,
                Sort.by(order)
        );

        if (name != null) {
            return repository.findByName(name, paging);
        }

        return repository.findAll(paging);
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

    // GET http://localhost:8080/api/videos/{id}/comments
    // Devuelve los comentarios de un vídeo por id

    @GetMapping("/{videoId}/comments")
    public List<Comment> findCommentsByVideo(
            @PathVariable String videoId
    ) {

        Video video = repository.findById(videoId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Video not found"
                        )
                );

        return video.getComments();
    }

    // GET GET http://localhost:8080/api/videos/{id}/captions
    // Devuelve los subtítulos de un vídeo por id

    @GetMapping("/{videoId}/captions")
    public List<Caption> findCaptionsByVideo(
            @PathVariable String videoId
    ) {

        Video video = repository.findById(videoId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Video not found"
                        )
                );

        return video.getCaptions();
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