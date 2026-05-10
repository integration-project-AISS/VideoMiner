package aiss.videominer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import aiss.videominer.model.Caption;

public class CaptionControllerTest {

    @Test
    void testCaptionCreation() {

        Caption caption = new Caption();

        caption.setId("cap1");
        caption.setName("Subtítulo Español");
        caption.setLanguage("Spanish");

        assertNotNull(caption);

        assertEquals("cap1", caption.getId());
        assertEquals("Subtítulo Español", caption.getName());
        assertEquals("Spanish", caption.getLanguage());
    }
}