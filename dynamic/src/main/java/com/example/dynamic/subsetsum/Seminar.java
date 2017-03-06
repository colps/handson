package com.example.dynamic.subsetsum;

import java.time.LocalTime;

public class Seminar {
	
	private final String name;
	private final int lightning;

	public Seminar(String name) {
		this.name = name;
		if(name.indexOf("lightning") > 0) {
			this.lightning = 1;
		} else {
			String time = name.replaceAll("[^0-9]+", "");
			this.lightning = Integer.parseInt(time)/5;
		}
	}
	
	public LocalTime getEndTime(LocalTime start) {
		return start.plusMinutes(lightning*5);
	}
	
	public int getLightning() {
		return lightning;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seminar other = (Seminar) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}
