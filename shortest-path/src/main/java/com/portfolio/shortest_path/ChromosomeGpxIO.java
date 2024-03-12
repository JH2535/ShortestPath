package com.portfolio.shortest_path;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.GPX.Writer.Indent;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;

public class ChromosomeGpxIO extends ChromosomeWriter {

	private PathBuilder pathBuilder = new PathBuilder();
	private String dir = this.pathBuilder.getFilePath("gpx_out/");

	public ChromosomeGpxIO(String dir) {
		this.dir = dir;
		this.fileExtension = "gpx";
	}
	
	public ChromosomeGpxIO() {
		this.fileExtension = "gpx";
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
	
	
	@Override
	public void write(Chromosome chromosome, String fileName) throws IOException {
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
