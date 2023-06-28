package com.geographical.distancefinder.controller;

import com.geographical.distancefinder.exception.InputValidationException;
import com.geographical.distancefinder.model.LocationInfo;
import com.geographical.distancefinder.model.request.GetDistanceBetweenPostCodesRequest;
import com.geographical.distancefinder.model.response.GetDistanceBetweenPostCodesResponse;
import com.geographical.distancefinder.service.DistanceFinderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DistanceFinderControllerTest {

    @InjectMocks private DistanceFinderController distanceFinderController;
    @Mock private DistanceFinderService distanceFinderService;

    @Test
    public void getDistanceSuccess() throws Exception {
        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();
        request.setPostCode1("AB10 1XG");
        request.setPostCode2("AB11 5QN");

        GetDistanceBetweenPostCodesResponse postCodesResponse = new GetDistanceBetweenPostCodesResponse();
        postCodesResponse.setLocationInfo(Arrays.asList(
                new LocationInfo("AB10 1XG",57.144156,-2.114864),
                new LocationInfo("AB11 5QN", 57.142701, -2.093295)
        ));
        postCodesResponse.setDistance(2);
        postCodesResponse.setUnit("km");

        ResponseEntity<GetDistanceBetweenPostCodesResponse> expectedResponse = ResponseEntity.ok(postCodesResponse);

        Mockito.when(distanceFinderService.getDistance(any())).thenReturn(postCodesResponse);
        ResponseEntity<GetDistanceBetweenPostCodesResponse> actualResponse = distanceFinderController.getDistance(request);
        Assert.assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void getDistanceIOException() throws Exception {
        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();
        request.setPostCode1("AB10 7JB");
        request.setPostCode2("AB11 5QN");

        ResponseEntity<GetDistanceBetweenPostCodesResponse> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Mockito.when(distanceFinderService.getDistance(any())).thenThrow(new IOException("test exception"));
        ResponseEntity<GetDistanceBetweenPostCodesResponse> actualResponse = distanceFinderController.getDistance(request);
        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getDistanceInputValidationException() throws Exception {
        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();

        ResponseEntity<GetDistanceBetweenPostCodesResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Mockito.when(distanceFinderService.getDistance(any())).thenThrow(new InputValidationException("test exception"));
        ResponseEntity<GetDistanceBetweenPostCodesResponse> actualResponse = distanceFinderController.getDistance(request);
        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getDistanceInvalidPostCodeException() throws Exception {
        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();
        request.setPostCode1("AB10 1XG");
        request.setPostCode2("AB11");

        ResponseEntity<GetDistanceBetweenPostCodesResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Mockito.when(distanceFinderService.getDistance(any())).thenThrow(new InputValidationException("test exception"));
        ResponseEntity<GetDistanceBetweenPostCodesResponse> actualResponse = distanceFinderController.getDistance(request);
        Assert.assertEquals(expectedResponse, actualResponse);
    }
}