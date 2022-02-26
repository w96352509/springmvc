package com.study.springmvc.lab.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.study.springmvc.lab.entity.Fund;

@Repository
public class FundDaoImpl implements FundDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Fund> queryAll() {
		String sql = "select f.fid , f.fname , f.createtime from fund f order by f.fid";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Fund>(Fund.class));
	}

	@Override
	public List<Fund> queryAll(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fund get(Integer sid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(Fund stock) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Fund stock) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer fid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
