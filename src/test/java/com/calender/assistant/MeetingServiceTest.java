package com.calender.assistant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.calender.assistant.DTO.MeetingRequest;
import com.calender.assistant.DTO.TimeSlot;
import com.calender.assistant.entity.Meetings;
import com.calender.assistant.repository.MeetingsRepository;
import com.calender.assistant.service.MeetingService;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MeetingServiceTest {
	@InjectMocks
	MeetingService service;
	
	@Mock
	MeetingsRepository repository;
	
	@Test
	public void testGetAllMeetingsByIdAndDate() {
		String employeeId = "1";
		String dateString = "2024-04-02";
        LocalDate date = LocalDate.parse(dateString);
        
        List<Meetings> meetingsList = new ArrayList<>();
        Meetings meeting = new Meetings();
        meeting.setId(1L);
        meeting.setEmployeeId("1");
        meeting.setMeetingId("1");
        meeting.setMeetingDate(LocalDate.parse("2024-04-02"));
        meeting.setMeetingStartTime(LocalTime.parse("11:00:00"));
        meeting.setMeetingEndTime(LocalTime.parse("12:00:00"));
        meetingsList.add(meeting);
        when(repository.findAllByEmployeeIdAndMeetingDate(employeeId, date)).thenReturn(Optional.of(meetingsList));
        List<Meetings> result = service.getAllMeetingsByIdAndDate(employeeId, date);
        
        assertNotNull(result);
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
		
        String result = service.scheduleMeeting(request);
       
        assertEquals("Meeting Scheduled successfully.",result);
	}
	
	@Test
	public void testfindFreeSlots() throws DateTimeParseException, MethodArgumentNotValidException {
		String employeeId1 = "1";
		String employeeId2 = "2";
		LocalDate date = LocalDate.parse("2024-04-02");
		
		List<Meetings> meetingsList = new ArrayList<>();
        Meetings meeting = new Meetings();
        meeting.setId(1L);
        meeting.setEmployeeId("1");
        meeting.setMeetingId("1");
        meeting.setMeetingDate(LocalDate.parse("2024-04-02"));
        meeting.setMeetingStartTime(LocalTime.parse("11:00:00"));
        meeting.setMeetingEndTime(LocalTime.parse("12:00:00"));
        meetingsList.add(meeting);
        when(repository.findAllByEmployeeIdAndMeetingDate(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(meetingsList));
        
        List<TimeSlot> result = service.findFreeSlots(employeeId1,employeeId2,date);
  
        assertNotNull(result);
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
		
		List<Meetings> meetingsList = new ArrayList<>();
        Meetings meeting1 = new Meetings();
        meeting1.setId(1L);
        meeting1.setEmployeeId("2");
        meeting1.setMeetingId("1");
        meeting1.setMeetingDate(LocalDate.parse("2024-04-02"));
        meeting1.setMeetingStartTime(LocalTime.parse("11:00:00"));
        meeting1.setMeetingEndTime(LocalTime.parse("12:00:00"));
        meetingsList.add(meeting1);
        Meetings meeting2 = new Meetings();
        meeting2.setId(1L);
        meeting2.setEmployeeId("2");
        meeting2.setMeetingId("1");
        meeting2.setMeetingDate(LocalDate.parse("2024-04-02"));
        meeting2.setMeetingStartTime(LocalTime.parse("11:00:00"));
        meeting2.setMeetingEndTime(LocalTime.parse("12:00:00"));
        meetingsList.add(meeting2);
		when(repository.findAllByMeetingDateAndMeetingStartTimeLessThanEqualAndMeetingEndTimeGreaterThanEqualAndEmployeeIdIn(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(Optional.ofNullable(meetingsList));
        
		List<String> result = service.participantsWithConflicts(request);
        
        assertNotNull(result);
	}
}
