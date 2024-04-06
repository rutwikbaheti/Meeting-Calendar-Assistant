package com.calender.assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:messages.properties")
public class MeetingCalendarAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingCalendarAssistantApplication.class, args);
	}

}
