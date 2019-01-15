package com.biz.cbt.sql;

public class CbtSql {
	public static final String SC_ALL =
			" SELECT * FROM tbl_cbt ";
	public static final String SC_FIND_NUM =
			" SELECT * FROM tbl_cbt "
			+ " WHERE cb_num = #{cb_num} ";
	public static final String SC_INSERT =
			
			" INSERT INTO tbl_cbt "
			+ " VALUES(SEQ_cbt.NEXTVAL, #{cb_question}, "
			+ " #{cb_q1}, #{cb_q2}, #{cb_q3}, #{cb_q4},"
			+ " #{cb_answernum} ) ";
			
	public static final String SC_UPDATE =
			" UPDATE tbl_cbt "
			+ " SET "
			+ " cb_question = #{cb_question}, "
			+ " cb_q1 = #{cb_q1}, "
			+ " cb_q2 = #{cb_q2}, "
			+ " cb_q3 = #{cb_q3}, "
			+ " cb_q4 = #{cb_q4}, "
			+ " cb_answernum = #{cb_answernum} "
			+ " WHERE cb_num = #{cb_num} ";
			
	public static final String SC_DELETE =
			" DELETE FROM tbl_cbt "
			+" WHERE cb_num = #{cb_num} ";
}
