package aiss.videominer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private VideoRepository videoRepository;

    // GET http://localhost:8080/api/videos
    @GetMapping
    public Page<Video> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String order
    ) {

        Pageable pageable;

        if (order != null) {
             pageable = PageRequest.of(page, size, Sort.by(order));
        } else {
             pageable = PageRequest.of(page, size);
        }
            
        return videoRepository.findAll(pageable);
    }

    // GET http://localhost:8080/api/videos/{videoId}
    @GetMapping("/{videoId}")
    public Video findOne(@PathVariable String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));
    }

    // PUT http://localhost:8080/api/videos/{videoId}
    @PutMapping("/{videoId}")
    public Video update(@PathVariable String videoId,
                        @Valid @RequestBody Video videoData) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        video.setName(videoData.getName());
        video.setDescription(videoData.getDescription());
        video.setReleaseTime(videoData.getReleaseTime());
        video.setAuthor(videoData.getAuthor());
        video.setComments(videoData.getComments());
        video.setCaptions(videoData.getCaptions());

        return videoRepository.save(video);
    }

    // DELETE http://localhost:8080/api/videos/{videoId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{videoId}")
    public void delete(@PathVariable String videoId) {
        if (!videoRepository.existsById(videoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }
        videoRepository.deleteById(videoId);
    }
}