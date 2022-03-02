package com.study.springmvc.lab.repository;

import java.sql.ResultSet;
import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.cj.protocol.Resultset;
import com.study.springmvc.lab.entity.Fund;
import com.study.springmvc.lab.entity.Fundstock;

@Repository
public class FundstockDaoImpl implements FundstockDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Fundstock> queryAll() {
		return queryAllCase3();
	}

	private List<Fundstock> queryAllCase1(){
		String sql = "select s.sid , s.fid , s.symbol , s.share from fundstock s order by s.sid";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Fundstock>(Fundstock.class));
	}
	
	private List<Fundstock> queryAllCase2(){
		String sql = "select s.sid , s.fid , s.symbol , s.share from fundstock s order by s.sid";
		RowMapper<Fundstock> rm = (ResultSet rs, int rowNum)->{
			Fundstock fundStock = new Fundstock();
			fundStock.setFid(rs.getInt("sid"));
			fundStock.setSid(rs.getInt("fid"));
			fundStock.setSymbol(rs.getString("symbol"));
			fundStock.setShare(rs.getInt("share"));
			String sql2 = "select f.fid, f.fname, f.createtime "
					      + "from fund f "
					      + "where f.fid = ? "
					      + "order by f.fid";
			List<Fund> funds = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<Fund>(Fund.class),fundStock.getFid());
			if (funds != null && funds.size() >0) {
				fundStock.setFund(funds.get(0));
			}
			return  fundStock;
		};
		    return  jdbcTemplate.query(sql, rm);
	}
	private List<Fundstock> queryAllCase3(){
		String sql = "select s.sid, s.fid, s.symbol, s.share , "
				+ "f.fid as fund_fid , f.fname as fund_fname , f.createtime as fund_createtime  "
				+ "from fundstock s left join fund f " + "on f.fid = s.fid";
		ResultSetExtractor<List<Fundstock>> resultSetExtractor = 
				JdbcTemplateMapperFactory
				.newInstance()
				.addKeys("sid") // Fundstock' sid																			// 的主鍵
				.newResultSetExtractor(Fundstock.class);

		return jdbcTemplate.query(sql, resultSetExtractor);
	}
	
	//分頁查詢
	@Override
	public List<Fundstock> queryPage(int offset) {
		//小於0筆全搜尋
		if(offset < 0) {
			return queryAll();
		}
		//設定sql
		String sql = "select s.sid, s.fid, s.symbol, s.share , "
				+ "f.fid as fund_fid , f.fname as fund_fname , f.createtime as fund_createtime  "
				+ "from fundstock s left join fund f " + "on f.fid = s.fid order by s.sid ";
		//加上數量限制(limit) interface設定大小 / offset(從哪+1開始)
		sql += String.format(" limit %d offset %d ", FundstockDao.LIMIT, offset);
		//
		ResultSetExtractor<List<Fundstock>> resultSetExtractor = 
				JdbcTemplateMapperFactory
				.newInstance()
				.addKeys("sid") // Fundstock' sid																			// 的主鍵
				.newResultSetExtractor(Fundstock.class);

		return jdbcTemplate.query(sql, resultSetExtractor);
	}


	@Override
	public Fundstock get(Integer sid) {
		// 先找到 fundstock(找到自己)
				String sql = "select s.sid, s.fid, s.symbol, s.share from fundstock s where s.sid = ?";
				Fundstock fundstock = jdbcTemplate.queryForObject(
						sql, 
						new BeanPropertyRowMapper<Fundstock>(Fundstock.class), 
						sid);
				// 再透過 自身fundstock.getFid() 找到 對應的fund
				sql = "select f.fid, f.fname, f.createtime from fund f where f.fid = ?";
				Fund fund = jdbcTemplate.queryForObject(
						sql, 
						new BeanPropertyRowMapper<Fund>(Fund.class), 
						fundstock.getFid());
				// 注入 fund
				fundstock.setFund(fund);
				return fundstock;
		
	}

	@Override
	public int add(Fundstock fundstock) {
		String sql = "insert into fundstock(fid, symbol, share) values(?, ?, ?)";
		int rowcount = jdbcTemplate.update(sql, fundstock.getFid(), fundstock.getSymbol(), fundstock.getShare());
		return rowcount;
	}

	@Override
	public int update(Fundstock fundstock) {
		String sql = "update fundstock set fid=?, symbol=?, share=? where sid=?";
		int rowcount = jdbcTemplate.update(sql, fundstock.getFid(), fundstock.getSymbol(), fundstock.getShare(), fundstock.getSid());
		return rowcount;
	}

	@Override
	public int delete(Integer sid) {
		String sql = "delete from fundstock where sid=?";
		int rowcount = jdbcTemplate.update(sql, sid);
		return rowcount;
	}

	@Override
	public int count() {
		String sql = "select count(*) from fundstock";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
