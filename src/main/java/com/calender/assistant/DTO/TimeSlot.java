package com.calender.assistant.DTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
	@DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
	@DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
}

