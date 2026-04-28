package aiss.videominer.controller;

import aiss.videominer.model.Caption;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    // Listar todos los canales (con paginación, filtrado por nombre y ordenación)
    @GetMapping("/channels")
    public List<Channel> findAllChannels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String order) {

        Pageable paging;
        if (order != null) {
            if (order.startsWith("-"))
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
        } else {
            paging = PageRequest.of(page, size);
        }

        if (name != null)
            return channelRepository.findByName(name, paging).getContent();
        return channelRepository.findAll(paging).getContent();
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

    // PUT - Actualizar canal
    @PutMapping("/channels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateChannel(@PathVariable String id, @Valid @RequestBody Channel channel) {
        Channel existingChannel = channelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Canal no encontrado"));
        existingChannel.setName(channel.getName());
        existingChannel.setDescription(channel.getDescription());
        existingChannel.setCreatedTime(channel.getCreatedTime());
        existingChannel.setVideos(channel.getVideos());
        channelRepository.save(existingChannel);
    }

    // DELETE - Eliminar canal
    @DeleteMapping("/channels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChannel(@PathVariable String id) {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Canal no encontrado");
        }
    }

    // ---------------------------------------------------------
    // Operaciones para VIDEOS
    // ---------------------------------------------------------

    // Listar todos los vídeos (con paginación, filtrado por nombre y ordenación)
    @GetMapping("/videos")
    public List<Video> findAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String order) {

        Pageable paging;
        if (order != null) {
            if (order.startsWith("-"))
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
        } else {
            paging = PageRequest.of(page, size);
        }

        if (name != null)
            return videoRepository.findByName(name, paging).getContent();
        return videoRepository.findAll(paging).getContent();
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

    // Listar todos los comentarios (con paginación, filtrado por texto y ordenación)
    @GetMapping("/comments")
    public List<Comment> findAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) String order) {

        Pageable paging;
        if (order != null) {
            if (order.startsWith("-"))
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
        } else {
            paging = PageRequest.of(page, size);
        }

        if (text != null)
            return commentRepository.findByText(text, paging).getContent();
        return commentRepository.findAll(paging).getContent();
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

    // Listar todas las captions (con paginación, filtrado por idioma y ordenación)
    @GetMapping("/captions")
    public List<Caption> findAllCaptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String order) {

        Pageable paging;
        if (order != null) {
            if (order.startsWith("-"))
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
        } else {
            paging = PageRequest.of(page, size);
        }

        if (language != null)
            return captionRepository.findByLanguage(language, paging).getContent();
        return captionRepository.findAll(paging).getContent();
    }

    // Buscar caption por ID
    @GetMapping("/captions/{id}")
    public Caption findCaptionById(@PathVariable String id) {
        return captionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Caption no encontrada"));
    }

    // Devolver las captions de un vídeo dado su id
    @GetMapping("/videos/{videoId}/captions")
    public List<Caption> findCaptionsByVideo(@PathVariable String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vídeo no encontrado"));
        return video.getCaptions();
    }
}