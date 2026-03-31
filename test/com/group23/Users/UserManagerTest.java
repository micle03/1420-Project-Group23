package com.group23.Users;

import com.group23.Users.model.Guest;
import com.group23.Users.model.User;
import com.group23.Users.service.UserManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    @Test
    void testCreateGuest() {
        Guest g = new Guest("U001", "Wyatt", "w@email.com");

        assertEquals("Guest", g.getUserType());
        assertEquals(1, g.getMaxConfirmedBookings());
    }

    @Test
    void testCreateUserAndAddToManager() {
        UserManager manager = new UserManager();

        User user = manager.createUser("U002", "Bob", "b@email.com", "guest");

        assertEquals("U002", user.getUserId());
        assertEquals(1, manager.getTotalUsers());
    }

    @Test
    void testGetUserById() {
        UserManager manager = new UserManager();
        User createdUser = manager.createUser("U003", "Alice", "a@email.com", "student");

        User foundUser = manager.getUserById("U003");

        assertEquals(createdUser, foundUser);
    }

    @Test
    void testDuplicateUserIdThrowsException() {
        UserManager manager = new UserManager();
        manager.createUser("U004", "First", "first@email.com", "staff");

        assertThrows(IllegalArgumentException.class, () -> {
            manager.createUser("U004", "Second", "second@email.com", "guest");
        });
    }
}