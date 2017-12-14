package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Inspectionplan;
import com.aust.bean.Inspectionplan;
import com.aust.bean.Page;
import com.aust.dao.impl.InspectionplanDaoImpl;
import com.aust.dao.inter.InspectionplanDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONObject;

public class InspectionplanService {
	private InspectionplanDaoInter dao;

	public InspectionplanService() {
		super();
		this.dao = new InspectionplanDaoImpl();
	}

	public void editInspectionplan(Inspectionplan inspectionplan) {
		String sql = "";

		List<Object> params = new LinkedList<>();
		params.add(inspectionplan.getInspectioninterval());
		params.add(inspectionplan.getContent());


		if (inspectionplan.getDevice() == null || inspectionplan.getOperator() == null) {
			sql = "UPDATE cc_inspectionplan SET inspectioninterval=?,  content=? WHERE deviceid= ?";
		} else {
			sql = "UPDATE cc_inspectionplan SET inspectioninterval=?,  content=?, operatorid=?  WHERE deviceid= ?";

			params.add(inspectionplan.getOperatorid());
		}
		params.add(inspectionplan.getDeviceid());
		// params.add(inspectionplan.getId());
		// System.out.println("update:"+sql);
		// 更新出库信息信息
		dao.update(sql, params);
		
	}

	public void deleteInspectionplan(String[] ids) {
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
					dao.deleteTransaction(conn, "DELETE FROM cc_inspectionplan WHERE id IN(" + mark + ")", sid);
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

	public int addInspectionplan(Inspectionplan inspectionplan) {
		dao.insert(
				"INSERT INTO cc_inspectionplan(deviceid, inspectioninterval,operatorid,content) value(?,?,?,?)",
				new Object[] { inspectionplan.getDeviceid(), inspectionplan.getInspectioninterval(),
						inspectionplan.getOperatorid(), inspectionplan.getContent() });
		return 1;
	}

	public String getInspectionplanList(Inspectionplan inspectionplan, Page page) {
		// sql语句
				StringBuffer sb = new StringBuffer("SELECT * FROM cc_inspectionplan ");
				// 参数
				List<Object> param = new LinkedList<>();
				// // 判断条件
				if (inspectionplan != null) {
					if (inspectionplan.getDevice() != null) {// 条件：年级
						int deviceid = inspectionplan.getDevice().getId();
						param.add(deviceid);
						sb.append("AND deviceid=? ");
					}
					if (inspectionplan.getOperator() != null) {// 条件：年级
						int operatorid = inspectionplan.getOperator().getId();
						param.add(operatorid);
						sb.append("AND operatorid=? ");
					}
					if (inspectionplan.getContent() != null) {
						sb.append("AND content like '%" + inspectionplan.getContent() + "%' ");
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
				List<Inspectionplan> list = dao.getInspectionplanList(sql, param);
				// 获取总记录数
				long total = getCount(inspectionplan);
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
			 * @param inspectionplan
			 * @param page
			 * @return
			 */
			private long getCount(Inspectionplan inspectionplan) {
				
				// sql语句
				StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_inspectionplan ");
				// 参数
				List<Object> param = new LinkedList<>();
				// 判断条件
				if (inspectionplan != null) {
					if (inspectionplan.getDevice() != null) {// 条件：年级
						int deviceid = inspectionplan.getDevice().getId();
						param.add(deviceid);
						sb.append("AND deviceid=? ");
					}
					if (inspectionplan.getOperator() != null) {// 条件：年级
						int operatorid = inspectionplan.getOperator().getId();
						param.add(operatorid);
						sb.append("AND operatorid=? ");
					}
					if (inspectionplan.getContent() != null) {
						sb.append("AND content like '%" + inspectionplan.getContent() + "%' ");
					}
					
				}
				String sql = sb.toString().replaceFirst("AND", "WHERE");

				long count = dao.count(sql, param).intValue();

				return count;
			}

}
