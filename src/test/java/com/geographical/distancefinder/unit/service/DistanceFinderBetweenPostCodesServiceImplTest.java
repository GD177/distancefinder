package com.geographical.distancefinder.unit.service;

import com.geographical.distancefinder.exception.InputValidationException;
import com.geographical.distancefinder.exception.NonRetryableException;
import com.geographical.distancefinder.model.LocationInfo;
import com.geographical.distancefinder.model.request.GetDistanceBetweenPostCodesRequest;
import com.geographical.distancefinder.model.response.GetDistanceBetweenPostCodesResponse;
import com.geographical.distancefinder.service.DistanceFinderBetweenPostCodesServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class DistanceFinderBetweenPostCodesServiceImplTest {

    @InjectMocks
    private DistanceFinderBetweenPostCodesServiceImpl distanceFinderBetweenPostCodesService;

    @Test
    public void testGetDistanceSuccessCase() throws Exception {
        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();
        request.setPostCode1("AB10 1XG");
        request.setPostCode2("AB11 5QN");

        GetDistanceBetweenPostCodesResponse expectedPostCodesResponse = new GetDistanceBetweenPostCodesResponse();
        expectedPostCodesResponse.setLocationInfo(Arrays.asList(
                new LocationInfo("AB10 1XG",57.144156,-2.114864),
                new LocationInfo("AB11 5QN", 57.142701, -2.093295)
        ));
        expectedPostCodesResponse.setDistance(1.3112226281726593);
        expectedPostCodesResponse.setUnit("km");

        GetDistanceBetweenPostCodesResponse actualResponse = distanceFinderBetweenPostCodesService.getDistance(request);
        Assert.assertEquals(expectedPostCodesResponse, actualResponse);
    }

    @Test(expected = InputValidationException.class)
    public void testGetDistanceValidationFailureCase() throws Exception {
        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();
        GetDistanceBetweenPostCodesResponse actualResponse = distanceFinderBetweenPostCodesService.getDistance(request);
    }

    @Test(expected = NonRetryableException.class)
    public void testGetDistanceInvalidPostCodeFailureCase() throws Exception
    {
        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();
        request.setPostCode1("AB10 1XG");
        request.setPostCode2("AB11");

        GetDistanceBetweenPostCodesResponse
                actualResponse = distanceFinderBetweenPostCodesService.getDistance(request);
    }
}