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
import com.aust.bean.Input;
import com.aust.bean.Input;
import com.aust.bean.Supplier;
import com.aust.dao.inter.InputDaoInter;
import com.aust.tools.MysqlTool;

public class InputDaoImpl extends BaseDaoImpl implements InputDaoInter {

	@Override
	public List<Input> getInputList(String sql, List<Object> param) {
		// 数据集合
		List<Input> list = new LinkedList<>();
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
				Input input = new Input();
				// 遍历每个字段
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					String field = meta.getColumnName(i);
					BeanUtils.setProperty(input, field, rs.getObject(field));
				}
				// 查询部门
				Device device = (Device) getObject(Device.class,
						"SELECT * FROM cc_device WHERE id=?", new Object[] { input.getDeviceid() });
				// 查询工种
				Employee operator = (Employee) getObject(Employee.class,
						"SELECT * FROM cc_employee WHERE id=?", new Object[] { input.getOperatorid() });
				// 查询权限
				Supplier supplier = (Supplier) getObject(Supplier.class, "SELECT * FROM cc_supplier WHERE id=?",
						new Object[] { input.getSupplierid()});
				// 添加
				input.setDevice(device);
				input.setOperator(operator);
				input.setSupplier(supplier);
				// 添加到集合
				list.add(input);
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
