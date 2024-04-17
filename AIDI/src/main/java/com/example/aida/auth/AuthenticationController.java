package com.example.aida.auth;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) throws Throwable {
        return ResponseEntity.ok(service.authenticate(request));
    }

//    @GetMapping("/activate-account")
//    public void confirm(
//            @RequestParam String token
//    ) {
//        //service.activateAccount(token);
//    }

//    @PostMapping("/send")
//    public String sendMail(@RequestParam String to, String subject, String body) {
//        return emailService.sendMail(to, subject, body);
//    }
//    @GetMapping("confirm")
//    public ResponseEntity<String> confirm(
//            @RequestParam String token
//    ) {
//        return ResponseEntity.ok(service.confirmToken(token));
//    }
}
