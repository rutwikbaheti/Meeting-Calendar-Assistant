package com.calender.assistant.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.calender.assistant.entity.Meetings;

public interface MeetingsRepository extends JpaRepository<Meetings, Long>{
	
	Optional<List<Meetings>> findAllByEmployeeIdAndMeetingDate(String employee1Id,LocalDate meetingDate);

	Optional<List<Meetings>> findAllByMeetingDateAndMeetingStartTimeLessThanEqualAndMeetingEndTimeGreaterThanEqualAndEmployeeIdIn(LocalDate date,LocalTime endTime,LocalTime startTime,List<String> employeeIds
    );
}
