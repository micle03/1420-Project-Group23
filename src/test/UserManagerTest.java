package test;

import com.group23.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    @Test
    void testCreateGuest() {
        Guest g = new Guest("U001", "Wyatt", "w@email.com");

        assertEquals("Guest", g.getUserType());
        assertEquals(1, g.getMaxConfirmedBookings());
    }
}