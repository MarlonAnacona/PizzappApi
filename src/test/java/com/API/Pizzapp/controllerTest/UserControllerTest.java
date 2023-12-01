package com.API.Pizzapp.controllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.API.Pizzapp.Controller.UserControllerImp;
import com.API.Pizzapp.Models.AuthResponse;
import com.API.Pizzapp.Models.LoginDTO;
import com.API.Pizzapp.Models.ResponseDTO;
import com.API.Pizzapp.Models.UserEntity;
import com.API.Pizzapp.Security.JwtAuthenticationFilter;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @InjectMocks
    private UserControllerImp userControllerImp;

    @Mock
    private UserServiceI userServiceI;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private HttpServletRequest request;

    @Mock
    JwtService jwtService;

    @Mock
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Test
    public void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setNombre("Test");

        AuthResponse mockAuthResponse = new AuthResponse();
        mockAuthResponse.setToken("testToken");

        when(userServiceI.createUser(any(UserEntity.class))).thenReturn(mockAuthResponse);

        ResponseEntity result = userControllerImp.createUser(user);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getBody() instanceof AuthResponse);
    }

    @Test
    public void testLoginUser() throws Exception {
        LoginDTO login = new LoginDTO();
        login.setEmail("test@example.com");
        login.setPassword("password123");

        AuthResponse mockAuthResponse = new AuthResponse();
        mockAuthResponse.setToken("testToken");

        when(userServiceI.loginUser(any(LoginDTO.class))).thenReturn(mockAuthResponse);

        ResponseEntity result = userControllerImp.loginUser(login);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertTrue(result.getBody() instanceof AuthResponse);
    }
    @Test
    public void testUpdateUser() throws Exception {
        // Mock user data
        UserEntity user = new UserEntity();
        user.setNombre("Updated Name");

        // Mock JWT token and email
        String jwtToken = "mockJwtToken";
        String email = "user@example.com";

        // Mock userServiceI.updateUser to return the user entity
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.getUsernameFromToken(jwtToken)).thenReturn(email);
        when(userServiceI.updateUser(email, user)).thenReturn(user);

        // Call the updateUser method
        ResponseEntity result = userControllerImp.updateUser(request, user);

        // Verify the response
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

    }

    @Test
    public void testCreateUserInvalidInput() {
        // Simula un error al intentar crear un usuario
        when(userServiceI.createUser(null)).thenThrow(new RuntimeException());

        ResponseEntity response = userControllerImp.createUser(null);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void testLoginUserInvalidInput() throws Exception {
        // Simula un error al intentar iniciar sesión
        when(userServiceI.loginUser(null)).thenThrow(new RuntimeException());

        ResponseEntity response = userControllerImp.loginUser(null);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void testUpdateUserInvalidInput() throws Exception {
        // Simula un error al intentar actualizar un usuario
        String invalidId = null;
        String jwtToken = "mockJwtToken";
        String email = "user@example.com";

        // Mock userServiceI.updateUser to return the user entity
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.getUsernameFromToken(jwtToken)).thenReturn(email);

        when(userServiceI.updateUser(invalidId, null)).thenThrow(new RuntimeException());

        ResponseEntity response = userControllerImp.updateUser(request, null);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }
}
