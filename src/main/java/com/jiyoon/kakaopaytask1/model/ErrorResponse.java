package com.jiyoon.kakaopaytask1.model;

import com.jiyoon.kakaopaytask1.constant.ApiError;

import lombok.Data;

@Data
public class ErrorResponse {
	final private String status = "ERROR";
	private String error;
	private String message;
	
	public ErrorResponse(ApiError apiError) {
        this.error = apiError.toString();
        this.message = apiError.getDesc();
    }
}
