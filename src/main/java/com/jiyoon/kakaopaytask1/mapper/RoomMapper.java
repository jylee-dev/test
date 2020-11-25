package com.jiyoon.kakaopaytask1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jiyoon.kakaopaytask1.model.Room;

@Mapper
public interface RoomMapper {	
	List<Room> selectRoomByRoomId(String roomId);	
}
