package com.bezkoder.springjwt.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @Test
    public void requestedAdminRoleCannotEscalatePrivileges() {
        String username = "admin-request-user";
        String email = "admin-request@example.test";
        String rawPassword = "admin-request-raw-pass";
        String encodedPassword = "encoded-admin-request-password";
        SignupRequest request = validSignupRequest(username, email, rawPassword);
        request.setRole(Collections.singleton("admin"));
        stubSuccessfulSignup(request, encodedPassword);

        ResponseEntity<?> response = authController.registerUser(request);

        User savedUser = captureSavedUser();
        assertExactlyUserRole(savedUser);
        assertFalse(hasRole(savedUser, ERole.ROLE_ADMIN));
        assertFalse(hasRole(savedUser, ERole.ROLE_STRATEGY));
        assertEquals(encodedPassword, savedUser.getPassword());
        assertNotEquals(rawPassword, savedUser.getPassword());
        verify(passwordEncoder).encode(rawPassword);
        verify(roleRepository).findByName(ERole.ROLE_USER);
        verify(roleRepository, never()).findByName(ERole.ROLE_ADMIN);
        verify(roleRepository, never()).findByName(ERole.ROLE_STRATEGY);
        assertSuccessfulSignupResponse(response);
    }

    @Test
    public void signupWithoutRequestedRoleReceivesUserRole() {
        String username = "default-role-user";
        String email = "default-role@example.test";
        String rawPassword = "default-role-raw-pass";
        String encodedPassword = "encoded-default-role-password";
        SignupRequest request = validSignupRequest(username, email, rawPassword);
        stubSuccessfulSignup(request, encodedPassword);

        ResponseEntity<?> response = authController.registerUser(request);

        User savedUser = captureSavedUser();
        assertExactlyUserRole(savedUser);
        assertEquals(encodedPassword, savedUser.getPassword());
        assertNotEquals(rawPassword, savedUser.getPassword());
        verify(passwordEncoder).encode(rawPassword);
        verify(roleRepository).findByName(ERole.ROLE_USER);
        verify(roleRepository, never()).findByName(ERole.ROLE_ADMIN);
        verify(roleRepository, never()).findByName(ERole.ROLE_STRATEGY);
        assertSuccessfulSignupResponse(response);
    }

    private SignupRequest validSignupRequest(String username, String email, String password) {
        SignupRequest request = new SignupRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

    private void stubSuccessfulSignup(SignupRequest request, String encodedPassword) {
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

        // This answer supports both the vulnerable admin lookup and the fixed user lookup.
        when(roleRepository.findByName(any(ERole.class)))
                .thenAnswer(invocation -> Optional.of(new Role(invocation.getArgument(0))));
    }

    private User captureSavedUser() {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        return userCaptor.getValue();
    }

    private void assertExactlyUserRole(User user) {
        assertEquals(1, user.getRoles().size());
        assertEquals(ERole.ROLE_USER, user.getRoles().iterator().next().getName());
    }

    private boolean hasRole(User user, ERole roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName() == roleName);
    }

    private void assertSuccessfulSignupResponse(ResponseEntity<?> response) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(
                "User registered successfully!",
                ((MessageResponse) response.getBody()).getMessage());
    }
}
