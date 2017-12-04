package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Department;
import com.aust.dao.impl.DepartmentDaoImpl;
import com.aust.dao.inter.DepartmentDaoInter;
import com.aust.bean.Page;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DepartmentService {

	private DepartmentDaoInter dao ;
	
	public DepartmentService() {
		this.dao = new DepartmentDaoImpl();;
	}

	
	
	public void editDepartment(Department department) {
		// TODO Auto-generated method stub
		String sql = "";
		
		List<Object> params = new LinkedList<>();
		params.add(department.getName());
		params.add(department.getTel());
		params.add(department.getRemark());
		
		
		//if(student.getGrade() == null || student.getClazz() == null){
		if(department.getEmployee()==null){
			sql = "UPDATE cc_department SET name=?, tel=?, remark=? WHERE id=?";
		} else{
			sql = "UPDATE cc_department SET name=?, tel=?, remark=?, leadid=?  WHERE id=?";
			params.add(department.getLeadid());
		}
		params.add(department.getId());
		//System.out.println("sql"+sql);
		//更新学生信息
		dao.update(sql, params);
		
	}
	
	/**
	 * 删除部门
	 * @param ids 学生id集合
	 * @param numbers 学生学号集合
	 * @throws Exception 
	 */
	public void deleteDepartment(String[] ids) throws Exception{
		//获取占位符
		String mark = StringTool.getMark(ids.length);
		Integer sid[] = new Integer[ids.length];
		for(int i =0 ;i < ids.length;i++){
			sid[i] = Integer.parseInt(ids[i]);
		}
		
		//获取连接
		Connection conn = MysqlTool.getConnection();
		//开启事务
		MysqlTool.startTransaction();
		try {
			//删除成绩表
			dao.deleteTransaction(conn, "DELETE FROM cc_department WHERE id IN("+mark+")", sid);
//			//删除学生
//			dao.deleteTransaction(conn, "DELETE FROM student WHERE id IN("+mark+")", sid);
//			//删除系统用户
//			dao.deleteTransaction(conn, "DELETE FROM user WHERE account IN("+mark+")",  numbers);
			
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
	 * 添加部门
	 * @param department
	 */
	
	public int addDepartment(Department department) {
		// TODO Auto-generated method stub
		if(getCountByName(department.getName())>0) //如果已经存在了，则不写入
			return 0;
		//添加部门记录
				dao.insert("INSERT INTO cc_department(id, name, leadid, tel, remark) value(?,?,?,?,?)", 
						new Object[]{
								department.getId(),
								department.getName(),
								department.getLeadid(),
								department.getTel(),
								department.getRemark()
						});
				return 1;
	}

	
	private long getCountByName(String name) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_department ");
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
	 * 分页获取学生
	 * @param student 学生信息
	 * @param page 分页
	 * @return
	 */
	public String getDepartmentList(Department department, Page page) {
		// TODO Auto-generated method stub
		//sql语句
				StringBuffer sb = new StringBuffer("SELECT * FROM cc_department ");
				//参数
				List<Object> param = new LinkedList<>();
				//判断条件
				if(department != null){ 
					if(department.getEmployee() != null){//条件：年级
						int leadid = department.getEmployee().getId();
						param.add(leadid);
						sb.append("AND leadid=? ");
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
				
				List<Department> list = dao.getDepartmentList(sql, param);
				//获取总记录数
				long total = getCount(department);
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
	private long getCount(Department department){
		//sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_department ");
		//参数
		List<Object> param = new LinkedList<>();
		//判断条件
		if(department != null){ 
			if(department.getEmployee() != null){//条件：人员
				int leadid = department.getEmployee().getId();
				param.add(leadid);
				sb.append("AND leadid=? ");
			}
		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");
		
		long count = dao.count(sql, param).intValue();
		
		return count;
	}

	/**
	 * 获取跟学生一个班的班级同学
	 * @param account
	 * @param page
	 * @return
	 */
	public String getStudentList(String account, Page page) {
		
		Department department = (Department) dao.getObject(Department.class, "SELECT * FROM cc_department WHERE id=?", new Object[]{account});
		
		return getDepartmentList(department, page);
	}

	/**
	 * 获取部门详细信息
	 * @param id
	 * @return
	 */
	public Department getDepartment(String id) {
		
		Department department = dao.getDepartmentList("SELECT * FROM cc_department WHERE id="+id, null).get(0);
		
		return department;
	}



	
//	/**
//	 * 设置照片
//	 * @param number
//	 * @param fileName 
//	 */
//	public void setPhoto(String number, String fileName) {
//		String photo = "photo/"+fileName;
//		dao.update("UPDATE student SET photo=? WHERE number=?", new Object[]{photo, number});
//	}

	public String getAllDepartmentList() {
		// TODO Auto-generated method stub
		//sql语句
				//获取数据
				List<Object> list = dao.getList(Department.class, "SELECT * FROM cc_department");
				
				//获取总记录数
				
		        String result = JSONArray.fromObject(list).toString();
		        //System.out.println("SQL:"+result);
		        //返回
				return result;
			} 

	

}
