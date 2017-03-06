package com.example.dynamic.subsetsum;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Schedule {
	
	private final List<Track> tracks = new ArrayList<>();
	
	public enum TimeUnit {
		
		MINUTES(5) {
			public int calculate(int value) {
				return value / ratio;
			}
		}, 
		LIGHTNING(5) {
			public int calculate(int value) {
				return value * ratio;
			}
		};

		int ratio;
		
		private TimeUnit(int ratio) {
			this.ratio = ratio;
		}
		
		public int convert(int value, TimeUnit convertTo) {
			if(this == convertTo) {
				return value;
			} else {
				return convertTo.calculate(value);
			}
		}
		
		public abstract int calculate (int value);
		
	}
	
	private static class Session {
		
		private final String name;
		private final LocalTime start;
		private final LocalTime end;
		private LocalTime currentEndTime;
		private final Set<Seminar> seminars= new LinkedHashSet<>();
		
		public Session(String name, int maxDuration, TimeUnit durationUnit, LocalTime startsAt) {
			this.name = name;
			this.start = startsAt;
			this.end = startsAt.plusMinutes(durationUnit.convert(maxDuration, TimeUnit.MINUTES));
			this.currentEndTime = startsAt;
		}
		
		public boolean add(Seminar seminar) {
			LocalTime endTime = seminar.getEndTime(currentEndTime);
			if(endTime.isAfter(end)) {
				return false;
			}
			this.currentEndTime = endTime;
			seminars.add(seminar);
			return true;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			LocalTime startTime = start;
			for(Seminar seminar : seminars) {
				builder.append(startTime);
				builder.append(" ");
				builder.append(seminar);
				builder.append("\n");
				startTime = seminar.getEndTime(startTime);
			} 
			return builder.toString();
		}
		
	}
	
	public static class Track {
		private final String id;
		
		public final Session mSession;
		public final Session eSession;
		
		public Track(String id, Session mSession, Session eSession) {
			this.id = id;
			this.mSession = mSession;
			this.eSession = eSession;
		}
		
		public boolean add(Seminar seminar) {
			if(mSession.add(seminar) || eSession.add(seminar)) {
				return true;
			}
			return false;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Track " + id + ":");
			builder.append("\n");
			builder.append(mSession);
			builder.append("12:00PM Lunch");
			builder.append("\n");
			builder.append(eSession);
			return builder.toString();
		}
	}
	
	public void scheduleTrack(List<Seminar> mSeminars, List<Seminar> eSeminars) {
		Track track = new Track(tracks.size() + 1 + "", 
				new Session("morning", 180, TimeUnit.MINUTES, LocalTime.of(9, 0)), 
				new Session("evening", 240, TimeUnit.MINUTES, LocalTime.of(13, 0)));
		tracks.add(track);
		List<Seminar> scheduled = mSeminars.stream()
					   .filter(seminar ->  track.add(seminar))
					   .collect(Collectors.toList());
		if(eSeminars == null){
			return;
		}
		scheduled.addAll(eSeminars.stream()
					   .filter(seminar ->  track.add(seminar))
					   .collect(Collectors.toList()));
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Track track : tracks) {
			builder.append(track);
			builder.append("\n");
		}
		return builder.toString();
	}
	
	
}
