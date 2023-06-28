package com.geographical.distancefinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Location Info model.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfo {
    private String postalCode;
    private double latitude;
    private double longitude;
}