package com.study.springmvc.case02.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ArrayDateService {

	public List<String> getNames(){
		return Arrays.asList("John" , "Mary" , "Helen") ;
	}
	
	public Map<String, Integer> getFruits(){
		Map<String, Integer> fruits = new LinkedHashMap<>();
		fruits.put("apple", 50);
		fruits.put("香蕉", 30);
		fruits.put("橘子", 20);
		return fruits;
	}
}
