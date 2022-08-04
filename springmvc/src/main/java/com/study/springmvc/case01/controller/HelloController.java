package com.study.springmvc.case01.controller;

import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springmvc.case01.enitity.User;

@Controller
//Base路徑:/case01/hello
@RequestMapping("/case01/hello")
public class HelloController {
	// 1.取得字串資料
	// 路徑:/welcome
	@RequestMapping("/welcome")
	@ResponseBody
	public String welcome() {
		return "Hello SpringMVC" + new Date();
	}

	// 2.帶參數(?xx=xx 配置 @RequestParam)
	// 路徑:/sayhi?name=vic&age=18
	@RequestMapping(value = { "/sayhi", "hi" }, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String sayhi(@RequestParam(value = "name") String name,
			@RequestParam(value = "age", required = false, defaultValue = "0") Integer age) {

		return String.format("Hi %s %d", name, age);
	}

	@GetMapping(value = "/bmi")
	@ResponseBody
	public String bmi(@RequestParam(value = "h", required = false) Optional<Double> h,
			@RequestParam(value = "w", required = false) Optional<Double> w) {
		String result = "None";
		if (h.isPresent() && w.isPresent()) {
			double bmi = w.get() / Math.pow(h.get() / 100, 2);
			result = String.format("%.2f", bmi);
		}
		return String.format("bmi = %s", result);
	}

	/*
	 * PathVariable 路徑：/exam/75 -> 結果：75 pass 路徑：/exam/45 -> 結果：45 fail
	 */
	@RequestMapping(value = "/exam/{score}")
	@ResponseBody
	public String exam(@PathVariable("score") Integer score) {
		return score + " " + ((score >= 60)? "Pass" : "Fail");
	}

	/*
	 * Lab: PathVariable + RequestParam 
	 * 路徑：/calc/add?x=30&y=20 -> 結果：50
	 * 路徑：/calc/sub?x=30&y=20 -> 結果：10 
	 * 路徑：/calc/sub?x=0&y=20 -> 結果：-20 
	 * 路徑：/calc/add -> 結果：0 
	 * 路徑：/calc/div ->
	 * 結果："None" <- 無此路徑
	 */

	@RequestMapping(value = "/calc/{exp}")
	@ResponseBody
	public String calc(@PathVariable("exp") String exp,
			           @RequestParam(value = "x", required = false , defaultValue = "0") Optional<Integer> x,
			           @RequestParam(value = "y", required = false , defaultValue = "0") Optional<Integer> y) {
		if(x.isPresent() && y.isPresent()) {
			switch (exp) {
				case "add":
					return x.get() + y.get() + "";
				case "sub":
					return x.get() - y.get() + "";
				default:
					return "exp value error";
			}
		}
		return "0";
	}
	/*
	 * 常見的 PathVariable 萬用字元 * 任意多字, ? 任意一字
	 * */
	@GetMapping("/any/name*/java?")
	@ResponseBody
	public String any() {
		return "Any";
	}
	
	/*
	 * 得到多筆資料
	 * 路徑：/age?a=18&a=19&a=20 
	 * 結果：[18, 19, 20] , age of average = 19
	 * */
	@RequestMapping("/age")
	@ResponseBody
	public String age(@RequestParam("a") List<Integer> ageList) {
		double avg = ageList.stream().mapToInt(age -> age).average().getAsDouble();
		return String.format("%s , age of average = %d", ageList, (int)avg);
	}
	/* Lab
	 * 得到多筆 score 資料
     * 網址輸入：/max?score=80&score=100&score=50
     * 網址輸入：/min?score=80&score=100&score=50
     * 結果得到：max score = 100
	 * 結果得到：min score = 80
	 * 建議使用：IntSummaryStatistics
	 * */
	  @RequestMapping("/{opt}")
	  @ResponseBody
	  public String maxAndMin(@PathVariable(value = "opt")String opt ,
			                 @RequestParam(value = "score")List<Integer> scores) {
		  String str = "%s score = %d";
		  //取得大小平均
		  IntSummaryStatistics stat = scores.stream().mapToInt(score ->score).summaryStatistics();
		  switch (opt) {
			case "max":
				str = String.format(str, opt, stat.getMax());
				break;
			case "min":
				str = String.format(str, opt, stat.getMin());
				break;
			case "avg":
				str = String.format(str, opt, stat.getAverage());
				break;
			case "sum":
				str = String.format(str, opt, stat.getSum());
				break;	
			default:
				str = "None";
				break;
		}
		  return str;
	  }
	  
	  /*
		 * Java pojo 物件
		 * 網址輸入：/user?name=John&age=18
		 * */
		@RequestMapping("/user")
		@ResponseBody
		//public String getUser(@Valid User user) {
		public String getUser(User user) {
			return user.toString();
	}
		/*
		 * Map 參數
		 * 網址輸入：/mix?name=John&score=100&age=18&pass=true
		 * 網址輸入：/mix?name=Mary&score=90&age=20&level=2
		 * */
		@RequestMapping("/mix")
		@ResponseBody
		public String mix(@RequestParam Map<String, String> map) {
			return map.toString();
		}
		/*
		 * 11. 獲取原生 HttpSession 的資料
		 * */
		@GetMapping(value = "/sessionInfo", produces = MediaType.TEXT_PLAIN_VALUE)
		@ResponseBody
		public String getSessionInfo(HttpSession httpSession) {
			String sessionKey = "firstAccessTime";
			Object time = httpSession.getAttribute(sessionKey);
			if(time == null) {
				time = new Date();
				httpSession.setAttribute(sessionKey, time);
			}
			return String.format("firstAccessTime: %s\nsessionid: %s", time, httpSession.getId());
 }
}
