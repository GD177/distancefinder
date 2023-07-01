package com.geographical.distancefinder.service;

import com.geographical.distancefinder.exception.InputValidationException;
import com.geographical.distancefinder.exception.NonRetryableException;
import com.geographical.distancefinder.model.LocationInfo;
import com.geographical.distancefinder.model.request.GetDistanceBetweenPostCodesRequest;
import com.geographical.distancefinder.model.response.GetDistanceBetweenPostCodesResponse;
import com.geographical.distancefinder.util.FileReader;
import org.jetbrains.annotations.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

/**
 * Service API handler
 */
@Service("distanceFinderBetweenPostCodesService")
public class DistanceFinderBetweenPostCodesServiceImpl implements DistanceFinderService {

    private final static double EARTH_RADIUS = 6371; // radius in kilometers
    private final static String UNIT = "km";
    private static final Logger log = LogManager.getLogger(DistanceFinderBetweenPostCodesServiceImpl.class);
    private Map<String, LocationInfo> postCodeMap;
    private static final String fileName = "ukpostcodes.csv";

    private File readFile() throws IOException {
        try {
            return FileReader.readFileFromLocal(fileName,"d");
        } catch (IOException e) {
            log.error("Error while loading postcode info : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public GetDistanceBetweenPostCodesResponse getDistance(
            final GetDistanceBetweenPostCodesRequest distanceBetweenPostCodesRequest) throws Exception {

            validateRequest(distanceBetweenPostCodesRequest);
            File file = readFile();
            createPostCodeMapFromFile(file);

            checkIfPostCodesExists(distanceBetweenPostCodesRequest);

            LocationInfo info1 = postCodeMap.get(distanceBetweenPostCodesRequest.getPostCode1());
            LocationInfo info2 = postCodeMap.get(distanceBetweenPostCodesRequest.getPostCode2());

            log.info("Calculating distance for postal codes: {} and {}",
                distanceBetweenPostCodesRequest.getPostCode1(), distanceBetweenPostCodesRequest.getPostCode2());

            double distance = calculateDistance(info1.getLatitude(), info1.getLongitude(),
                    info2.getLatitude(), info2.getLongitude());

            log.info("Distance calculation result: {}", distance);

            return responseBuilder(info1, info2, distance);
    }

    @NotNull
    private static GetDistanceBetweenPostCodesResponse responseBuilder(LocationInfo info1, LocationInfo info2, double distance) {
        GetDistanceBetweenPostCodesResponse getDistanceBetweenPostCodesResponse = new GetDistanceBetweenPostCodesResponse();
        getDistanceBetweenPostCodesResponse.setDistance(distance);
        getDistanceBetweenPostCodesResponse.setLocationInfo(List.of(info1, info2));
        getDistanceBetweenPostCodesResponse.setUnit(UNIT);
        return getDistanceBetweenPostCodesResponse;
    }

    private void validateRequest(final GetDistanceBetweenPostCodesRequest distanceBetweenPostCodesRequest)
            throws InputValidationException {
        if(!Objects.nonNull(distanceBetweenPostCodesRequest) || distanceBetweenPostCodesRequest.getPostCode1() == null
                || distanceBetweenPostCodesRequest.getPostCode1().isEmpty() ||
                distanceBetweenPostCodesRequest.getPostCode1().isBlank() ||
                distanceBetweenPostCodesRequest.getPostCode2() == null ||
                distanceBetweenPostCodesRequest.getPostCode2().isEmpty() ||
                distanceBetweenPostCodesRequest.getPostCode2().isBlank()) {
            throw new InputValidationException("distanceBetweenPostCodesRequest is invalid");
        }
    }

    private void checkIfPostCodesExists(final GetDistanceBetweenPostCodesRequest distanceBetweenPostCodesRequest)
            throws NonRetryableException
    {
        if(!postCodeMap.containsKey(distanceBetweenPostCodesRequest.getPostCode1()) ||
                !postCodeMap.containsKey(distanceBetweenPostCodesRequest.getPostCode2()))
        {
            throw new NonRetryableException("requested postcodes doesn't exists");
        }
    }

    private void createPostCodeMapFromFile(@NotNull File file) throws IOException {
        postCodeMap = new HashMap<>();

        // Read lines from the file
        try (Stream<String> lines = Files.lines(file.toPath())) {
            lines.skip(1) // Skip the header line if present
                    .forEach(line -> {
                        String[] parts = line.split(",");
                        if (parts.length == 4) {
                            String postcode = parts[1];
                            double latitude = Double.parseDouble(parts[2]);
                            double longitude = Double.parseDouble(parts[3]);

                            LocationInfo location = new LocationInfo();
                            location.setPostalCode(parts[1]);
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);

                            postCodeMap.put(postcode, location);
                        }
                    });
        } catch (IOException e) {
            log.error("Error while creating postcode map: {} | StackTrace: {} ", e.getMessage(),
                    Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    private double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
        // Using Haversine formula!
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians) + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }
}