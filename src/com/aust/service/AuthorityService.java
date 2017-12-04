package com.aust.service;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import com.aust.bean.Authority;
import com.aust.dao.impl.BaseDaoImpl;
import com.aust.dao.inter.BaseDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONArray;

public class AuthorityService {
BaseDaoInter dao = new BaseDaoImpl();
	
	/**
	 * 获取所有权限
	 * @return
	 */
	public String getAuthorityList(String authorityid){
		List<Object> list;
		if(StringTool.isEmpty(authorityid)){
			list = dao.getList(Authority.class, "SELECT * FROM cc_authority group by 1");
		} else{
			list = dao.getList(Authority.class, 
					"SELECT *  FROM cc_authority as p WHERE p.id=?", 
					new Object[]{Integer.parseInt(authorityid)});
		}
		//json化
        String result = JSONArray.fromObject(list).toString();
        return result;
	}
	
	
	/**
	 * 获取所有权限
	 * @return
	 */
	public long getAuthorityCountByName(String name){
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_authority ");
		//参数
		List<Object> param = new LinkedList<>();
		//判断条件
		if(name != null){ 
				param.add(name);
				sb.append("where name=? ");
		}
		String sql = sb.toString();
		
		long count = dao.count(sql, param).intValue();
		
		return count;
	}

	/**
	 * 添加权限
	 * @param course
	 */
	public int addAuthority(Authority p) {
		
		if(getAuthorityCountByName(p.getName())>0) //如果已经存在了，则不写入
			return 0;
		
		dao.insert("INSERT INTO cc_authority(name,remark) value(?,?)", 
				                         new Object[]{p.getName(),p.getRemark()}
				                         );
		return 1;
	}

	/**
	 * 删除权限
	 * @param courseid
	 * @throws Exception 
	 */
	public void deleteAuthority(int authorityid) throws Exception {
		//获取连接
		Connection conn = MysqlTool.getConnection();
		try {
			//开启事务
			MysqlTool.startTransaction();
			//删除成绩表
			dao.deleteTransaction(conn, "DELETE FROM cc_authority WHERE id=?", new Object[]{authorityid});
//			//删除班级的课程和老师的关联
//			dao.deleteTransaction(conn, "DELETE FROM clazz_course_teacher WHERE courseid=?", new Object[]{courseid});
//			//删除年级与课程关联
//			dao.deleteTransaction(conn, "DELETE FROM grade_course WHERE courseid=?",  new Object[]{courseid});
//			//最后删除课程
//			dao.deleteTransaction(conn, "DELETE FROM course WHERE id=?",  new Object[]{courseid});
			
			//提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			//回滚事务
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}
	
	/**
	 * 修改部门信息
	 * @param teacher
	 * @throws Exception
	 */
	public void editProfession(Authority authority) throws Exception {
		Connection conn = MysqlTool.getConnection();
		try {
			//开启事务
			MysqlTool.startTransaction();
			
			String sql = "UPDATE cc_authority set name=?,remark=? WHERE id=?";
			Object[] param = new Object[]{
					authority.getName(), 
					authority.getRemark(),
					authority.getId()
				};
			//修改教师信息
			dao.updateTransaction(conn, sql, param);
			
			
			//提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			//回滚事务
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}
}
