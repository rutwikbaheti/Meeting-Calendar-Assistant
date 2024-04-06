package com.calender.assistant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calender.assistant.DTO.MeetingRequest;
import com.calender.assistant.DTO.TimeSlot;
import com.calender.assistant.entity.Meetings;
import com.calender.assistant.repository.MeetingsRepository;

@Service
public class MeetingService {
	
	@Autowired
	MeetingsRepository repository;
	
	public List<Meetings> getAllMeetingsByIdAndDate(String employeeId, LocalDate date) {
		// fetch all meetings of employee 
		Optional<List<Meetings>> meetingsOptional =  repository.findAllByEmployeeIdAndMeetingDate(employeeId, date);
		if(meetingsOptional.isPresent()) {
			return meetingsOptional.get();
		}
		return null;
	}
	
	public String scheduleMeeting(MeetingRequest meetingRequest) {
        List<Meetings> lst = new ArrayList<>();
        Meetings meeting = new Meetings();
        meeting.setEmployeeId(meetingRequest.getEmployeeId());
        meeting.setMeetingDate(meetingRequest.getDate());
        meeting.setMeetingStartTime(meetingRequest.getStartTime());
        meeting.setMeetingEndTime(meetingRequest.getEndTime());
        meeting.setMeetingId(meetingRequest.getMeetingId());
        lst.add(meeting);
        for(String id : meetingRequest.getParticipants()) {
        	Meetings meetings = new Meetings();
            meetings.setEmployeeId(id);
            meetings.setMeetingDate(meetingRequest.getDate());
            meetings.setMeetingStartTime(meetingRequest.getStartTime());
            meetings.setMeetingEndTime(meetingRequest.getEndTime());
            meetings.setMeetingId(meetingRequest.getMeetingId());
            lst.add(meetings);
        }
        repository.saveAll(lst);
        return "Meeting Scheduled successfully.";
    }
	
    public List<TimeSlot> findFreeSlots(String employee1Id, String employee2Id, LocalDate date) {
    	
    	// fetch all meetings of employee 2
    	Optional<List<Meetings>> employee1MeetingsOptional = repository.findAllByEmployeeIdAndMeetingDate(employee1Id,date);
    	List<Meetings> employee1Meetings = new ArrayList<>();
    	if(employee1MeetingsOptional.isPresent()) {
    		employee1Meetings = employee1MeetingsOptional.get();
    	}
    	
    	// fetch all meetings of employee 2
    	Optional<List<Meetings>> employee2MeetingsOptional = repository.findAllByEmployeeIdAndMeetingDate(employee2Id,date);
    	List<Meetings> employee2Meetings = new ArrayList<>();
    	if(employee2MeetingsOptional.isPresent()) {
    		employee2Meetings = employee2MeetingsOptional.get();
    	}
    	
    	// Combine and sort meetings
    	List<Meetings> allMeetings = new ArrayList<>();
    	allMeetings.addAll(employee1Meetings);
    	allMeetings.addAll(employee2Meetings);
    	allMeetings.sort(Comparator.comparing(Meetings::getMeetingStartTime));
    	allMeetings.forEach(m -> System.out.println(m.getMeetingStartTime()+" "+m.getMeetingEndTime()));
    	System.out.println();
    	
    	// Merge overlapping meetings
    	List<Meetings> mergedMeetings = new ArrayList<>();
    	Meetings currentMeeting = allMeetings.get(0);
    	
    	for (Meetings meeting : allMeetings) {
    	  // If current meeting's end time is equal or after next meeting's start time - merge overlapping meetings
    	  if (currentMeeting.getMeetingEndTime().equals(meeting.getMeetingStartTime()) || currentMeeting.getMeetingEndTime().isAfter(meeting.getMeetingStartTime())) {
    		  currentMeeting.setMeetingEndTime(meeting.getMeetingEndTime());
    	  } else {
    	    //add current meeting to list and update current meeting to next meeting
    		  mergedMeetings.add(currentMeeting);
    		  currentMeeting = meeting;
    	    }
    	}
    	mergedMeetings.add(currentMeeting);
    	mergedMeetings.forEach(m -> System.out.println(m.getMeetingStartTime()+" "+m.getMeetingEndTime()));
    	
    	//Day start time
    	LocalTime startTime = LocalTime.of(9,00,00);
    	//Day end time
    	LocalTime endTime = LocalTime.of(18,00,00);
    	//free slots
    	List<TimeSlot> freeSlots = new ArrayList<>();
    	for (int i = 0; i < mergedMeetings.size(); i++) {
    	  currentMeeting = mergedMeetings.get(i);
    	  freeSlots.add(new TimeSlot(startTime, currentMeeting.getMeetingStartTime()));
    	  startTime = currentMeeting.getMeetingEndTime();
    	}
    	freeSlots.add(new TimeSlot(startTime, endTime));
    	
        return freeSlots;
    }

    public List<String> participantsWithConflicts(MeetingRequest meetingRequest) {
        // Check conflicts for each participant
        Optional<List<Meetings>> participantMeetingsOptional = repository.findAllByMeetingDateAndMeetingStartTimeLessThanEqualAndMeetingEndTimeGreaterThanEqualAndEmployeeIdIn(meetingRequest.getDate(),meetingRequest.getStartTime(),meetingRequest.getEndTime(),meetingRequest.getParticipants());
       
        List<String> participantsWithConflicts = new ArrayList<>();
        if(participantMeetingsOptional.isPresent()) {
        	participantsWithConflicts = participantMeetingsOptional.get().stream().map(Meetings::getEmployeeId).collect(Collectors.toList());
        }
        return participantsWithConflicts;
    }
}
