package com.study.springmvc.case02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.study.springmvc.case02.service.TimeService;

@Controller
@RequestMapping("/case02/time")
public class TimeController {
	
	@Autowired
	private TimeService timeService;
	
	@GetMapping("/now")
	public ModelAndView getCurrentDateAndTime() {
		String time = timeService.getDateAndTimeStamp();
		String view = "/WEB-INF/view/case02/show_time.jsp";
		ModelAndView mav = new ModelAndView();
		mav.addObject("time", time);
		mav.setViewName(view);
		return mav;
	}
}
