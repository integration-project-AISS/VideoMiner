package aiss.videominer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import aiss.videominer.model.User;

public class UserControllerTest {

    @Test
    void testUserCreation() {

        User user = new User();

        user.setName("Carlos");
        user.setUser_link("https://youtube.com");
        user.setPicture_link("https://image.com/avatar.png");

        assertNotNull(user);

        assertEquals("Carlos", user.getName());
        assertEquals("https://youtube.com", user.getUser_link());
        assertEquals("https://image.com/avatar.png", user.getPicture_link());
    }
}