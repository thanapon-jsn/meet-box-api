package com.neah.meetbox.controller;

import com.google.gson.Gson;
import com.neah.meetbox.CommonResponseModel;
import com.neah.meetbox.entity.UserProfile;
import com.neah.meetbox.service.UserProfileService;
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

@RestController
@RequestMapping("/users")
public class UserProfileController {

    private UserProfileService userProfileService;
    private Logger logger = LoggerFactory.getLogger(UserProfileController.class);
    private ResponseModel res = new ResponseModel();

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Integer id) {
        logger.info("I> " + id);
        UserProfile userProfile = userProfileService.getUserProfileById(id).orElse(null);
        if (userProfile == null) {
            return ResponseEntity.notFound().build();
        }
        logger.info("O> " + new Gson().toJson(userProfile));
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping()
    public ResponseEntity<ResponseModel> createUserProfile(@Valid @RequestBody UserProfile userProfile) {
        logger.info("I> " + new Gson().toJson(userProfile));
        UserProfile createUser = userProfileService.createUserProfile(userProfile);
        if (createUser == null) {
            res.code = "1";
            res.message = "Create user fail";
            ResponseEntity.badRequest().body(res);
            logger.info("O> " + new Gson().toJson(res));
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
        response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), exception.getMessage());
    }

    private class ResponseModel extends CommonResponseModel {

    }
}
