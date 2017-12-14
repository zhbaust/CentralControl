package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Maintenanceplan;
import com.aust.bean.Maintenanceplan;
import com.aust.bean.Page;
import com.aust.dao.impl.MaintenanceplanDaoImpl;
import com.aust.dao.inter.MaintenanceplanDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONObject;

public class MaintenanceplanService {
	private MaintenanceplanDaoInter dao;

	public MaintenanceplanService() {
		super();
		this.dao = new MaintenanceplanDaoImpl();
	}

	public void editMaintenanceplan(Maintenanceplan maintenanceplan) {
		String sql = "";

		List<Object> params = new LinkedList<>();
		params.add(maintenanceplan.getMaintenanceinterval());
		params.add(maintenanceplan.getContent());
		params.add(StringTool.StrTosqlDateTime(maintenanceplan.getRemindtime()));

		params.add(maintenanceplan.getRemark());

		if (maintenanceplan.getDevice() == null || maintenanceplan.getOperator() == null) {
			sql = "UPDATE cc_maintenanceplan SET maintenanceinterval=?,  content=?,remindtime=?, remark= ? WHERE deviceid= ?";
		} else {
			sql = "UPDATE cc_maintenanceplan SET maintenanceinterval=?,  content=?,remindtime=?, remark= ?, operatorid=?  WHERE deviceid= ?";

			params.add(maintenanceplan.getOperatorid());
		}
		params.add(maintenanceplan.getDeviceid());
		// params.add(maintenanceplan.getId());
		// System.out.println("update:"+sql);
		// 更新出库信息信息
		dao.update(sql, params);

	}

	public void deleteMaintenanceplan(String[] ids) {
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
			dao.deleteTransaction(conn, "DELETE FROM cc_maintenanceplan WHERE id IN(" + mark + ")", sid);
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
		} finally {
			MysqlTool.closeConnection();
		}
	}

	public int addMaintenanceplan(Maintenanceplan maintenanceplan) {
		// 添加员工记录
		dao.insert(
				"INSERT INTO cc_maintenanceplan(deviceid, maintenanceinterval,operatorid,content,remindtime,remark) value(?,?,?,?,?,?)",
				new Object[] { maintenanceplan.getDeviceid(), maintenanceplan.getMaintenanceinterval(),
						maintenanceplan.getOperatorid(), maintenanceplan.getContent(),
						StringTool.StrTosqlDateTime(maintenanceplan.getRemindtime()), maintenanceplan.getRemark() });
		return 1;
	}

	public String getMaintenanceplanList(Maintenanceplan maintenanceplan, Page page) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM cc_maintenanceplan ");
		// 参数
		List<Object> param = new LinkedList<>();
		// // 判断条件
		if (maintenanceplan != null) {
			if (maintenanceplan.getDevice() != null) {// 条件：年级
				int deviceid = maintenanceplan.getDevice().getId();
				param.add(deviceid);
				sb.append("AND deviceid=? ");
			}
			if (maintenanceplan.getOperator() != null) {// 条件：年级
				int operatorid = maintenanceplan.getOperator().getId();
				param.add(operatorid);
				sb.append("AND operatorid=? ");
			}
			if (maintenanceplan.getContent() != null) {
				sb.append("AND content like '%" + maintenanceplan.getContent() + "%' ");
			}
			if (maintenanceplan.getRemindtime() != null) {
				String[] t = maintenanceplan.getRemindtime().split("@");

				param.add(StringTool.StrTosqlDateTime(t[0]));
				param.add(StringTool.StrTosqlDateTime(t[1]));
				sb.append("AND (remindtime>= ? and remindtime<= ? ) ");
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
		//System.out.println("sql: " + sql);
		List<Maintenanceplan> list = dao.getMaintenanceplanList(sql, param);
		// 获取总记录数
		long total = getCount(maintenanceplan);
		// 定义Map
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// total键 存放总记录数，必须的
		jsonMap.put("total", total);
		// rows键 存放每页记录 list
		jsonMap.put("rows", list);
		// 格式化Map,以json格式返回数据
		String result = JSONObject.fromObject(jsonMap).toString();
		// 返回
		//System.out.println("service"+result);
		return result;
	}

	/**
	 * 获取记录数
	 * 
	 * @param maintenanceplan
	 * @param page
	 * @return
	 */
	private long getCount(Maintenanceplan maintenanceplan) {
		
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_maintenanceplan ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (maintenanceplan != null) {
			if (maintenanceplan.getDevice() != null) {// 条件：年级
				int deviceid = maintenanceplan.getDevice().getId();
				param.add(deviceid);
				sb.append("AND deviceid=? ");
			}
			if (maintenanceplan.getOperator() != null) {// 条件：年级
				int operatorid = maintenanceplan.getOperator().getId();
				param.add(operatorid);
				sb.append("AND operatorid=? ");
			}
			if (maintenanceplan.getContent() != null) {
				sb.append("AND content like '%" + maintenanceplan.getContent() + "%' ");
			}
			if (maintenanceplan.getRemindtime() != null) {
				String[] t = maintenanceplan.getRemindtime().split("@");

				param.add(StringTool.StrTosqlDateTime(t[0]));
				param.add(StringTool.StrTosqlDateTime(t[1]));
				sb.append("AND (remindtime>= ? and remindtime<= ? ) ");
				
			}
		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");

		long count = dao.count(sql, param).intValue();

		return count;
	}

}
