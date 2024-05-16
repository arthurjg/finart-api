package br.com.artsoft.finart.model.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTimeUtil {
	
	private static final String DEFAULT_OFFSET = "-03:00"; 
	
	public static Date getByLocalDateTime(LocalDateTime dateTime) {
		Instant instant = dateTime.toInstant(ZoneOffset.of(DEFAULT_OFFSET));
		return Date.from(instant);
	}
	
	public static LocalDateTime getFirstHourByLocalDate(LocalDate date) {		
		return date.atTime(LocalTime.of(0, 0, 0));		 
	}

}
