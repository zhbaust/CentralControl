package com.aust.service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Device;
import com.aust.bean.Employee;
import com.aust.bean.Page;
import com.aust.dao.impl.EmployeeDaoImpl;
import com.aust.dao.inter.EmployeeDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EmployeeService {

	private EmployeeDaoInter dao;

	public EmployeeService() {
		super();
		this.dao = new EmployeeDaoImpl();
	}

	public Employee getEmployee(String loginname) {
		// TODO Auto-generated method stub
		Employee employee = dao.getEmployeeList("SELECT * FROM cc_employee WHERE loginname = '" + loginname + "'", null)
				.get(0);

		return employee;
	}

	// 修改人员信息
	public void editEmployee(Employee employee, String newdepartmentid, int userid) {
		// TODO Auto-generated method stub
		String sql = "";
		String oldid = employee.getDepartmentid() + "";
		Employee employee1 = getEmployee(employee.getLoginname());
		if (!oldid.equals(newdepartmentid)) {
			employee.setDepartmentid(Integer.parseInt(newdepartmentid));
			insertTransferJob(employee1.getId(), oldid, newdepartmentid, userid);
		}

		List<Object> params = new LinkedList<>();
		params.add(employee.getName());

		params.add(employee.getSex());

		params.add(StringTool.StrTosqlDate(employee.getBirthday()));
		params.add(employee.getTel());

		params.add(employee.getAddress());
		params.add(employee.getJobstate());
		params.add(employee.getCardnumber());
		params.add(employee.getRemark());

		if (employee.getDepartment() == null || employee.getProfession() == null || employee.getAuthority() == null) {
			sql = "UPDATE cc_employee SET name=?,  sex=?, birthday=?, tel=?, address=?, jobstate=?, cardnumber=?,remark=? WHERE loginname=?";
		} else {
			sql = "UPDATE cc_employee SET name=?,  sex=?, birthday=?, tel=?, address=?, jobstate=?, cardnumber=?,remark=?, departmentid=? ,professionid=? ,authorityid=? WHERE loginname=?";
			params.add(employee.getDepartmentid());
			params.add(employee.getProfessionid());
			params.add(employee.getAuthorityid());
		}
		params.add(employee.getLoginname());
		// params.add(employee.getId());
		// System.out.println("update:"+sql);
		// 更新学生信息
		dao.update(sql, params);

		// 修改系统用户名
		// dao.update("UPDATE user SET name=? WHERE account=?",
		// new Object[]{student.getName(), student.getNumber()});

	}

	private void insertTransferJob(int employeeid, String oldid, String newdepartmentid, int userid) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		dao.insert(
				"INSERT INTO cc_jobtransfer(employeeid, olddepartmentid,departmentid,transferdate,transferreson, operatorid,remark )"
						+ " value(?,?,?,?,?,?,?)",
				new Object[] { employeeid, oldid, newdepartmentid, df.format(new Date()), "工种需要", userid, "部门调动" });
	}

	public void deleteEmployee(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		// 获取占位符
		String mark = StringTool.getMark(ids.length);
		Integer sid[] = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++) {
			sid[i] = Integer.parseInt(ids[i]);
		}

		// 获取连接
		Connection conn = MysqlTool.getConnection();
		// 开启事务
		MysqlTool.startTransaction();
		try {
			// 删除成绩表
			dao.deleteTransaction(conn, "DELETE FROM cc_employee WHERE id IN(" + mark + ")", sid);
			// 删除学生
			// dao.deleteTransaction(conn, "DELETE FROM student WHERE id
			// IN("+mark+")", sid);
			// 删除系统用户
			// dao.deleteTransaction(conn, "DELETE FROM user WHERE account
			// IN("+mark+")", numbers);

			// 提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			// 回滚事务
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}

	public int addEmployee(Employee employee) {
		// TODO Auto-generated method stub

		if (getEmployeeCountByName(employee.getLoginname()) > 0) // 如果已经存在了，则不写入
			return 0;

		// 添加员工记录
		dao.insert(
				"INSERT INTO cc_employee(name, loginname,sex,birthday,tel, address,departmentid,"
						+ "professionid,authorityid,jobstate,cardnumber,picture,remark) value(?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { employee.getName(), employee.getLoginname(), employee.getSex(),
						StringTool.StrTosqlDate(employee.getBirthday()), employee.getTel(), employee.getAddress(),
						employee.getDepartmentid(), employee.getProfessionid(), employee.getAuthorityid(),
						employee.getJobstate(), employee.getCardnumber(), "photo/student.jpg", employee.getRemark() });
		return 1;
	}

	private long getEmployeeCountByName(String loginname) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_employee ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (loginname != null) {
			param.add(loginname);
			sb.append("where loginname=? ");
		}
		String sql = sb.toString();

		long count = dao.count(sql, param).intValue();

		return count;
	}

	public String getEmployeeList(Employee employee, Page page) {
		// TODO Auto-generated method stub
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM cc_employee ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (employee != null) {
			if (employee.getName() != null) {// 条件：年级
				sb.append("AND name like '%" + employee.getName() + "%'");
			}

			if (employee.getTel() != null) {// 条件：年级
				sb.append("AND tel like '%" + employee.getTel() + "%'");
			}

			if (employee.getCardnumber() != null) {// 条件：年级
				sb.append("AND cardnumber like '%" + employee.getCardnumber() + "%'");
			}
			if (employee.getDepartment() != null) {// 条件：年级
				int departmentid = employee.getDepartment().getId();
				param.add(departmentid);
				sb.append("AND departmentid=? ");
			}
			if (employee.getProfession() != null) {// 条件：年级
				int professionid = employee.getProfession().getId();
				param.add(professionid);
				sb.append("AND professionid=? ");
			}
			if (employee.getAuthority() != null) {// 条件：年级
				int authorityid = employee.getAuthority().getId();
				param.add(authorityid);
				sb.append("AND authorityid=? ");
			}
		}
		// 添加排序
		sb.append("ORDER BY id DESC ");
		// 分页
		if (page != null) {
			param.add(page.getStart());
			param.add(page.getSize());
			sb.append("limit ?,?");
		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");
		// 获取数据
		// System.out.println("sql: "+sql);
		List<Employee> list = dao.getEmployeeList(sql, param);
		// 获取总记录数
		long total = getCount(employee);
		// 定义Map
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// total键 存放总记录数，必须的
		jsonMap.put("total", total);
		// rows键 存放每页记录 list
		jsonMap.put("rows", list);
		// 格式化Map,以json格式返回数据
		String result = JSONObject.fromObject(jsonMap).toString();
		// 返回
		// System.out.println("service"+result);
		return result;
	}

	/**
	 * 获取记录数
	 * 
	 * @param employee
	 * @param page
	 * @return
	 */
	private long getCount(Employee employee) {
		// TODO Auto-generated method stub
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_employee ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (employee.getName() != null) {// 条件：年级
			sb.append("AND name like '%" + employee.getName() + "%'");
		}

		if (employee.getTel() != null) {// 条件：年级
			sb.append("AND tel like '%" + employee.getTel() + "%'");
		}

		if (employee.getCardnumber() != null) {// 条件：年级
			sb.append("AND cardnumber like '%" + employee.getCardnumber() + "%'");
		}
		if (employee != null) {
			if (employee.getDepartment() != null) {// 条件：年级
				int departmentid = employee.getDepartment().getId();
				param.add(departmentid);
				sb.append("AND departmentid=? ");
			}
			if (employee.getProfession() != null) {// 条件：年级
				int professionid = employee.getProfession().getId();
				param.add(professionid);
				sb.append("AND professionid=? ");
			}
			if (employee.getAuthority() != null) {// 条件：年级
				int authorityid = employee.getAuthority().getId();
				param.add(authorityid);
				sb.append("AND authorityid=? ");
			}
		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");

		long count = dao.count(sql, param).intValue();

		return count;
	}

	/**
	 * 获取跟人员
	 * 
	 * @param account
	 * @param page
	 * @return
	 */
	public String getStudentList(String account, Page page) {

		Employee employee = (Employee) dao.getObject(Employee.class, "SELECT * FROM cc_employee WHERE loginname=?",
				new Object[] { account });

		return getEmployeeList(employee, page);
	}

	/**
	 * 设置照片
	 * 
	 * @param id
	 * @param fileName
	 */
	public void setPhoto(String id, String fileName) {
		String photo = "photo/" + fileName;
		dao.update("UPDATE cc_employee SET photo=? WHERE id=?", new Object[] { photo, id });
	}

	public String getAllEmployeeList() {
		// TODO Auto-generated method stub
		// 获取数据
		List<Object> list = dao.getList(Employee.class, "SELECT * FROM cc_employee");

		// 获取总记录数

		String result = JSONArray.fromObject(list).toString();
		// System.out.println("SQL:"+result);
		// 返回
		return result;
	}

	// public String getEmployeeQueryList(Employee employee, Page page) {
	// // TODO Auto-generated method stub
	// StringBuffer sb = new StringBuffer("SELECT * FROM cc_employee ");
	// //参数
	// List<Object> param = new LinkedList<>();
	// //判断条件
	// if(employee.getName() != null){//条件：年级
	// sb.append("AND name like '%"+employee.getName()+"%'");
	// }
	//
	// if(employee.getTel() != null){//条件：年级
	// sb.append("AND tel like '%"+employee.getTel()+"%'");
	// }
	//
	// if(employee.getCardnumber() != null){//条件：年级
	// sb.append("AND cardnumber like '%"+employee.getCardnumber()+"%'");
	// }
	//
	// if(employee.getDepartment() != null){//条件：年级
	// int departmentid = employee.getDepartment().getId();
	// param.add(departmentid);
	// sb.append("AND departmentid=? ");
	// }
	// if(employee.getProfession() != null){//条件：年级
	// int professionid = employee.getProfession().getId();
	// param.add(professionid);
	// sb.append("AND professionid=? ");
	// }
	// if(employee.getAuthority() != null){//条件：年级
	// int authorityid = employee.getAuthority().getId();
	// param.add(authorityid);
	// sb.append("AND authorityid=? ");
	// }
	//
	// //添加排序
	// sb.append("ORDER BY id DESC ");
	// //分页
	// if(page != null){
	// param.add(page.getStart());
	// param.add(page.getSize());
	// sb.append("limit ?,?");
	// }
	// String sql = sb.toString().replaceFirst("AND", "WHERE");
	// //获取数据
	// //System.out.println("sql: "+sql);
	// List<Employee> list = dao.getEmployeeList(sql, param);
	// //获取总记录数
	// long total = getCount(employee);
	// //定义Map
	// Map<String, Object> jsonMap = new HashMap<String, Object>();
	// //total键 存放总记录数，必须的
	// jsonMap.put("total", total);
	// //rows键 存放每页记录 list
	// jsonMap.put("rows", list);
	// //格式化Map,以json格式返回数据
	// String result = JSONObject.fromObject(jsonMap).toString();
	// //返回
	// //System.out.println("service"+result);
	// return result;
	// }

}
