package com.jiyoon.kakaopaytask1.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiyoon.kakaopaytask1.constant.ApiError;
import com.jiyoon.kakaopaytask1.exception.ApiException;
import com.jiyoon.kakaopaytask1.mapper.ReceiveMapper;
import com.jiyoon.kakaopaytask1.mapper.RoomMapper;
import com.jiyoon.kakaopaytask1.mapper.SeedMapper;
import com.jiyoon.kakaopaytask1.model.Receive;
import com.jiyoon.kakaopaytask1.model.SuccessResponse;
import com.jiyoon.kakaopaytask1.model.Room;
import com.jiyoon.kakaopaytask1.model.Seed;

@Service
public class ReceiveService {
	@Autowired
	SeedMapper seedMapper;
	@Autowired
	ReceiveMapper receiveMapper;
	@Autowired
	RoomMapper roomMappser;
	@Autowired
	ComService comService;

	@Transactional
	public SuccessResponse receive(String token, int userId) throws ApiException {		
		Seed seed = seedMapper.selectSeedByToken(token);
		
		if (userId == seed.getUserId()) {			
			throw new ApiException(ApiError.RECEIVE_YOURS);
		}
		
		if (comService.getPeriod(seed.getRegDate(), "m") > 10) {
			throw new ApiException(ApiError.RECEIVE_PERIOD_OVER);
		}
		
		if (!isSameRoomUser(seed.getRoomId(), userId)) {
			throw new ApiException(ApiError.NOT_SAME_ROOM_USER);
		}	
		
		int receivedAmount = -1;
		for (Receive receive : receiveMapper.selectReceiveListByToken(token)) {
			if (receive.getUserId() == null || receive.getUserId().equals(null)) {
				receive.setUserId(userId);
				receiveMapper.updateUserIdBySeq(receive);
				receivedAmount = receive.getAmount();
				break;
			} else if (receive.getUserId() == userId) {
				throw new ApiException(ApiError.RECEIVE_DUPLICATE);
			}
		}
		
		if (receivedAmount == -1) {
			throw new ApiException(ApiError.RECEIVE_FINISH);
		}
		
		try {
			// response data
			HashMap<String, Object> data = new HashMap<>();
			data.put("received amount", receivedAmount);
			
			// response
			SuccessResponse sResponse = new SuccessResponse();					
			sResponse.setMessage("receive success!");
			sResponse.setData(data);
			return sResponse;
			
		} catch (Exception e) {
			throw new ApiException(ApiError.RECEIVE_ERROR, e.getLocalizedMessage());
		}
	}
		
	public boolean isSameRoomUser(String roomId, int userId) {		
		for (Room room : roomMappser.selectRoomByRoomId(roomId)) {
			if (room.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}
}
