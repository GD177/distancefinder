package com.geographical.distancefinder.model.response;

import com.geographical.distancefinder.model.LocationInfo;
import lombok.Data;

import java.util.List;

/**
 * Distance between postcodes response
 */
@Data
public class GetDistanceBetweenPostCodesResponse {
    private List<LocationInfo> locationInfo;
    private double distance;
    private String unit;
}