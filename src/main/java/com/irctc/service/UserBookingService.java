package com.irctc.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irctc.entities.UserDetails;

public class UserBookingService {

    private UserDetails user;
    private List<UserDetails> userList;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "localDB/user.json";

    public UserBookingService(UserDetails user) throws IOException {
        this.user = user;

        File usersFile = new File(USERS_PATH);

        if (usersFile.exists()) {
            userList = objectMapper.readValue(
                usersFile,
                new TypeReference<List<UserDetails>>() {}
            );
        }
    }
}
