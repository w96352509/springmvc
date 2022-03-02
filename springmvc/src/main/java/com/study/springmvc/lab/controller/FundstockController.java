package com.study.springmvc.lab.controller;

import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.LinkedHashMap;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springmvc.lab.entity.Fund;
import com.study.springmvc.lab.entity.Fundstock;
import com.study.springmvc.lab.repository.FundDao;
import com.study.springmvc.lab.repository.FundstockDao;

@Controller
@RequestMapping("/lab/fundstock")
public class FundstockController {
	@Autowired
	private FundstockDao fundstockDao;
	
	@Autowired
	private FundDao fundDao;
	
	private int pageNumber = -1; //-1 = 0 - 1
	
	@GetMapping("/")
	public String index(@ModelAttribute Fundstock fundstock,@ModelAttribute Fund fund, Model model) {
		return "redirect:./page/" + pageNumber;
	}
	
	@GetMapping("/page/{pageNumber}")
	public String page(@PathVariable("pageNumber") int pageNumber, 
			 @ModelAttribute Fundstock fundstock
			,@ModelAttribute Fund fund
			, Model model) {
		this.pageNumber = pageNumber;
		//針對分頁面
		//FundstockDao.LIMIT = 5 (每頁最大筆數)
		// 第一頁 = 1-1*5 = 0 index=0(開始)
		// 第二頁 = 2-1*5 = 5 index=5(開始)
		int offset = (pageNumber-1) * FundstockDao.LIMIT;
		//將起點offset放入方法
		List<Fundstock> fundstocks =  fundstockDao.queryPage(offset);
		//全部基金
		List<Fund> funds = fundDao.queryAll();
		//頁面總數: 總資料量 / 5
		int pageTotalCount = (fundstockDao.count() / FundstockDao.LIMIT);
		model.addAttribute("_method", "POST");
		model.addAttribute("fundstocks", fundstocks);
		model.addAttribute("funds", funds);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("count" , fundstockDao.count());
		model.addAttribute("groupMap", getGroupMap());
		return "lab/fundstock";
	}
	
	@GetMapping("/{sid}")
	public String get(@PathVariable("sid") Integer sid, Model model) {
		int offset = (pageNumber-1) * FundstockDao.LIMIT;
		List<Fund> funds = fundDao.queryAll();
		List<Fundstock> fundstocks =  fundstockDao.queryPage(offset);
		int pageTotalCount = (fundstockDao.count() / FundstockDao.LIMIT)+1;
		model.addAttribute("_method", "PUT");
		model.addAttribute("fundstocks", fundstocks);
		model.addAttribute("fundstock", fundstockDao.get(sid));
		model.addAttribute("funds", funds);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("groupMap", getGroupMap());
		return "lab/fundstock";
	}
	
	@PostMapping("/")
	public String add(Fundstock fundstock) {
		fundstockDao.add(fundstock);
		return "redirect:./";
	}
	
	@PutMapping("/{sid}")
	public String update(Fundstock fundstock) {
		fundstockDao.update(fundstock);
		return "redirect:./";
	}
	
	@DeleteMapping("/{sid}")
	public String delete(@PathVariable("sid") Integer sid) {
		fundstockDao.delete(sid);
		return "redirect:./";
	}
	
	private Map<String, Integer> getGroupMap() {
		// select s.symbol, sum(s.share) as share
		// from fundstock s
		// group by s.symbol
		List<Fundstock> fundstocks = fundstockDao.queryAll();
		Map<String, Integer> status = fundstocks.stream()
				                       .collect(groupingBy(Fundstock::getSymbol, 
						                summingInt(Fundstock::getShare)));
		Map<String, Integer> statAndSort  = new LinkedHashMap<>();
		status.entrySet().stream().sorted(
				Map.Entry.<String, Integer>comparingByValue().reversed())
		        .forEachOrdered(e->statAndSort.put(e.getKey(), e.getValue()));
		status = statAndSort;
		return status;
	}
	
	
}
