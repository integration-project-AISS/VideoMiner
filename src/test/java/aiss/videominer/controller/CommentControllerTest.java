package aiss.videominer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import aiss.videominer.model.Comment;

public class CommentControllerTest {

    @Test
    void testCommentCreation() {

        Comment comment = new Comment();

        comment.setId("c1");
        comment.setText("Comentario de prueba");
        comment.setCreatedOn("2024-01-01");

        assertNotNull(comment);

        assertEquals("c1", comment.getId());
        assertEquals("Comentario de prueba", comment.getText());
        assertEquals("2024-01-01", comment.getCreatedOn());
    }
}