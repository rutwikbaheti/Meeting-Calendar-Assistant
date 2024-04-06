package com.calender.assistant.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calender.assistant.DTO.FreeSlotRequest;
import com.calender.assistant.DTO.MeetingRequest;
import com.calender.assistant.DTO.TimeSlot;
import com.calender.assistant.entity.Meetings;
import com.calender.assistant.service.MeetingService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class MeetingController {
	@Autowired
    private MeetingService meetingService;
	
	@Operation(summary="Fetch all meetings data based on employeeId and date")
	@GetMapping("/{employeeId}")
	public ResponseEntity<List<Meetings>> getAllMeetingsByIdAndDate(@PathVariable String employeeId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date)throws DateTimeParseException{
		List<Meetings> meetings = meetingService.getAllMeetingsByIdAndDate(employeeId,date);
		return ResponseEntity.ok(meetings);
	}
	
	@Operation(summary="Schedule a meeting")
	@PostMapping("/schedule")
    public ResponseEntity<String> scheduleMeeting(@Valid @RequestBody MeetingRequest meetingRequest) throws MethodArgumentNotValidException, DateTimeParseException{
        String result = meetingService.scheduleMeeting(meetingRequest);
        return ResponseEntity.ok(result);
    }

	@Operation(summary="Check for available free slots")
    @GetMapping("/freeSlots")
    public ResponseEntity<List<TimeSlot>> findFreeSlots(@Valid @RequestBody FreeSlotRequest freeSlotRequest) throws MethodArgumentNotValidException, DateTimeParseException{
    	System.out.println(freeSlotRequest.toString());
    	List<TimeSlot>  freeSlots = meetingService.findFreeSlots(freeSlotRequest.getEmployeeId1(), freeSlotRequest.getEmployeeId2(),freeSlotRequest.getDate());
        return ResponseEntity.ok(freeSlots);
    }

	@Operation(summary="Get list of participants with conflicts")
    @PostMapping("/checkConflicts")
    public ResponseEntity<List<String>> participantsWithConflicts(@Valid @RequestBody MeetingRequest meetingRequest) throws MethodArgumentNotValidException, DateTimeParseException{
    	List<String> conflictResponse = meetingService.participantsWithConflicts(meetingRequest);
        return ResponseEntity.ok(conflictResponse);
    }
 
}
