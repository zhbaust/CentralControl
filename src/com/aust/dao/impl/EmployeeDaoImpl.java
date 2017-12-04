package com.aust.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Authority;
import com.aust.bean.Department;
import com.aust.bean.Employee;
import com.aust.bean.Profession;
import com.aust.dao.inter.EmployeeDaoInter;
import com.aust.tools.MysqlTool;

public class EmployeeDaoImpl extends BaseDaoImpl implements EmployeeDaoInter{

public List<Employee> getEmployeeList(String sql, List<Object> param) {
	//数据集合
			List<Employee> list = new LinkedList<>();
			try {
				//获取数据库连接
				Connection conn = MysqlTool.getConnection();
				//预编译
				PreparedStatement ps = conn.prepareStatement(sql);
				//设置参数
				if(param != null && param.size() > 0){
					for(int i = 0;i < param.size();i++){
						ps.setObject(i+1, param.get(i));
					}
				}
				//执行sql语句
				ResultSet rs = ps.executeQuery();
				//获取元数据
				ResultSetMetaData meta = rs.getMetaData();
				//遍历结果集
				while(rs.next()){
					//创建对象
					Employee employee = new Employee();
					//遍历每个字段
					for(int i=1;i <= meta.getColumnCount();i++){
						String field = meta.getColumnName(i);
						BeanUtils.setProperty(employee, field, rs.getObject(field));
					}
					//查询部门
					Department department = (Department) getObject(Department.class, "SELECT * FROM cc_department WHERE id=?", new Object[]{employee.getDepartmentid()});
					//查询工种
					Profession profession = (Profession) getObject(Profession.class, "SELECT * FROM cc_profession WHERE id=?", new Object[]{employee.getProfessionid()});
					//查询权限
					Authority authority = (Authority) getObject(Authority.class, "SELECT * FROM cc_authority WHERE id=?", new Object[]{employee.getAuthorityid()});
					//添加
					employee.setDepartment(department);
					employee.setProfession(profession);
					employee.setAuthority(authority);
					//添加到集合
					list.add(employee);
				}
				//关闭连接
				MysqlTool.closeConnection();
				MysqlTool.close(ps);
				MysqlTool.close(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		
	}
}
