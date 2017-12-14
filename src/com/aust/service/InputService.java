package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Input;
import com.aust.bean.Input;
import com.aust.bean.Page;
import com.aust.bean.Input;
import com.aust.dao.impl.InputDaoImpl;
import com.aust.dao.inter.InputDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONObject;

public class InputService {
	private InputDaoInter dao;

	public InputService() {
		super();
		this.dao = new InputDaoImpl();
	}

	public void editInput(Input input) {
		String sql = "";
	
		List<Object> params = new LinkedList<>();
		params.add(StringTool.StrTosqlDate(input.getInputdate()));

		params.add(StringTool.StrTosqlDate(input.getProductiondate()));

		params.add(input.getNum());
		params.add(input.getValidity());

		params.add(input.getRemark());

		if (input.getDevice() == null || input.getOperator() == null || input.getSupplier() == null) {
			sql = "UPDATE cc_input SET inputdate=?,  productiondate=?, num=?, validity=?, remark=? WHERE deviceid=?";
		} else {
			sql = "UPDATE cc_input SET inputdate=?,  productiondate=?, num=?, validity=?, remark=?,operatorid=?,supplierid=?  WHERE deviceid=?";
			
			params.add(input.getOperatorid());
			params.add(input.getSupplierid());
		}
		params.add(input.getDeviceid());
		// params.add(input.getId());
		// System.out.println("update:"+sql);
		// 更新学生信息
		dao.update(sql, params);
	}

	public void deleteInput(String[] ids) {
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
			dao.deleteTransaction(conn, "DELETE FROM cc_input WHERE id IN(" + mark + ")", sid);
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

	public int addInput(Input input) {
		// TODO Auto-generated method stub
		// if (getInputCountByName(input.getLoginname()) > 0) // 如果已经存在了，则不写入
		// return 0;

		// 添加员工记录
		dao.insert(
				"INSERT INTO cc_input(deviceid, inputdate,productiondate,num,operatorid, validity,supplierid,remark) value(?,?,?,?,?,?,?,?)",
				new Object[] { input.getDeviceid(), StringTool.StrTosqlDate(input.getInputdate()),
						StringTool.StrTosqlDate(input.getProductiondate()), input.getNum(), input.getOperatorid(),
						input.getValidity(), input.getSupplierid(), input.getRemark() });
		return 1;

	}

	public String getInputList(Input input, Page page) {
		// sql语句
				StringBuffer sb = new StringBuffer("SELECT * FROM cc_input ");
				// 参数
				List<Object> param = new LinkedList<>();
//				// 判断条件
			if (input != null) {
					if (input.getDevice() != null) {// 条件：年级
						int deviceid = input.getDevice().getId();
						param.add(deviceid);
						sb.append("AND deviceid=? ");
					}
					if (input.getOperator() != null) {// 条件：年级
						int operatorid = input.getOperator().getId();
						param.add(operatorid);
						sb.append("AND operatorid=? ");
					}
					if (input.getSupplier()!= null) {// 条件：年级
						int supplierid = input.getSupplier().getId();
						param.add(supplierid);
						sb.append("AND supplierid=? ");
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
				 System.out.println("sql: "+sql);
				List<Input> list = dao.getInputList(sql, param);
				// 获取总记录数
				long total = getCount(input);
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
	 * @param input
	 * @param page
	 * @return
	 */
	private long getCount(Input input) {
		// TODO Auto-generated method stub
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_input ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (input != null) {
		if (input.getDevice() != null) {// 条件：年级
			int deviceid = input.getDevice().getId();
			param.add(deviceid);
			sb.append("AND deviceid=? ");
		}
		if (input.getOperator() != null) {// 条件：年级
			int operatorid = input.getOperator().getId();
			param.add(operatorid);
			sb.append("AND operatorid=? ");
		}
		if (input.getSupplier()!= null) {// 条件：年级
			int supplierid = input.getSupplier().getId();
			param.add(supplierid);
			sb.append("AND supplierid=? ");
		}
	}
		String sql = sb.toString().replaceFirst("AND", "WHERE");

		long count = dao.count(sql, param).intValue();

		return count;
	}

}
