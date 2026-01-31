package com.irctc.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irctc.entities.TrainDetails;

public class TrainService {

    private static final String TRAIN_FILE_PATH = "../localDB/train.json";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<TrainDetails> trainList;

    public TrainService() throws IOException {
        File file = new File(TRAIN_FILE_PATH);
        if (!file.exists()) {
            trainList = new ArrayList<>();
            return;
        }
        trainList = objectMapper.readValue(file, new TypeReference<List<TrainDetails>>() {});
    }

    public List<TrainDetails> searchTrains(String source, String destination) {
        return trainList.stream()
                .filter(t -> validTrain(t, source, destination))
                .collect(Collectors.toList());
    }

    private boolean validTrain(TrainDetails train, String source, String destination) {
        List<String> stations = train.getStations();
        int s = stations.indexOf(source.toLowerCase());
        int d = stations.indexOf(destination.toLowerCase());
        return s != -1 && d != -1 && s < d;
    }

    public void updateTrain(TrainDetails updatedTrain) throws IOException {
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId()
                        .equalsIgnoreCase(updatedTrain.getTrainId()))
                .findFirst();

        if (index.isPresent()) {
            trainList.set(index.getAsInt(), updatedTrain);
        } else {
            trainList.add(updatedTrain);
        }

        objectMapper.writeValue(new File(TRAIN_FILE_PATH), trainList);
    }
}
