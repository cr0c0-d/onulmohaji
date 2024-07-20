package me.croco.onulmohaji.util;

import me.croco.onulmohaji.facility.domain.Facility;

public class Haversine {
    public Double getDistance(Double latitude_1, Double longitude_1, Double latitude_2, Double longitude_2) {
        return 6371 * Math.acos(Math.cos(Math.toRadians(latitude_1)) * Math.cos(Math.toRadians(latitude_2)) * Math.cos(Math.toRadians(longitude_2) - Math.toRadians(longitude_1)) + Math.sin(Math.toRadians(latitude_1)) * Math.sin(Math.toRadians(latitude_2)));
    }

    public Double getFacilityDistance(Double latitude, Double longitude, Facility facility) {
        return getDistance(latitude, longitude, facility.getLatitude(), facility.getLongitude());
    }
}
