package aiss.videominer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.CommentRepository;
import aiss.videominer.repository.VideoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    // GET http://localhost:8080/api/comments
    @GetMapping("/comments")
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    // GET http://localhost:8080/api/comments/{commentId}
    @GetMapping("/comments/{commentId}")
    public Comment findOne(@PathVariable String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found"));
    }

    // GET http://localhost:8080/api/videos/{videoId}/comments
    @GetMapping("/videos/{videoId}/comments")
    public List<Comment> findByVideoId(@PathVariable String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        return video.getComments() != null ? video.getComments() : new ArrayList<>();
    }

    // POST http://localhost:8080/api/videos/{videoId}/comments
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/videos/{videoId}/comments")
    public Comment create(@PathVariable String videoId,
                          @Valid @RequestBody Comment comment) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        if (video.getComments() == null) {
            video.setComments(new ArrayList<>());
        }

        video.getComments().add(comment);
        videoRepository.save(video);

        return comment;
    }

    // PUT http://localhost:8080/api/videos/{videoId}/comments/{commentId}
    @PutMapping("/videos/{videoId}/comments/{commentId}")
    public Comment update(@PathVariable String videoId,
                          @PathVariable String commentId,
                          @Valid @RequestBody Comment commentData) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        Comment comment = video.getComments().stream()
                .filter(c -> commentId.equals(c.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found"));

        comment.setText(commentData.getText());
        comment.setCreatedOn(commentData.getCreatedOn());

        return commentRepository.save(comment);
    }

    // DELETE http://localhost:8080/api/videos/{videoId}/comments/{commentId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/videos/{videoId}/comments/{commentId}")
    public void delete(@PathVariable String videoId,
                       @PathVariable String commentId) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        boolean belongsToVideo = video.getComments() != null &&
                video.getComments().stream().anyMatch(c -> commentId.equals(c.getId()));

        if (!belongsToVideo) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }

        commentRepository.deleteById(commentId);
    }
}