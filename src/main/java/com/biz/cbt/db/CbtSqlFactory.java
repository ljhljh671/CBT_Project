package com.biz.cbt.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.biz.cbt.dao.CbtDao;

public class CbtSqlFactory {
	
SqlSessionFactory sessionFactory;
	
	public SqlSessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	public CbtSqlFactory() {
		Properties props = new Properties();
		
		props.put("DRIVER", DbContract.ORACLE_PRO.Driver);
		props.put("URL", DbContract.ORACLE_PRO.url);
		props.put("USER", DbContract.ORACLE_PRO.user);
		props.put("PASSWORD", DbContract.ORACLE_PRO.password);
		
		
		// DataSourceFactory 만드는
		CbtDataSourceFactory dataSourceFactory 
			= new CbtDataSourceFactory();
		
		dataSourceFactory.setProperties(props);
		
		// DataSourceFactory로 부터 DataSource를 요청
		DataSource datasource = dataSourceFactory.getDataSource();
		
		// Data를 대신 받아줄 중간 창고(공장)
		TransactionFactory transactionfactory
			= new JdbcTransactionFactory();
		
		Environment environment 
			= new Environment("GradeEnv",transactionfactory,datasource);
		
		Configuration config = new Configuration(environment);
		config.addMapper(CbtDao.class);

		
		this.sessionFactory
			= new SqlSessionFactoryBuilder().build(config);
	}
	


}
