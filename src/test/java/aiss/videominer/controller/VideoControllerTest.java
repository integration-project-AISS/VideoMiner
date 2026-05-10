package aiss.videominer.controller;

import aiss.videominer.model.Video;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Caption;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class VideoControllerTest {

    @Test
    void testVideoCreation() {

        Video video = new Video();

        video.setId("v1");
        video.setName("Video Test");
        video.setDescription("Descripción del vídeo");
        video.setComments(new ArrayList<Comment>());
        video.setCaptions(new ArrayList<Caption>());

        assertNotNull(video);

        assertEquals("v1", video.getId());
        assertEquals("Video Test", video.getName());
        assertEquals("Descripción del vídeo", video.getDescription());

        assertNotNull(video.getComments());
        assertNotNull(video.getCaptions());

        assertEquals(0, video.getComments().size());
        assertEquals(0, video.getCaptions().size());
    }
}