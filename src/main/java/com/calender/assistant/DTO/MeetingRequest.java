package com.calender.assistant.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeetingRequest {
	@NotBlank(message = "{employeeId.not.blank}")
	private String employeeId;
	@NotBlank(message = "{meetingId.not.blank}")
	private String meetingId;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private LocalDate date;
	@DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime  startTime;
	@DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
	@NotEmpty(message = "{participants.list.not.empty}")
	private List<String> participants;
}
