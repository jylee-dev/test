package com.jiyoon.kakaopaytask1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jiyoon.kakaopaytask1.model.Seed;

@Mapper
public interface SeedMapper {	
	Seed selectSeedByToken(String token);
	void insertSeed(Seed seed);
}
