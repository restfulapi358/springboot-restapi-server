package com.restapi.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ZoomMeetingRequest {
	
	 	private String topic;
	    private int type = 1; // 1 = instant meeting
	    private String start_time;
	    private int duration;
	    private String timezone;

}
