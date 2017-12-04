package com.aust.service;

import java.util.List;

import com.aust.bean.Employee;
//import com.lizhou.bean.Clazz;
//import com.lizhou.bean.Grade;
import com.aust.bean.SystemInfo;
//import com.aust.bean.User;
import com.aust.dao.impl.BaseDaoImpl;
import com.aust.dao.impl.SystemDaoImpl;
import com.aust.dao.inter.BaseDaoInter;
import com.aust.dao.inter.SystemDaoInter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 年级服务层
 * @author bojiangzhou
 *
 */
public class SystemService {
	
	SystemDaoInter dao = new SystemDaoImpl();
	
	/**
	 * 获取系统所有账号
	 * @return
	 */
	public String getAccountList(){
		//获取数据
		List<String> list = dao.getColumn("SELECT loginname FROM cc_employee", null);
		//json化
        String result = JSONArray.fromObject(list).toString();
        
        return result;
	}

	/**
	 * 登录验证
	 * @param user
	 * @return 
	 */
	public Employee getAdmin(Employee employee) {
		Employee searchUser = (Employee) dao.getObject(Employee.class, 
				"SELECT * FROM cc_employee WHERE loginname=? AND password=? AND authorityid=?", 
				new Object[]{employee.getLoginname(), employee.getPassword(), employee.getAuthorityid()});
		
		//System.out.println("login:"+employee.getLoginname());
		
		return searchUser;
	}

	/**
	 * 修改用户密码
	 * @param user
	 */
	public void editPassword(Employee employee) {
		dao.update("UPDATE cc_employee SET password=? WHERE loginname=?", 
				new Object[]{employee.getPassword(),employee.getLoginname()});
	}
	
	/**
	 * 修改系统信息
	 * @param name 修改的名称
	 * @param value 值
	 * @return 返回修改后的系统信息对象
	 */
	public SystemInfo editSystemInfo(String name, String value) {
		//修改数据库
		dao.update("UPDATE system SET "+name+" = ?", new Object[]{value});
		//重新加载数据
		//获取系统初始化对象
    	SystemInfo sys = (SystemInfo) dao.getObject(SystemInfo.class, "SELECT * FROM system", null);
    	return sys;
	}
	
	
}
