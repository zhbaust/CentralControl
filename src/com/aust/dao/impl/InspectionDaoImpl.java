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
import com.aust.bean.Inspection;
import com.aust.dao.inter.InspectionDaoInter;
import com.aust.tools.MysqlTool;

public class InspectionDaoImpl extends BaseDaoImpl implements InspectionDaoInter {

	@Override
	public List<Inspection> getInspectionList(String sql, List<Object> param) {
		// 数据集合
				List<Inspection> list = new LinkedList<>();
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
						Inspection inspection = new Inspection();
						// 遍历每个字段
						for (int i = 1; i <= meta.getColumnCount(); i++) {
							String field = meta.getColumnName(i);
							BeanUtils.setProperty(inspection, field, rs.getObject(field));
						}
						// 查询部门
						Device device = (Device) getObject(Device.class, "SELECT * FROM cc_device WHERE id=?",
								new Object[] { inspection.getDeviceid() });
						// 查询工种
						Employee operator = (Employee) getObject(Employee.class, "SELECT * FROM cc_employee WHERE id=?",
								new Object[] { inspection.getOperatorid() });
					

						// 添加
						inspection.setDevice(device);
						inspection.setOperator(operator);
						// 添加到集合
						list.add(inspection);
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
