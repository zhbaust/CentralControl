package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Maintenance;
import com.aust.bean.Page;
import com.aust.dao.impl.MaintenanceDaoImpl;
import com.aust.dao.inter.MaintenanceDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONObject;

public class MaintenanceService {
	private MaintenanceDaoInter dao;

	public MaintenanceService() {
		super();
		this.dao = new MaintenanceDaoImpl();
	}

	public void editMaintenance(Maintenance maintenance) {
		String sql = "";

		List<Object> params = new LinkedList<>();
		params.add(StringTool.StrTosqlDateTime(maintenance.getMaintenancetime()));

		params.add(maintenance.getDispose());

		params.add(maintenance.getRemark());

		if (maintenance.getDevice() == null || maintenance.getOperator() == null) {
			sql = "UPDATE cc_maintenance SET maintenancetime=?,  dispose=?, remark=? WHERE deviceid=?";
		} else {
			sql = "UPDATE cc_maintenance SET maintenancetime=?,  dispose=?, remark=?,operatorid=?  WHERE deviceid=?";

			params.add(maintenance.getOperatorid());
		}
		params.add(maintenance.getDeviceid());
		// params.add(maintenance.getId());
		// System.out.println("update:"+sql);
		// 更新出库信息信息
		dao.update(sql, params);

	}

	public void deleteMaintenance(String[] ids) {
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
			dao.deleteTransaction(conn, "DELETE FROM cc_maintenance WHERE id IN(" + mark + ")", sid);
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

	public int addMaintenance(Maintenance maintenance) {
		dao.insert("INSERT INTO cc_maintenance(deviceid, maintenancetime,dispose,operatorid,remark) value(?,?,?,?,?)",
				new Object[] { maintenance.getDeviceid(), StringTool.StrTosqlDateTime(maintenance.getMaintenancetime()), maintenance.getDispose(),
						maintenance.getOperatorid(),  maintenance.getRemark() });
		return 1;
	}

	public String getMaintenanceList(Maintenance maintenance, Page page) {
		// sql语句
				StringBuffer sb = new StringBuffer("SELECT * FROM cc_maintenance ");
				// 参数
				List<Object> param = new LinkedList<>();
				// 判断条件
				if (maintenance != null) {
					if (maintenance.getDevice() != null) {// 条件：年级
						int deviceid = maintenance.getDevice().getId();
						param.add(deviceid);
						sb.append("AND deviceid=? ");
					}
					if (maintenance.getOperator() != null) {// 条件：年级
						int operatorid = maintenance.getOperator().getId();
						param.add(operatorid);
						sb.append("AND operatorid=? ");
					}
					if (maintenance.getDispose() != null) {// 条件：年级
					
						sb.append("AND dispose like '%"+maintenance.getDispose()+"%' ");
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
				List<Maintenance> list = dao.getMaintenanceList(sql, param);
				// 获取总记录数
				long total = getCount(maintenance);
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
			 * @param maintenance
			 * @param page
			 * @return
			 */
			private long getCount(Maintenance maintenance) {
				// TODO Auto-generated method stub
				// sql语句
				StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_maintenance ");
				// 参数
				List<Object> param = new LinkedList<>();
				// 判断条件
				if (maintenance != null) {
					if (maintenance.getDevice() != null) {// 条件：年级
						int deviceid = maintenance.getDevice().getId();
						param.add(deviceid);
						sb.append("AND deviceid=? ");
					}
					if (maintenance.getOperator() != null) {// 条件：年级
						int operatorid = maintenance.getOperator().getId();
						param.add(operatorid);
						sb.append("AND operatorid=? ");
					}
					if (maintenance.getDispose() != null) {// 条件：年级
					
						sb.append("AND dispose like '%"+maintenance.getDispose()+"%' ");
					}
				}
				String sql = sb.toString().replaceFirst("AND", "WHERE");

				long count = dao.count(sql, param).intValue();

				return count;
			}

}
