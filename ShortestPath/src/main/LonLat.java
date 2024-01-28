package main;

public class LonLat {
	public double longitude;
	public double latitude;

	public LonLat(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
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
