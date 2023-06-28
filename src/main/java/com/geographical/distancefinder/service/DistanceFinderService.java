package com.geographical.distancefinder.service;

import com.geographical.distancefinder.model.request.GetDistanceBetweenPostCodesRequest;
import com.geographical.distancefinder.model.response.GetDistanceBetweenPostCodesResponse;

/**
 * Service API handler interface
 */
public interface DistanceFinderService {
    /**
     *
     * @param distanceBetweenPostCodesRequest : postCode1 , postCode2
     * @return GetDistanceBetweenPostCodesResponse
     * @throws Exception : InputValidationException, NonRetryableException
     */
    GetDistanceBetweenPostCodesResponse getDistance(final GetDistanceBetweenPostCodesRequest distanceBetweenPostCodesRequest) throws Exception;
}