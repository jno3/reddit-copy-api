package com.rdt.redditcopy.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomErrorController implements ErrorController{
    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fake error");
    }
}