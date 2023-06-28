package com.geographical.distancefinder.controller;

import com.geographical.distancefinder.model.LocationInfo;
import com.geographical.distancefinder.model.request.GetDistanceBetweenPostCodesRequest;
import com.geographical.distancefinder.model.response.GetDistanceBetweenPostCodesResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DistanceFinderController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceFinderControllerIT {
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testRetrieveStudentCourse() throws Exception {

        GetDistanceBetweenPostCodesRequest request = new GetDistanceBetweenPostCodesRequest();
        request.setPostCode1("AB10 1XG");
        request.setPostCode2("AB11 5QN");

        HttpEntity<GetDistanceBetweenPostCodesRequest> entity =
                new HttpEntity<>(request);
        GetDistanceBetweenPostCodesResponse expected = new GetDistanceBetweenPostCodesResponse();
        expected.setLocationInfo(Arrays.asList(
                new LocationInfo("AB10 1XG",57.144156,-2.114864),
                new LocationInfo("AB11 5QN", 57.142701, -2.093295)
        ));
        expected.setDistance(2);
        expected.setUnit("km");

        ResponseEntity<GetDistanceBetweenPostCodesResponse> response = restTemplate.exchange(
                createURLWithPort(), HttpMethod.GET, entity, GetDistanceBetweenPostCodesResponse.class);

        // Assert the response
        Assert.assertEquals(200, response.getStatusCode().value());
    }

    private String createURLWithPort() {
        return "http://localhost:8080/api/distance";
    }
}
