package com.jiyoon.kakaopaytask1.model;

import lombok.Data;

@Data
public class SuccessResponse {	
	final private String status = "SUCCESS";
	private String message;
	private Object data;
}