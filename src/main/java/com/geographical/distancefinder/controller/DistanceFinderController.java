package com.geographical.distancefinder.controller;

import com.geographical.distancefinder.exception.InputValidationException;
import com.geographical.distancefinder.model.request.GetDistanceBetweenPostCodesRequest;
import com.geographical.distancefinder.model.response.GetDistanceBetweenPostCodesResponse;
import com.geographical.distancefinder.service.DistanceFinderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Service Controller
 */
@RestController
@RequestMapping("/api")
public class DistanceFinderController {
    @Autowired
    @Qualifier("distanceFinderBetweenPostCodesService")
    private DistanceFinderService distanceFinderService;

    /**
     * Calculates distance between two postal codes
     * @param distanceBetweenPostCodesRequest (postCode1, postCode2)
     * @return GetDistanceBetweenPostCodesResponse
     */
    @GetMapping("/distance")
    public ResponseEntity<GetDistanceBetweenPostCodesResponse> getDistance(
            final GetDistanceBetweenPostCodesRequest distanceBetweenPostCodesRequest)
    {
        try {
            final GetDistanceBetweenPostCodesResponse dist = distanceFinderService.
                    getDistance(distanceBetweenPostCodesRequest);

            return ResponseEntity.ok(dist);
        } catch (InputValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}