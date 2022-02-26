package com.study.springmvc.lab.entity;

import java.util.Date;
import java.util.List;

public class Fund {
	private Integer fid;
	private String fname;
	private Date createtime;
	private List<FundStock> fundstocks; //單方

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<FundStock> getFundstocks() {
		return fundstocks;
	}

	public void setFundstocks(List<FundStock> fundstocks) {
		this.fundstocks = fundstocks;
	}

	@Override
	public String toString() {
		return "Fund [fid=" + fid + ", fname=" + fname + ", createtime=" + createtime + ", fundstocks=" + fundstocks
				+ "]";
	}

	
}
