package http;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {

    @Test
    void UUID_Test() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}
