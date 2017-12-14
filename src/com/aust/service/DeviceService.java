package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Department;
import com.aust.bean.Device;
import com.aust.bean.Device;
import com.aust.bean.Page;
import com.aust.dao.impl.DeviceDaoImpl;
import com.aust.dao.inter.DeviceDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DeviceService {
	private DeviceDaoInter dao;

	public DeviceService() {
		super();
		this.dao = new DeviceDaoImpl();
	}

	public void editDevice(Device device) {
		// TODO Auto-generated method stub
		String sql = "";

		List<Object> params = new LinkedList<>();
		params.add(device.getDeviceid());
		params.add(device.getName());
		params.add(device.getModal());
		params.add(device.getNum());
		params.add(device.getPrice());
		params.add(device.getRemark());

		sql = "UPDATE cc_device SET deviceid=?, name=?, modal=?, num=?, price=?, remark=?  WHERE id=?";

		params.add(device.getId());
		// TODO Test
		// System.out.println("sql"+sql);
		// 更新学生信息
		dao.update(sql, params);
	}

	public void deleteDevice(String[] ids) {
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
			// 删除设备表
			dao.deleteTransaction(conn, "DELETE FROM cc_device WHERE id IN(" + mark + ")", sid);
			//
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

	public int addDevice(Device device) {
		// TODO Auto-generated method stub
		if (getCountByName(device.getName()) > 0) // 如果已经存在了，则不写入
			return 0;
		// 添加部门记录
		// System.out.println("price:"+device.getPrice());
		dao.insert("INSERT INTO cc_device(deviceid, name, modal, num,price, remark) value(?,?,?,?,?,?)",
				new Object[] { device.getDeviceid(), device.getName(), device.getModal(), device.getNum(),
						device.getPrice() + "", device.getRemark() });
		return 1;
	}

	private long getCountByName(String name) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_device ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (name != null) {
			param.add(name);
			sb.append("where name=? ");
		}
		String sql = sb.toString();

		long count = dao.count(sql, param).intValue();

		return count;
	}

	public String getDeviceList(Device device, Page page) {
		// TODO Auto-generated method stub
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM cc_device ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (device != null) {
			// System.out.println("did:"+device.getDeviceid());
			if (!StringTool.isEmpty(device.getDeviceid())) {// 条件：设备编号
				String deviceid = device.getDeviceid();
				sb.append("AND deviceid like '%" + deviceid + "%' ");
			}
			if (device.getName() != null) {// 条件：设备名称
				String name = device.getName();
				sb.append("AND name like '%" + name + "%' ");
			}
			if (device.getModal() != null) {// 条件：设备型号
				String modal = device.getModal();
				sb.append("AND modal like '%" + modal + "%' ");
			}

		}
		// 添加排序
		sb.append("ORDER BY id ASC ");
		// 分页
		if (page != null) {
			param.add(page.getStart());
			param.add(page.getSize());
			sb.append("limit ?,?");
		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");
		// 获取数据
		// System.out.println(sql);
		List<Device> list = dao.getDeviceList(sql, param);
		// 获取总记录数
		long total = getCount(device);
		// 定义Map
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// total键 存放总记录数，必须的
		jsonMap.put("total", total);
		// rows键 存放每页记录 list
		jsonMap.put("rows", list);
		// 格式化Map,以json格式返回数据
		String result = JSONObject.fromObject(jsonMap).toString();
		// System.out.println("SQL:"+result);
		// 返回
		return result;
	}

	/**
	 * 获取记录数
	 * 
	 * @param student
	 * @param page
	 * @return
	 */
	private long getCount(Device device) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_device ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (device != null) {
			if (device.getDeviceid() != null) {// 条件：设备编号
				String deviceid = device.getDeviceid();
				sb.append("AND deviceid like '%" + deviceid + "%' ");
			}
			if (device.getName() != null) {// 条件：设备名称
				String name = device.getName();
				sb.append("AND name like '%" + name + "%' ");
			}
			if (device.getModal() != null) {// 条件：设备型号
				String modal = device.getModal();
				sb.append("AND modal like '%" + modal + "%' ");
			}

		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");

		long count = dao.count(sql, param).intValue();

		return count;
	}

	public String getAllDeviceList() {
		// TODO Auto-generated method stub
		//获取数据
		List<Object> list = dao.getList(Device.class, "SELECT * FROM cc_device");
		
		//获取总记录数
		
        String result = JSONArray.fromObject(list).toString();
        //System.out.println("SQL:"+result);
        //返回
		return result;
	}

}
