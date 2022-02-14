package com.study.springmvc.case02.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.case02.service.LottoService;

@Controller
@RequestMapping("/case02/lotto")
public class LottoController {
	
	@Autowired
	private LottoService lottoService;
	
	private List<Set<Integer>> lottos = new ArrayList<>();

	
	// lotto 主畫面
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("lottos", lottoService.getLottos());
		lottos = lottoService.getLottos();
		if (lottos.size()>0) {
			//1.準備記錄統計資料用
			Map<Integer, Long> stat = null;
			// flatMap 取代 foreach 
			// 得到 lotto(5個) 放入集合
			//利用 flatMap 將資料拆散後收集 collect 起來
			List<Integer> nums = lottos.stream()
					.flatMap(lotto -> lotto.stream()).collect(Collectors.toList());
			
			// 將資料透過 groupingby 分組 , 並放入準備記錄統計資料
			stat = nums.stream().collect(groupingBy(identity(), counting()));
			
			//加上順序
			//entrySet 放在 Set 中 :一個鍵對一個值
			Map<Integer, Long> statAndSort = new LinkedHashMap<>(); // 放入排序後的結果
			stat.entrySet().stream()
			.sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
			//放入statAndSort map
			.forEachOrdered(e -> statAndSort.put(e.getKey(), e.getValue()));
			// 將排序後的結果指派給 stat
						stat = statAndSort;
						model.addAttribute("stat", stat);
		}
		return "case02/show_lotto";
	}
	
	// 電腦選號
	@RequestMapping("/add")
	public String add() {
		lottoService.addLotto();
		return "redirect:./";
	}
	
	// 修改紀錄
	@RequestMapping("/update/{index}")
	public String update(@PathVariable("index") int index) {
		lottoService.updateLotto(index);
		return "redirect:../";
	}
	
	// 刪除紀錄
	
	@RequestMapping("/delete/{index}")
	public String delete(@PathVariable("index") int index) {
		lottoService.deleteLotto(index);
		return "redirect:../";
	}
	
	
	
}
