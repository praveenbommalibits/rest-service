package com.beta.replyservice;

import com.beta.replyservice.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping("/v2")
public class ReplyV2Controller {


    @Autowired
    private ReplyService replyService;

    @GetMapping("/reply/{rule}-{input}")
    public ResponseEntity<ReplyMessage> processV2(@PathVariable String rule, @PathVariable String input) {
        String processedString;
        try {
            processedString = replyService.processV2(rule, input);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReplyMessage("Invalid input"));
        }

        if (processedString != null) {
            return ResponseEntity.ok(new ReplyMessage(processedString));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReplyMessage("Invalid input"));
        }
    }
}
