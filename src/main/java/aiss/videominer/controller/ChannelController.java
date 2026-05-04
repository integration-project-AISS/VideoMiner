package aiss.videominer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aiss.videominer.model.Channel;
import aiss.videominer.model.Video;
import aiss.videominer.repository.ChannelRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    private ChannelRepository channelRepository;

    // GET http://localhost:8080/api/channels
    @GetMapping
    public Page<Channel> findAll(
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

        return channelRepository.findAll(pageable);
   }

    // GET http://localhost:8080/api/channels/{channelId}
    @GetMapping("/{channelId}")
    public Channel findOne(@PathVariable String channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Channel not found"));
    }

    // GET http://localhost:8080/api/channels/{channelId}/videos
    @GetMapping("/{channelId}/videos")
    public List<Video> findVideosByChannelId(@PathVariable String channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Channel not found"));

        return channel.getVideos();
    }

    // POST http://localhost:8080/api/channels
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel) {
        if (channel.getVideos() == null) {
            channel.setVideos(new ArrayList<>());
        }
        return channelRepository.save(channel);
    }

    // POST http://localhost:8080/api/channels/{channelId}/videos
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{channelId}/videos")
    public Video addVideoToChannel(@PathVariable String channelId,
                                   @Valid @RequestBody Video video) {

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Channel not found"));

        if (channel.getVideos() == null) {
            channel.setVideos(new ArrayList<>());
        }

        channel.getVideos().add(video);
        channelRepository.save(channel);

        return video;
    }

    // PUT http://localhost:8080/api/channels/{channelId}
    @PutMapping("/{channelId}")
    public Channel update(@PathVariable String channelId,
                          @Valid @RequestBody Channel channelData) {

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Channel not found"));

        channel.setName(channelData.getName());
        channel.setDescription(channelData.getDescription());
        channel.setCreatedTime(channelData.getCreatedTime());

        if (channelData.getVideos() != null) {
            channel.setVideos(channelData.getVideos());
        }

        return channelRepository.save(channel);
    }

    // PUT http://localhost:8080/api/channels/{channelId}/videos/{videoId}
    @PutMapping("/{channelId}/videos/{videoId}")
    public Video updateVideoInChannel(@PathVariable String channelId,
                                      @PathVariable String videoId,
                                      @Valid @RequestBody Video videoData) {

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Channel not found"));

        if (channel.getVideos() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }

        Video video = channel.getVideos().stream()
                .filter(v -> videoId.equals(v.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"));

        video.setName(videoData.getName());
        video.setDescription(videoData.getDescription());
        video.setReleaseTime(videoData.getReleaseTime());
        video.setAuthor(videoData.getAuthor());
        video.setComments(videoData.getComments());
        video.setCaptions(videoData.getCaptions());

        channelRepository.save(channel);

        return video;
    }

    // DELETE http://localhost:8080/api/channels/{channelId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{channelId}")
    public void delete(@PathVariable String channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found");
        }
        channelRepository.deleteById(channelId);
    }

    // DELETE http://localhost:8080/api/channels/{channelId}/videos/{videoId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{channelId}/videos/{videoId}")
    public void deleteVideoFromChannel(@PathVariable String channelId,
                                       @PathVariable String videoId) {

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Channel not found"));

        if (channel.getVideos() == null || channel.getVideos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }

        boolean removed = channel.getVideos().removeIf(v -> videoId.equals(v.getId()));
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }

        channelRepository.save(channel);
    }
}