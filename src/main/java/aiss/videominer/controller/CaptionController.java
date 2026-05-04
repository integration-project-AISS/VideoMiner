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

import aiss.videominer.model.Caption;
import aiss.videominer.model.Video;
import aiss.videominer.repository.CaptionRepository;
import aiss.videominer.repository.VideoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CaptionController {

    @Autowired
    private CaptionRepository captionRepository;

    @Autowired
    private VideoRepository videoRepository;

    // GET http://localhost:8080/api/captions
    @GetMapping("/captions")
    public List<Caption> findAll() {
        return captionRepository.findAll();
    }

    // GET http://localhost:8080/api/captions/{captionId}
    @GetMapping("/captions/{captionId}")
    public Caption findOne(@PathVariable String captionId) {
        return captionRepository.findById(captionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Caption not found"));
    }

    // GET http://localhost:8080/api/videos/{videoId}/captions
    @GetMapping("/videos/{videoId}/captions")
    public List<Caption> findByVideoId(@PathVariable String videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        return video.getCaptions() != null ? video.getCaptions() : new ArrayList<>();
    }

    // POST http://localhost:8080/api/videos/{videoId}/captions
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/videos/{videoId}/captions")
    public Caption create(@PathVariable String videoId,
                          @Valid @RequestBody Caption caption) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        if (video.getCaptions() == null) {
            video.setCaptions(new ArrayList<>());
        }

        video.getCaptions().add(caption);
        videoRepository.save(video);

        return caption;
    }

    // PUT http://localhost:8080/api/videos/{videoId}/captions/{captionId}
    @PutMapping("/videos/{videoId}/captions/{captionId}")
    public Caption update(@PathVariable String videoId,
                          @PathVariable String captionId,
                          @Valid @RequestBody Caption captionData) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        Caption caption = video.getCaptions().stream()
                .filter(c -> captionId.equals(c.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Caption not found"));

        caption.setName(captionData.getName());
        caption.setLanguage(captionData.getLanguage());

        return captionRepository.save(caption);
    }

    // DELETE http://localhost:8080/api/videos/{videoId}/captions/{captionId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/videos/{videoId}/captions/{captionId}")
    public void delete(@PathVariable String videoId,
                       @PathVariable String captionId) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        boolean belongsToVideo = video.getCaptions() != null &&
                video.getCaptions().stream().anyMatch(c -> captionId.equals(c.getId()));

        if (!belongsToVideo) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caption not found");
        }

        captionRepository.deleteById(captionId);
    }
}