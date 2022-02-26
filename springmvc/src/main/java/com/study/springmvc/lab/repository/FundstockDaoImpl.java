package com.study.springmvc.lab.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.study.springmvc.lab.entity.FundStock;

@Repository
public class FundstockDaoImpl implements FundstockDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<FundStock> queryAll() {
		String sql = "select s.sid , s.fid , s.symbol , s.share from fundstock s order by s.sid";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(FundStock.class));
	}

	@Override
	public List<FundStock> queryAll(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FundStock get(Integer sid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(FundStock stock) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FundStock stock) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer sid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
