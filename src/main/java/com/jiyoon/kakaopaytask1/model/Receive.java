package com.jiyoon.kakaopaytask1.model;

import lombok.Data;

@Data
public class Receive {
	final private String token;
	final private int seq;
	final private int amount;
	Integer userId;
}