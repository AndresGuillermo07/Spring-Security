package com.springboot.springboot_security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class CustomerController {

    @GetMapping("/index")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/index2")
    public String index2(){
        return "Hello World Not SECURED!!";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession(HttpServletRequest request) {
        // Obtener ID de sesión
        String sessionId = getSessionId(request);

        // Obtener el usuario autenticado
        Object userObject = getAuthenticatedUser();

        // Construir la respuesta
        Map<String, Object> details = new HashMap<>();
        details.put("response", "Hello World");
        details.put("sessionId", sessionId);
        details.put("userObject", userObject);

        return ResponseEntity.ok(details);
    }

    private String getSessionId(HttpServletRequest request) {
        // Verificar si la sesión está disponible
        return (request.getSession(false) != null) ? request.getSession().getId() : "No Active Session";
    }

    private Object getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return authentication.getPrincipal();
        }
        return "No Authenticated User";
    }

}
