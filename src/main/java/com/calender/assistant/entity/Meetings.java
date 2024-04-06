package com.calender.assistant.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meetings")
public class Meetings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "meeting_id")
    private String meetingId;
    
    @Column(name = "meeting_date") 
    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    private LocalDate meetingDate;

    @Column(name = "meeting_start_time") 
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime meetingStartTime;

    @Column(name = "meeting_end_time")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime meetingEndTime;
}
