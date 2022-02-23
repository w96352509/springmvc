package com.study.springmvc.case03.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;
import com.study.springmvc.case03.entity.Exam;
import com.study.springmvc.case03.entity.ExamSubject;

@Service
public class ExamService {

	// CopyOnWriteArrayList 是執行緒安全的集合-適合多執行緒操作
	// ArrayList 是非執行緒安全的集合-適合單緒操作
	// 因為預設的情況下 Spring 會將 ExamController 定義為 Singleton（單一實體）
	// 所以可以使用 CopyOnWriteArrayList 來作為多執行緒資料操作的集合類
	private List<Exam> exams = new CopyOnWriteArrayList<>();
	private List<ExamSubject> examSubjects = new CopyOnWriteArrayList<>();
	private List<ExamSubject> examSlots = new CopyOnWriteArrayList<>();
	private List<ExamSubject> examPay = new CopyOnWriteArrayList<>();
	{
		examSubjects.add(new ExamSubject("808", "JavaSE 8 OCP I"));
		examSubjects.add(new ExamSubject("809", "JavaSE 8 OCP II"));
		examSubjects.add(new ExamSubject("819", "JavaSE 11 OCP "));
		examSubjects.add(new ExamSubject("900", "JavaEE 7 OCP"));
		examSlots.add(new ExamSubject("A", "早上"));
		examSlots.add(new ExamSubject("B", "下午"));
		examSlots.add(new ExamSubject("C", "晚上"));
		examPay.add(new ExamSubject("已繳", "true"));
		examPay.add(new ExamSubject("未繳", "falase"));
	}
	
	//提供 controller 選項
	public List<ExamSubject> queryExamSubjectList(){
		return examSubjects;
	}
	
	//提供 controller 選項
	public List<ExamSubject> queryExamSlotList(){
		   
			return examSlots;
	}
	
	//提供 controller 選項
		public List<ExamSubject> queryExamPayList(){
			   
				return examPay;
		}
	
	//首頁(查詢多筆)
	public List<Exam> query() {
		return exams;
	}
	
	// 查詢單筆
		public Optional<Exam> get(int index) {
			if(index < 0 || exams.size() == 0 || index >= exams.size()) {
				return Optional.ofNullable(null); //ofNullable 允許 null ;
			}
			return Optional.ofNullable(exams.get(index));
		}
	
	//新增
	public synchronized Boolean add(Exam exam) {
		int previousSize = exams.size(); //原大小
		exams.add(exam);
		int nextSize = exams.size();     //新增後的大小
		return nextSize > previousSize;
	}
	
	//修改
	public synchronized Boolean update( int index , Exam exam) {
		if(index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		exams.set(index, exam);
		return true;
	}
	
	//刪除
	public synchronized Boolean delete(int index) {
		if(index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		exams.remove(index);
		return true;
	}
	
	// 修改 ExamNote 單一欄位資料
		public synchronized Boolean updateExamNote(int index, String examNote) {
			if(index < 0 || exams.size() == 0 || index >= exams.size()) {
				return false;
			}
			Exam exam = exams.get(index);
			exam.setExamNote(examNote);
			exams.set(index, exam);
			return true;
		}
}
