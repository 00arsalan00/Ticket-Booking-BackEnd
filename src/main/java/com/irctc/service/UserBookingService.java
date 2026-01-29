package com.irctc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irctc.entities.TicketDetails;
import com.irctc.entities.TrainDetails;
import com.irctc.entities.UserDetails;

public class UserBookingService {

    private ObjectMapper objectMapper = new ObjectMapper();
    
    private List<UserDetails> userList;
    
    private UserDetails user;
    
    private final String USER_FILE_PATH = "IRCTC\src\main\java\com\irctc\localDB\user.json";
    
    // parameterize constructor
    private UserBookingService(UserDetails user) throws IOException{
    	this.user = user;
    	loadUserFromFile();
    }
    
    // non parameterize constructor
    private UserBookingService() throws IOException{
    	loadUserFromFile();	
    }
    
    // load user from json file object mapper used 
    private void loadUserFromFile() throws IOException{
    	userList = objectMapper.readValue(new File(USER_FILE_PATH), new TypeReference<List<UserDetails>>() {});
    }
    
    // login user: checking name and its password with hashed password
    public Boolean login() {
    	Optional<UserDetails> foundUser = userList.stream().filter(curUser -> {
    		return curUser.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),curUser.getHashedPassword());
    	}).findFirst();
    	
    	return foundUser.isPresent();	
    }
    
    // signup user
    public Boolean signup(UserDetails curUser) {
    	try {
    		userList.add(curUser);
    		saveUserToFile();
    		return Boolean.TRUE;
    	}catch (IOException e) {
    		return Boolean.FALSE;
		}
    }
    
    // saving signup details
    private void saveUserToFile() throws IOException{
    	File userFile = new File(USER_FILE_PATH);
    	objectMapper.writeValue(userFile, userList);
    }
    
    // fetcing tickets of user
    public void fetchBooking() {
    	Optional<UserDetails> fetchUser = userList.stream().filter(curUser -> {
    		return curUser.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),curUser.getHashedPassword());
    	}).findFirst();
    	
    	if(fetchUser.isPresent()) {
    		fetchUser.get().printTickets();
    	}
    }
    
    public Boolean cancelBooking(String ticketId) {
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Enter your Ticket Id");
    	
    	ticketId = scan.next();
    	
    	if(ticketId == null || ticketId.isEmpty()) {
    		System.out.println("TicketId cannot be null or Empty");
    		return Boolean.FALSE;
    	}
    	
    	boolean removed = user.getTicketsBooked().removeIf(ticketId -> ticketId.getTicketId().equals(ticketId));
    	
    	if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
        System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }
    
    //Get trains from S to D
    
    public List<TrainDetails> getTrain(String source,String destination){
    	try {
    		TrainService trainService = new TrainService();
    		return trainService.searchTrains(source,destination);
    	}catch (IOException e) {
    		return new ArrayList<>();
		}
    }
    
    // get seat details
    public List<List<Integer>> fetchTrain(TrainDetails train){
    	return train.getSeats();
    }
    
    //Book seat
    
    public Boolean bookTrainSeat(TrainDetails train, int row, int seat) {
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true; // Booking successful
                } else {
                    return false; // Seat is already booked
                }
            } else {
                return false; // Invalid row or seat index
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }
    
    
    
    
    
    

    
}
