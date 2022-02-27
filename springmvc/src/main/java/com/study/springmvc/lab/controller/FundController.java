package com.study.springmvc.lab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.springmvc.lab.entity.Fund;
import com.study.springmvc.lab.repository.FundDao;

@RestController
@RequestMapping("/lab/fund")
public class FundController {
	
	@Autowired
	private FundDao fundDao;
	
	@GetMapping("/")
	public List<Fund> index() {
		return fundDao.queryAll();
	}
	
	@GetMapping("/{fid}")
	public Fund get(@PathVariable("fid") Integer fid) {
		return fundDao.get(fid);
	}
	
	@PostMapping("/")
	public int add(@RequestBody Fund fund) {
		return fundDao.add(fund);
	}
	
	@PutMapping("/")
	public int update(@RequestBody Fund fund) {
		return fundDao.update(fund);
	}
	
	@DeleteMapping("/{fid}")
	public int delete(@PathVariable("fid") Integer fid) {
		return fundDao.delete(fid);
	}
	
}
