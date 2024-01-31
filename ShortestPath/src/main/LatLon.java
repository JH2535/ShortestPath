package main;

import java.util.Objects;

public class LatLon {
	public double longitude;
	public double latitude;

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
}
