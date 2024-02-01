package test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import main.LatLon;

public class LatLonTest {

	@Test
	public void testDistance() {
		LatLon newYork = new LatLon(40.714268, -74.005974);
		LatLon losAng = new LatLon(34.0522, -118.2437);
		
		double actualDistance = newYork.distanceTo(losAng);
		
		double expectedDistance = 3935700;
		
		assertTrue(Math.abs(actualDistance - expectedDistance) < 50);
	
	}
}

