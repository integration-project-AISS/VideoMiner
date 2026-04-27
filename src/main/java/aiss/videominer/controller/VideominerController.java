package aiss.videominer.controller;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import aiss.videominer.model.*;
import aiss.videominer.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.List;



@RestController
@RequestMapping("/videominer/api")
public class VideominerController {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CaptionRepository captionRepository;

    // ---------------------------------------------------------
    // Operaciones para CHANNELS
    // ---------------------------------------------------------

    // Listar todos los canales
    @GetMapping("/channels")
    public List<Channel> findAllChannels() {
        return channelRepository.findAll();
    }

    // Buscar un canal por ID
    @GetMapping("/channels/{id}")
    public Channel findChannelById(@PathVariable String id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canal no encontrado"));
    }

    // Añadir un nuevo canal (Operación para los adaptadores/mineros)
    @PostMapping("/channels")
    @ResponseStatus(HttpStatus.CREATED)
    public Channel createChannel(@Valid @RequestBody Channel channel) {
        return channelRepository.save(channel);
    }

    // ---------------------------------------------------------
    // Operaciones para VIDEOS
    // ---------------------------------------------------------

    // Listar todos los vídeos
    @GetMapping("/videos")
    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    // Buscar vídeo por ID
    @GetMapping("/videos/{id}")
    public Video findVideoById(@PathVariable String id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vídeo no encontrado"));
    }

    // ---------------------------------------------------------
    // Operaciones para COMMENTS
    // ---------------------------------------------------------

    // Listar todos los comentarios
    @GetMapping("/comments")
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    // Buscar comentario por ID
    @GetMapping("/comments/{id}")
    public Comment findCommentById(@PathVariable String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario no encontrado"));
    }

    // Devolver los comentarios de un vídeo dado su id
    @GetMapping("/videos/{videoId}/comments")
    public List<Comment> findCommentsByVideo(@PathVariable String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vídeo no encontrado"));
        return video.getComments();
    }

    // ---------------------------------------------------------
    // Operaciones para CAPTIONS
    // ---------------------------------------------------------

    // Listar todas las captions
    @GetMapping("/captions")
    public List<Caption> findAllCaptions() {
        return captionRepository.findAll();
    }

    // Buscar caption por ID
    @GetMapping("/captions/{id}")
    public Caption findCaptionById(@PathVariable String id) {
        return captionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Caption no encontrada"));
    }

    @GetMapping("/videos/{videoId}/captions")
    public List<Caption> findCaptionsByVideo(@PathVariable String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vídeo no encontrado"));
        return video.getCaptions();
    }
}