package com.portfolio.shortest_path;

import java.util.Objects;

public class LatLon {
	private double longitude;
	private double latitude;

	public LatLon(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof LatLon)){
			return false;
		}
		LatLon knownLonLat = (LatLon) other;
		return this.longitude == knownLonLat.longitude &&
				this.latitude == knownLonLat.latitude;
	}

	public double distanceTo(LatLon destination) {
		double earthsRad = 6371;
		
		double dLat = Math.toRadians((destination.latitude - this.latitude));
	    double dLong = Math.toRadians((destination.longitude - this.longitude));

	    double startLat = Math.toRadians(this.latitude);
	    double endLat = Math.toRadians(destination.latitude);

	    double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return earthsRad * c;
	}
	
	double haversine(double val) {
	    return Math.pow(Math.sin(val / 2), 2);
	}
}
