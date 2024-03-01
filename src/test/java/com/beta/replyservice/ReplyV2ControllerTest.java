package com.beta.replyservice;
import com.beta.replyservice.service.ReplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ReplyV2ControllerTest {

    @Mock
    private ReplyService replyService;

    @InjectMocks
    private ReplyV2Controller replyV2Controller;

    @BeforeEach
    void setUp() {
        // Setup mock behavior if needed
    }

    @Test
    void testProcessV2_ValidInputAndRule_ReverseString() {
        // Given
        String input = "kbzw9ru";
        String rule = "1";
        String expectedOutput = "ur9wzbk";

        when(replyService.processV2(rule, input)).thenReturn(expectedOutput);

        // When
        ResponseEntity<ReplyMessage> response = replyV2Controller.processV2(rule, input);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody().getMessage());
    }

    @Test
    void testProcessV2_ValidInputAndRule_EncodeMD5() {
        // Given
        String input = "kbzw9ru";
        String rule = "2";
        String expectedOutput = "0fafeaae780954464c1b29f765861fad";

        when(replyService.processV2(rule, input)).thenReturn(expectedOutput);

        // When
        ResponseEntity<ReplyMessage> response = replyV2Controller.processV2(rule, input);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody().getMessage());
    }

    @Test
    void testProcessV2_InvalidInput() {
        // Given
        String input = "!@#$";
        String rule = "1";

        when(replyService.processV2(rule, input)).thenThrow(new IllegalArgumentException("Invalid input"));

        // When
        ResponseEntity<ReplyMessage> response = replyV2Controller.processV2(rule, input);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    @Test
    void testProcessV2_InvalidRule() {
        // Given
        String input = "kbzw9ru";
        String rule = "3";

        when(replyService.processV2(rule, input)).thenThrow(new IllegalArgumentException("Invalid rule"));

        // When
        ResponseEntity<ReplyMessage> response = replyV2Controller.processV2(rule, input);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody().getMessage());
    }
}

