package com.study.springmvc.case03.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.study.springmvc.case03.entity.Exam;
import com.study.springmvc.case03.service.ExamService;

@Controller
@RequestMapping("/case03/exam")
public class ExamController {

	@Autowired
	private ExamService examService;
	
	//首頁(查詢全)
	@GetMapping("/")
	public String index(@ModelAttribute Exam exam , Model model) {
		model.addAttribute("_method" ,"POST"); // 預設新增為post
		model.addAttribute("exams" , examService.query());
		model.addAttribute("examSubjects", examService.queryExamSubjectList());
		model.addAttribute("examSlot", examService.queryExamSlotList());
		model.addAttribute("examPay" , examService.queryExamPayList());
		return "case03/exam";
	}
	
	//單筆
	@GetMapping("/{index}")
	public String get(@PathVariable("index") int index , Model model) {
		Optional<Exam> optExam = examService.get(index);
		if (optExam.isPresent()) { //如果有資料
			model.addAttribute("_method" ,"PUT"); //修改PUT
			model.addAttribute("exams" , examService.query());
			model.addAttribute("exam" , optExam.get());
			model.addAttribute("examSubjects", examService.queryExamSubjectList());
			model.addAttribute("examSlot", examService.queryExamSlotList());
			model.addAttribute("examPay" , examService.queryExamPayList());
			return "case03/exam";
		}
		    //沒找到資料 , 應該要透過統一處理機制來進行
		    return "redirect:./";
	}
	
	@PostMapping("/")
	public String add(Exam exam) { 
		examService.add(exam);
		return "redirect:./";
	}
	
	@PutMapping("/{index}") //哪一筆 Exam
	public String update(Exam exam , @PathVariable("index") int index) {
		examService.update(index, exam);
		return "redirect:./";
	}
	
	@PutMapping("/{index}/exam_note")
	public String updateExamNote(@PathVariable("index") int index, Exam exam) {
		examService.updateExamNote(index, exam.getExamNote());
		return "redirect:../";
	}
	
	@DeleteMapping("/{index}")
	public String delete(@PathVariable("index") int index) {
		examService.delete(index);
		return "redirect:./";
	}
	
}
