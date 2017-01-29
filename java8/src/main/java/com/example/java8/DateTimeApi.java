package com.example.java8;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.GregorianCalendar;

public class DateTimeApi {
	
	// 1) Temporal extends TemporalAccessor is the super interface 
	//    for all date, time. duration classes
	public void instantAndDuration() {
		Instant start = Instant.now();
		// run algo rithm
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		System.out.println(duration.toMillis());
	}
	
	public void localAndZonedDates() {
		// LocalDate has no time
		LocalDate today = LocalDate.now();
		LocalDate broBday = LocalDate.of(1984, 10, 1);
		LocalDate programmersDay = LocalDate.of(2014, 1, 1).plusDays(255);
		
		// Diff between 2 LocalDate objects is a Period
		broBday.plus(Period.ofYears(1));
		// may not give same result as above for leap year
		broBday.plus(Duration.ofDays(365));
		broBday.plus(Period.ofDays(365));
		
		Period period = broBday.until(programmersDay);
		
		// for time use
		LocalTime.of(0, 1);
		LocalTime.now();
		
		//for Date time
		LocalDateTime.now();
	
	}
	
	// The TemporalAdjusters class provides a number of static
	// methods for common adjustments, such as “the first
	// Tuesday of every month.”
	public void temporaryAdjuster() {
		LocalDate firstTuesday = LocalDate.of(1984, 4, 1).with(
				TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
		
		// custom temporary adjuster
		TemporalAdjuster NEXT_WORKDAY = w -> {
			LocalDate result = (LocalDate) w;
			do {
			result = result.plusDays(1);
			} while (result.getDayOfWeek().getValue() >= 6);
			return result;
		};
		
		NEXT_WORKDAY = TemporalAdjusters.ofDateAdjuster(w -> {
			LocalDate result = w; // no cast
			do {
			result = result.plusDays(1);
			} while (result.getDayOfWeek().getValue() >= 6);
			return result;
		});
		
		LocalDate backToWork = LocalDate.now().with(NEXT_WORKDAY);
	}

	
	public void zonedTime() {
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime nowZoned = now.atZone(ZoneId.of("America/New_York"));
		ZonedDateTime apollo11launch = ZonedDateTime.of(1969, 7, 16, 9, 32, 0, 0,
				ZoneId.of("America/New_York"));
	
		ZonedDateTime meeting = now.atZone(ZoneId.of("America/New_York"));
		// Caution! Won’t work with daylight savings time
		ZonedDateTime nextMeeting = meeting.plus(Duration.ofDays(7));
		// Instead use Period class
		nextMeeting = meeting.plus(Period.ofDays(7));
	}
	
	// predefined formatters, localle specific, custom
	public void dateTimeFormatter() {
		ZonedDateTime apollo11launch = ZonedDateTime.of(1969, 7, 16, 9, 32, 0, 0,
				ZoneId.of("America/New_York"));
		String formatted = DateTimeFormatter.ISO_DATE_TIME.format(apollo11launch);
		// 1969-07-16T09:32:00-05:00[America/New_York]
		
		/**
		 * The standard formatters are mostly intended for machine-readable timestamps. 
		 * To present dates and times to human readers, use a locale-specific formatter. 
		 * There are four styles, SHORT, MEDIUM, LONG, and FULL, for both date andtie
		 */
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
		formatted = formatter.format(apollo11launch);
		// July 16, 1969 9:32:00 AM EDT
		
		// custom
		formatter = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm");
	}
	
	public void interoperabilityWithLegacyCode() {
		// shows to and from
		Date.from(Instant.now()).toInstant();
		Date.valueOf(LocalDate.now()).toLocalDate();
		
		GregorianCalendar.from(ZonedDateTime.now()).toZonedDateTime();
		GregorianCalendar.from(ZonedDateTime.now()).toInstant();
		
		Timestamp.from(Instant.now()).toInstant();
		Timestamp.from(Instant.now()).toLocalDateTime();

		Time.valueOf(LocalTime.now()).toLocalTime();
		
		
	}
}
