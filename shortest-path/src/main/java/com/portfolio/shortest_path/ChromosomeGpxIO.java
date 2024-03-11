package com.portfolio.shortest_path;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.GPX.Writer.Indent;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;

public class ChromosomeGpxIO {

	private PathBuilder pathBuilder = new PathBuilder();
	private String dir = this.pathBuilder.getFilePath("gpx_out/");

	public ChromosomeGpxIO(String dir) {
		this.dir = dir;
	}
	
	public ChromosomeGpxIO() {
	}

	public List<LatLon> readFile(String file) throws IOException {
		StringBuilder fullPath = new StringBuilder(this.dir);
		fullPath.append(file);
		return GPX.read(Path.of(fullPath.toString())).tracks()
				.flatMap(Track::segments)
				.flatMap(TrackSegment::points)
				.map(p -> 
				new LatLon(
						p.getLatitude().doubleValue(), 
						p.getLongitude().doubleValue()
				)
		).toList();
	}
	
	public void writeGpx(Chromosome chromosome) throws IOException {
		String hashCode = Integer.toString(chromosome.hashCode());
		long time = System.currentTimeMillis();
		String timeString = Long.toString(time);
		StringBuilder fileName = new StringBuilder();
		fileName.append(hashCode);
		fileName.append("_");
		fileName.append(timeString);
		fileName.append(".gpx");
		this.writeGpx(chromosome, fileName.toString());
	}

	public void writeGpx(Chromosome chromosome, String fileName) throws IOException {
		List<LatLon> path = chromosome.getPath();
		List<WayPoint> segPoints = path.stream().map(p -> WayPoint.builder()
				     .lat(p.getLat()).lon(p.getLon())
				     .build()).toList();
		
		GPX gpx = GPX.builder()
			.addTrack(track -> track
				.addSegment(segment -> segment
					.points(segPoints)))
		.build();
		Indent indent = new GPX.Writer.Indent("    ");
		StringBuilder fullPath = new StringBuilder(this.dir);
		fullPath.append(fileName);
		GPX.Writer.of(indent).write(gpx, Path.of(fullPath.toString()));
	}
}
