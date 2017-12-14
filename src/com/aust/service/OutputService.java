package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Output;
import com.aust.bean.Output;
import com.aust.bean.Output;
import com.aust.bean.Page;
import com.aust.dao.impl.OutputDaoImpl;
import com.aust.dao.inter.OutputDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONObject;

public class OutputService {
	private OutputDaoInter dao;

	public OutputService() {
		super();
		this.dao = new OutputDaoImpl();
	}

	public void editOutput(Output output) {
		String sql = "";

		List<Object> params = new LinkedList<>();
		params.add(StringTool.StrTosqlDate(output.getOutputdate()));

		params.add(output.getNum());

		params.add(output.getRemark());

		if (output.getDevice() == null || output.getReceiver() == null || output.getSender() == null) {
			sql = "UPDATE cc_output SET outputdate=?,  num=?, remark=? WHERE deviceid=?";
		} else {
			sql = "UPDATE cc_output SET outputdate=?,  num=?, remark=?,receiveid=?,sendid=?  WHERE deviceid=?";

			params.add(output.getReceiveid());
			params.add(output.getSendid());
		}
		params.add(output.getDeviceid());
		// params.add(output.getId());
		// System.out.println("update:"+sql);
		// 更新出库信息信息
		dao.update(sql, params);

	}

	public void deleteOutput(String[] ids) {
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
			dao.deleteTransaction(conn, "DELETE FROM cc_output WHERE id IN(" + mark + ")", sid);
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

	public int addOutput(Output output) {
		// 添加员工记录
		dao.insert("INSERT INTO cc_output(deviceid, outputdate,num,receiveid,sendid,remark) value(?,?,?,?,?,?)",
				new Object[] { output.getDeviceid(), StringTool.StrTosqlDate(output.getOutputdate()), output.getNum(),
						output.getReceiveid(), output.getSendid(), output.getRemark() });
		return 1;
	}

	public String getOutputList(Output output, Page page) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM cc_output ");
		// 参数
		List<Object> param = new LinkedList<>();
		// // 判断条件
		if (output != null) {
			if (output.getDevice() != null) {// 条件：年级
				int deviceid = output.getDevice().getId();
				param.add(deviceid);
				sb.append("AND deviceid=? ");
			}
			if (output.getReceiver() != null) {// 条件：年级
				int receiveid = output.getReceiver().getId();
				param.add(receiveid);
				sb.append("AND receiveid=? ");
			}
			if (output.getSender() != null) {// 条件：年级
				int sendid = output.getSender().getId();
				param.add(sendid);
				sb.append("AND sendid=? ");
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
		List<Output> list = dao.getOutputList(sql, param);
		// 获取总记录数
		long total = getCount(output);
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
	 * @param output
	 * @param page
	 * @return
	 */
	private long getCount(Output output) {
		// TODO Auto-generated method stub
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_output ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (output != null) {
			if (output.getDevice() != null) {// 条件：年级
				int deviceid = output.getDevice().getId();
				param.add(deviceid);
				sb.append("AND deviceid=? ");
			}
			if (output.getReceiver() != null) {// 条件：年级
				int receiveid = output.getReceiver().getId();
				param.add(receiveid);
				sb.append("AND receiveid=? ");
			}
			if (output.getSender() != null) {// 条件：年级
				int sendid = output.getSender().getId();
				param.add(sendid);
				sb.append("AND sendid=? ");
			}
		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");

		long count = dao.count(sql, param).intValue();

		return count;
	}

}
