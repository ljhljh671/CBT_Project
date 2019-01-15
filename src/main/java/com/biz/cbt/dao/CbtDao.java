package com.biz.cbt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.biz.cbt.sql.CbtSql;
import com.biz.cbt.vo.CbtVO;

public interface CbtDao {
	
	@Select(CbtSql.SC_ALL)
	public List<CbtVO> selectAll();
	
	@Select(CbtSql.SC_FIND_NUM)
	public CbtVO findByNum(long cb_num);
	
	@Insert(CbtSql.SC_INSERT)
	public int insert(CbtVO vo);
	
	@Update(CbtSql.SC_UPDATE)
	public int update(CbtVO vo);
	
	@Delete(CbtSql.SC_DELETE)
	public int delete(long cb_num);

}
