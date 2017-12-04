package com.aust.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Department;
import com.aust.bean.Jobtransfer;
import com.aust.bean.Page;
import com.aust.dao.impl.JobTransferDaoImpl;
import com.aust.dao.inter.JobTransferDaoInter;

import net.sf.json.JSONObject;

public class JobtransferService {
	
   private JobTransferDaoInter dao;
   public JobtransferService() {
		super();
		this.dao = new JobTransferDaoImpl();
	}
public void editJobtransfer(Jobtransfer jobtransfer) {
	// TODO Auto-generated method stub
	String sql = "";
	
	List<Object> params = new LinkedList<>();
	
	sql = "UPDATE cc_jobtransfer SET transferreson=?  WHERE id=?";
	
	
	params.add(jobtransfer.getTransferreson());
	params.add(jobtransfer.getId());
	//System.out.println("sql"+sql);
	//更新学生信息
	dao.update(sql, params);
}

/**
 * 分页获取学生
 * @param student 学生信息
 * @param page 分页
 * @return
 */
public String getJobtransferList(Jobtransfer jobtransfer, Page page) {
	// TODO Auto-generated method stub
	//sql语句
			StringBuffer sb = new StringBuffer("SELECT * FROM cc_jobtransfer ");
			//参数
			List<Object> param = new LinkedList<>();
			//判断条件
			if(jobtransfer != null){ 
				if(jobtransfer.getEmployee() != null){//条件：年级
					int employeeid = jobtransfer.getEmployee().getId();
					param.add(employeeid);
					sb.append("AND employeeid=? ");
				}
				if(jobtransfer.getOlddepartment() != null){//条件：年级
					int olddepartmentid = jobtransfer.getOlddepartment().getId();
					param.add(olddepartmentid);
					sb.append("AND olddepartmentid=? ");
				}
				if(jobtransfer.getDepartment() != null){//条件：年级
					int departmentid = jobtransfer.getDepartment().getId();
					param.add(departmentid);
					sb.append("AND departmentid=? ");
				}
				if(jobtransfer.getOperator() != null){//条件：年级
					int operatorid = jobtransfer.getOperator().getId();
					param.add(operatorid);
					sb.append("AND operatorid=? ");
				}
				
			}
			//添加排序
			sb.append("ORDER BY id ASC ");
			//分页
			if(page != null){
				param.add(page.getStart());
				param.add(page.getSize());
				sb.append("limit ?,?");
			}
			String sql = sb.toString().replaceFirst("AND", "WHERE");
			//获取数据
			
			List<Jobtransfer> list = dao.getJobtransferList(sql, param);
			//获取总记录数
			long total = getCount(jobtransfer);
			
			//定义Map
			Map<String, Object> jsonMap = new HashMap<String, Object>();  
			//total键 存放总记录数，必须的
	        jsonMap.put("total", total);
	        //rows键 存放每页记录 list 
	        jsonMap.put("rows", list); 
	        //格式化Map,以json格式返回数据
	        String result = JSONObject.fromObject(jsonMap).toString();
	        //System.out.println("SQL:"+result);
	        //返回
			return result;
		}  

/**
 * 获取记录数
 * @param student
 * @param page
 * @return
 */
private long getCount(Jobtransfer jobtransfer){
	//sql语句
	StringBuffer sb = new StringBuffer("SELECT * FROM cc_jobtransfer ");
	//参数
	List<Object> param = new LinkedList<>();
	//判断条件
	if(jobtransfer != null){ 
		if(jobtransfer.getEmployee() != null){//条件：年级
			int employeeid = jobtransfer.getEmployee().getId();
			param.add(employeeid);
			sb.append("AND employeeid=? ");
		}
		if(jobtransfer.getOlddepartment() != null){//条件：年级
			int olddepartmentid = jobtransfer.getOlddepartment().getId();
			param.add(olddepartmentid);
			sb.append("AND olddepartmentid=? ");
		}
		if(jobtransfer.getDepartment() != null){//条件：年级
			int departmentid = jobtransfer.getDepartment().getId();
			param.add(departmentid);
			sb.append("AND departmentid=? ");
		}
		if(jobtransfer.getOperator() != null){//条件：年级
			int operatorid = jobtransfer.getOperator().getId();
			param.add(operatorid);
			sb.append("AND operatorid=? ");
		}
		
	}
	String sql = sb.toString().replaceFirst("AND", "WHERE");
	
	long count = dao.count(sql, param).intValue();
	
	return count;
}

}
