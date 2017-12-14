package com.aust.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Command;
import com.aust.bean.Command;
import com.aust.bean.Employee;
import com.aust.dao.inter.CommandDaoInter;
import com.aust.tools.MysqlTool;

public class CommandDaoImpl extends BaseDaoImpl implements CommandDaoInter {

	@Override
	public List<Command> getCommandList(String sql, List<Object> param) {
		
		// 数据集合
		List<Command> list = new LinkedList<>();
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
				Command command = new Command();
				// 遍历每个字段
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					String field = meta.getColumnName(i);
					// StringTool.print("field "+field);
					// StringTool.print("value "+rs.getObject(field));
					BeanUtils.setProperty(command, field, rs.getObject(field));
				}
				// command.setId(rs.getInt("id"));
				// command.setName(rs.getString("name"));
				// command.setLeadid(rs.getInt("leadID"));//错误原因，数据库字段名的id是大写的ID，所以错误，和Command类中的属性leadid不符合。
				// command.setTel(rs.getString("tel"));
				// command.setRemark(rs.getString("remark"));
				// 查询班级
				// command.setLeadid(1);
				// System.out.println("daoimpl:"+command.getName()+":"+command.getLeadid());
				Employee publisher = (Employee) getObject(Employee.class, "SELECT * FROM cc_employee WHERE id= ?",
						new Object[] { command.getPublisherid() });
//				Employee publisher = (Employee) getObject(Employee.class, "SELECT * FROM cc_employee WHERE id= ?",
//						new Object[] { command.getPublisherid() });
				// Employee employee = (Employee) getObject(Employee.class,
				// "SELECT * FROM cc_employee WHERE id=1");
				// 查询年级
				// Grade grade = (Grade) getObject(Grade.class, "SELECT * FROM
				// grade WHERE id=?", new Object[]{stu.getGradeid()});
				// 添加
				command.setPublisher(publisher);
				
				// 添加到集合
				list.add(command);
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
