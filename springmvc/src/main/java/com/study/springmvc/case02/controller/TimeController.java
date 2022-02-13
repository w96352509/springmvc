package com.study.springmvc.case02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
		//String view = "/WEB-INF/view/case02/show_time.jsp";
		String view = "case02/show_time";
		ModelAndView mav = new ModelAndView();
		mav.addObject("time", time);
		mav.setViewName(view);
		return mav;
	}
	
	@GetMapping("/now2")
	public ModelAndView getCurrentDateAndTime2() {
		String time = timeService.getDateAndTimeStamp();
		String view = "case02/show_time";
		return  new ModelAndView(view , "time" , time);
	}
	
	@GetMapping("/now3")
	public ModelAndView getCurrentDateAndTime3() {
		String time = timeService.getDateAndTimeStamp();
		String view = "case02/show_time";
		return  new ModelAndView(view).addObject("time",time);
	}
	
	@GetMapping("/now4")
	//@ResponseBody(不要加)
	public String getCurrentDateAndTime4(Model model) {
		String time = timeService.getDateAndTimeStamp();
		model.addAttribute("time" , time);
		return "case02/show_time";
	}
}
