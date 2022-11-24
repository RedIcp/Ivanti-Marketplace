package s3_gps_ivanti.dto.login;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenDTOTest {

    @Test
    void hasRole() {
        AccessTokenDTO token = AccessTokenDTO.builder()
                .subject("user")
                .roles(List.of("Customer"))
                .userID("id")
                .build();

        assertTrue(token.hasRole("Customer"));

    }
    @Test
    void hasRole_NotFound() {
        AccessTokenDTO token = AccessTokenDTO.builder()
                .subject("user")
                .roles(null)
                .userID("id")
                .build();

        assertFalse(token.hasRole("Customer"));
    }
}