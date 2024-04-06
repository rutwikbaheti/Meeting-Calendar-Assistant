package com.calender.assistant.DTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class FreeSlotRequest {
	@NotBlank
	private String employeeId1;
	@NotBlank
	private String employeeId2;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private LocalDate date ;
}
