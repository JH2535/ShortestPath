package main;

import java.util.Objects;

public class LonLat {
	public double longitude;
	public double latitude;

	public LonLat(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof LonLat)){
			return false;
		}
		LonLat knownLonLat = (LonLat) other;
		return this.longitude == knownLonLat.longitude &&
				this.latitude == knownLonLat.latitude;
	}
}
