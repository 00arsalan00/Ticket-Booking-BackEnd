package com.irctc.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irctc.entities.TrainDetails;
import com.irctc.entities.UserDetails;
import com.irctc.util.UserServiceUtil;

public class UserBookingService {

    private static final String USER_FILE_PATH = "../localDB/user.json";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<UserDetails> userList;

    public UserBookingService() throws IOException {
        loadUsers();
    }

    private void loadUsers() throws IOException {
        File file = new File(USER_FILE_PATH);
        if (!file.exists()) {
            userList = new ArrayList<>();
            return;
        }
        userList = objectMapper.readValue(file, new TypeReference<List<UserDetails>>() {});
    }

    private void saveUsers() throws IOException {
        objectMapper.writeValue(new File(USER_FILE_PATH), userList);
    }

    // LOGIN
    public boolean login(String name, String password) {
        return userList.stream()
                .anyMatch(u ->
                        u.getName().equalsIgnoreCase(name) &&
                        UserServiceUtil.checkPassword(password, u.getHashedPassword())
                );
    }

    // SIGNUP
    public boolean signup(UserDetails user) {
        if (userList.stream().anyMatch(u -> u.getName().equalsIgnoreCase(user.getName()))) {
            return false;
        }

        user.setHashedPassword(UserServiceUtil.hashPassword(user.getPassword()));
        user.setPassword(null);

        userList.add(user);
        try {
            saveUsers();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // CANCEL TICKET
    public boolean cancelBooking(UserDetails user, String ticketId) {
        return user.getTicketsBooked()
                .removeIf(ticket -> ticket.getTicketId().equalsIgnoreCase(ticketId));
    }

    // SEARCH TRAINS
    public List<TrainDetails> getTrains(String source, String destination) throws IOException {
        TrainService trainService = new TrainService();
        return trainService.searchTrains(source, destination);
    }

    // BOOK SEAT
    public boolean bookTrainSeat(TrainDetails train, int row, int seat) throws IOException {
        TrainService trainService = new TrainService();

        List<List<Integer>> seats = train.getSeats();
        if (row < 0 || row >= seats.size()) return false;
        if (seat < 0 || seat >= seats.get(row).size()) return false;

        if (seats.get(row).get(seat) == 1) return false;

        seats.get(row).set(seat, 1);
        trainService.updateTrain(train);
        return true;
    }
}
