package aiss.videominer.controller;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import aiss.videominer.model.Channel;

public class ChannelControllerTest {

    @Test
    void testChannelCreation() {

        Channel channel = new Channel();

        channel.setId("1");
        channel.setName("Canal Test");
        channel.setDescription("Descripción del canal");
        channel.setVideos(new ArrayList<>());

        assertNotNull(channel);

        assertEquals("1", channel.getId());
        assertEquals("Canal Test", channel.getName());
        assertEquals("Descripción del canal", channel.getDescription());

        assertNotNull(channel.getVideos());
        assertEquals(0, channel.getVideos().size());
    }
}