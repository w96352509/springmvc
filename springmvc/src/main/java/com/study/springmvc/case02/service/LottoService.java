package com.study.springmvc.case02.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class LottoService {
   //總樂透	
   private List<Set<Integer>> lottos = new ArrayList<>();
   
   //集合數字
   public  List<Set<Integer>> getLottos(){
	 return lottos;
   }
   
   //新增數字
   public void addLotto() {
	   lottos.add(generateLotto());
   }
   
   //更新數字
   public void updateLotto(int index) {
	   lottos.set(index, generateLotto());
   }
 
   //刪除數字
   public void deleteLotto(int index) {
	   lottos.remove(index);
   }
   
   //取號碼
   private Set<Integer> generateLotto(){
	   Random r = new Random();
	   //樂透 539: 1~39 取出不重複好碼
	   Set<Integer> lotto = new LinkedHashSet<>();
	   while(lotto.size()<5) {
		   lotto.add(r.nextInt(39)+1);
	   }
	   return lotto;
   }
}
