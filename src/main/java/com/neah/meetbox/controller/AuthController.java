package com.neah.meetbox.controller;

import com.google.gson.Gson;
import com.neah.meetbox.CommonResponseModel;
import com.neah.meetbox.entity.UserProfile;
import com.neah.meetbox.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private ResponseModel res = new ResponseModel();

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseModel> signUp(@Valid @RequestBody UserProfile userProfile) {
        logger.info("I> " + new Gson().toJson(userProfile));
        UserProfile resultUser = authService.signUp(userProfile);
        if (resultUser == null) {
            res.code = "1";
            res.message = "Sign up is fail";
            logger.info("O> " + new Gson().toJson(res));
            return ResponseEntity.badRequest().body(res);
        }
        res.code = "0";
        res.message = "Success";
        logger.info("O> " + new Gson().toJson(res));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ResponseModel> signIn(@RequestBody Map<String, Object> reqBody) {
        logger.info("I> " + new Gson().toJson(reqBody));
        String username = (String) reqBody.get("username");
        String password = (String) reqBody.get("password");

        if (username == null || username.isEmpty()) {
            res.code = "SI001";
            res.message = "Username is required";
            return ResponseEntity.badRequest().body(res);
        }

        if (password == null || password.isEmpty()) {
            res.code = "SI002";
            res.message = "Password is required";
            return ResponseEntity.badRequest().body(res);
        }

        Optional<UserProfile> resultUser = authService.signIn(username, password);

        if (!resultUser.isPresent()) {
            res.code = "2";
            res.message = "Sign in is fail";
            logger.info("O> " + new Gson().toJson(res));
            return ResponseEntity.badRequest().body(res);
        }
        res.code = "0";
        res.message = "Success";
        logger.info("O> " + new Gson().toJson(res));
        return ResponseEntity.ok(res);
    }

    @ExceptionHandler
    public void handleUserNotFoundException(Exception exception, HttpServletResponse response) throws IOException {
        logger.error("Error : " + exception, exception);
        if (exception instanceof SQLException) {
            response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        }
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    private class ResponseModel extends CommonResponseModel {

    }
}
