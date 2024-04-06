package com.calender.assistant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.calender.assistant.DTO.FreeSlotRequest;
import com.calender.assistant.DTO.MeetingRequest;
import com.calender.assistant.DTO.TimeSlot;
import com.calender.assistant.controller.MeetingController;
import com.calender.assistant.entity.Meetings;
import com.calender.assistant.service.MeetingService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MeetingControllerTest {
	@InjectMocks
	MeetingController controller;
	
	@Mock
	MeetingService service;
	
	@Test
	public void testGetAllMeetingsByIdAndDate() {
		String employeeId = "1";
		String dateString = "2024-04-02";
        LocalDate date = LocalDate.parse(dateString);
        ResponseEntity<List<Meetings>> result = controller.getAllMeetingsByIdAndDate(employeeId, date);
        
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
        assertNotNull(result.getBody());
	}
	
	@Test
	public void testScheduleMeeting() throws DateTimeParseException, MethodArgumentNotValidException {
		MeetingRequest request = new MeetingRequest();
		request.setEmployeeId("2");
		request.setMeetingId("2");
		request.setDate(LocalDate.parse("2024-04-02"));
		request.setStartTime(LocalTime.parse("12:00:00"));
		request.setEndTime(LocalTime.parse("13:00:00"));
		List<String> participants = new ArrayList<>();
		participants.add("3");
		request.setParticipants(participants);
        ResponseEntity<String> result = controller.scheduleMeeting(request);
        
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
        assertEquals("Meeting Scheduled successfully.",result.getBody());
	}
	
	@Test
	public void testfindFreeSlots() throws DateTimeParseException, MethodArgumentNotValidException {
		FreeSlotRequest request = new FreeSlotRequest();
		request.setEmployeeId1("1");
		request.setEmployeeId2("2");
		request.setDate(LocalDate.parse("2024-04-02"));
        ResponseEntity<List<TimeSlot>> result = controller.findFreeSlots(request);
        
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
        assertNotNull(result.getBody());
	}
	
	@Test
	public void testParticipantsWithConflicts() throws DateTimeParseException, MethodArgumentNotValidException {
		MeetingRequest request = new MeetingRequest();
		request.setEmployeeId("4");
		request.setMeetingId("3");
		request.setDate(LocalDate.parse("2024-04-02"));
		request.setStartTime(LocalTime.parse("12:00:00"));
		request.setEndTime(LocalTime.parse("13:00:00"));
		List<String> participants = new ArrayList<>();
		participants.add("1");
		participants.add("2");
		participants.add("3");
		request.setParticipants(participants);
        ResponseEntity<List<String>> result = controller.participantsWithConflicts(request);
        
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
        assertNotNull(result.getBody());
	}
}
