package com.geographical.distancefinder.integration;

import com.geographical.distancefinder.controller.DistanceFinderController;
import com.geographical.distancefinder.model.LocationInfo;
import com.geographical.distancefinder.model.request.GetDistanceBetweenPostCodesRequest;
import com.geographical.distancefinder.model.response.GetDistanceBetweenPostCodesResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DistanceFinderController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceFinderControllerIT {
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private GetDistanceBetweenPostCodesRequest request;
    private HttpEntity<GetDistanceBetweenPostCodesRequest> entity;

    @Before
    public void setup() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<>(request, headers);
        request = new GetDistanceBetweenPostCodesRequest();
    }

    @Test
    public void testGetDistanceSuccess() throws Exception {
        request.setPostCode1("AB10 1XG");
        request.setPostCode2("AB11 5QN");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(createURLWithPort())
                .queryParam("postCode1", request.getPostCode1())
                .queryParam("postCode2", request.getPostCode2());

        URI uri = builder.build().encode().toUri();

        GetDistanceBetweenPostCodesResponse expected = new GetDistanceBetweenPostCodesResponse();
        expected.setLocationInfo(List.of(
                new LocationInfo("AB10 1XG",57.144156,-2.114864),
                new LocationInfo("AB11 5QN", 57.142701, -2.093295)
        ));
        expected.setDistance(1.3112226281726593);
        expected.setUnit("km");

        ResponseEntity<GetDistanceBetweenPostCodesResponse> response = restTemplate.exchange(
                uri, HttpMethod.GET, entity , GetDistanceBetweenPostCodesResponse.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(expected, response.getBody());
    }

    @Test
    public void testGetDistanceInputValidationException() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(createURLWithPort())
                .queryParam("postCode1", request.getPostCode1())
                .queryParam("postCode2", request.getPostCode2());

        URI uri = builder.build().encode().toUri();

        ResponseEntity<GetDistanceBetweenPostCodesResponse> response = restTemplate.exchange(
                uri, HttpMethod.GET, entity , GetDistanceBetweenPostCodesResponse.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetDistanceInvalidPostCodeException() throws Exception {
        request.setPostCode1("AB10 1XG");
        request.setPostCode2("AB11");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(createURLWithPort())
                .queryParam("postCode1", request.getPostCode1())
                .queryParam("postCode2", request.getPostCode2());

        URI uri = builder.build().encode().toUri();

        ResponseEntity<GetDistanceBetweenPostCodesResponse> response = restTemplate.exchange(
                uri, HttpMethod.GET, entity , GetDistanceBetweenPostCodesResponse.class);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private String createURLWithPort() {
        return "http://localhost:8080/api/distance";
    }
}