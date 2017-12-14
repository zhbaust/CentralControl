package com.aust.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Device;
import com.aust.bean.Employee;
import com.aust.bean.Maintenance;
import com.aust.bean.Maintenance;
import com.aust.dao.inter.MaintenanceDaoInter;
import com.aust.tools.MysqlTool;

public class MaintenanceDaoImpl extends BaseDaoImpl implements MaintenanceDaoInter {

	@Override
	public List<Maintenance> getMaintenanceList(String sql, List<Object> param) {
		// 数据集合
		List<Maintenance> list = new LinkedList<>();
		try {
			// 获取数据库连接
			Connection conn = MysqlTool.getConnection();
			// 预编译
			PreparedStatement ps = conn.prepareStatement(sql);
			// 设置参数
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					ps.setObject(i + 1, param.get(i));
				}
			}
			// 执行sql语句
			ResultSet rs = ps.executeQuery();
			// 获取元数据
			ResultSetMetaData meta = rs.getMetaData();
			// 遍历结果集
			while (rs.next()) {
				// 创建对象
				Maintenance maintenance = new Maintenance();
				// 遍历每个字段
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					String field = meta.getColumnName(i);
					BeanUtils.setProperty(maintenance, field, rs.getObject(field));
				}
				// 查询部门
				Device device = (Device) getObject(Device.class, "SELECT * FROM cc_device WHERE id=?",
						new Object[] { maintenance.getDeviceid() });
				// 查询工种
				Employee operator = (Employee) getObject(Employee.class, "SELECT * FROM cc_employee WHERE id=?",
						new Object[] { maintenance.getOperatorid() });
			

				// 添加
				maintenance.setDevice(device);
				maintenance.setOperator(operator);
				// 添加到集合
				list.add(maintenance);
			}
			// 关闭连接
			MysqlTool.closeConnection();
			MysqlTool.close(ps);
			MysqlTool.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
