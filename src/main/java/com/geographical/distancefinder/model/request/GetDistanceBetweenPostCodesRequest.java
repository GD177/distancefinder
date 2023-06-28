package com.geographical.distancefinder.model.request;

import lombok.Data;

/**
 * Distance between postcodes request
 */

@Data
public class GetDistanceBetweenPostCodesRequest {
    private String postCode1;
    private String postCode2;
}