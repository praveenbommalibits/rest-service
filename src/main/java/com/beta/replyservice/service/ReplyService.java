package com.beta.replyservice.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class ReplyService {

    private final Map<Character, Function<String, String>> ruleProcessors;

    public ReplyService() {
        ruleProcessors = new HashMap<>();
        ruleProcessors.put('1', this::reverseString);
        ruleProcessors.put('2', this::encodeMD5);
        // Add more rule processors for future requirements
    }

    public String processV2(String rule, String input) {
        if (!isValidInput(input) || !isValidRule(rule)) {
            throw new IllegalArgumentException("Invalid input");
        }

        String[] ruleParts = rule.split("-");
        StringBuilder processedString = new StringBuilder(input);

        for (char rulePart : ruleParts[0].toCharArray()) {
            Function<String, String> ruleProcessor = ruleProcessors.get(rulePart);
            if (ruleProcessor != null) {
                processedString = new StringBuilder(ruleProcessor.apply(processedString.toString()));
            } else {
                throw new IllegalArgumentException("Invalid rule");
            }
        }
        return processedString.toString();
    }

    private String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    private String encodeMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    private boolean isValidInput(String input) {
        return input.matches("[a-z0-9]+");
    }

    private boolean isValidRule(String rule) {
        return rule.matches("[12]{2}");
    }
}
