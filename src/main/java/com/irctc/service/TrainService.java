package com.irctc.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irctc.entities.TrainDetails;

public class TrainService {
	
	private List<TrainDetails> trainList;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final String TRAIN_FILE_PATH = "../localDB/train.json";
	
	private TrainService() throws IOException{
		File train = new File(TRAIN_FILE_PATH);
		
		trainList = objectMapper.readValue(trains, new TypeReference<List<TrainDetails>>() {});
	}
	
	public List<TrainDetails> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }

	

	private boolean validTrain(TrainDetails train, String source, String destination) {
		
		List<String> stationOrder = trainList.getStations();
		
		int sourceIndex = stationOrder.indexOf(source.toLowerCase());
		int destinationIndex = stationOrder.indexOf(destination.toLowerCase());
		
		return sourceIndex != -1 && destinationIndex != -1 && sourceIndex<destinationIndex;
	}

	public void addTrain(TrainDetails train) {
		// TODO Auto-generated method stub
		
	}

	
	
}
