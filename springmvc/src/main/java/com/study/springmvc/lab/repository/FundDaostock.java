package com.study.springmvc.lab.repository;

import java.util.List;

import com.study.springmvc.lab.entity.FundStock;

public interface FundDaostock {
    //每頁五筆
	int LIMIT = 5 ;
	//全部查詢
	List<FundStock> queryAll();
	//分頁查詢
	List<FundStock> queryAll(int offset);
	//取得單筆
	FundStock get(Integer sid);
	//新增
	int add(FundStock stock);
	//修改
	int update(FundStock stock);
    //刪除
	int delete(Integer sid);
}
