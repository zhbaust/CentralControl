package com.aust.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aust.bean.Supplier;
import com.aust.bean.Employee;
import com.aust.bean.Page;
import com.aust.bean.Supplier;
import com.aust.dao.impl.SupplierDaoImpl;
import com.aust.dao.inter.SupplierDaoInter;
import com.aust.tools.MysqlTool;
import com.aust.tools.StringTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SupplierService {

	private SupplierDaoInter dao;

	public SupplierService() {
		super();
		this.dao = new SupplierDaoImpl();
	}

	public void editSupplier(Supplier supplier) {
		String sql = "";

		List<Object> params = new LinkedList<>();
		params.add(supplier.getName());
		params.add(supplier.getAddress());
		params.add(supplier.getTel());
		params.add(supplier.getEmail());
		params.add(supplier.getQq());
		params.add(supplier.getWechat());
		params.add(supplier.getLinkman());
		params.add(supplier.getRemark());

		sql = "UPDATE cc_supplier SET  name=?, address=?, tel=?, email=?, qq=?,wechat=?,linkman=?,remark=?  WHERE id=?";

		params.add(supplier.getId());
		// TODO Test
		// System.out.println("sql"+sql);
		// 更新学生信息
		dao.update(sql, params);

	}

	public void deleteSupplier(String[] ids) {
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
			// 删除供应商表
			dao.deleteTransaction(conn, "DELETE FROM cc_supplier WHERE id IN(" + mark + ")", sid);
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

	public int addSupplier(Supplier supplier) {
		if (getCountByName(supplier.getName()) > 0) // 如果已经存在了，则不写入
			return 0;
		// 添加部门记录
		// System.out.println("price:"+supplier.getPrice());
		dao.insert("INSERT INTO cc_supplier(name,address,tel,email,qq,wechat,linkman,remark) value(?,?,?,?,?,?,?,?)",
				new Object[] { supplier.getName(), supplier.getAddress(), supplier.getTel(), supplier.getEmail(),
						supplier.getQq(), supplier.getWechat(), supplier.getLinkman(), supplier.getRemark() });
		return 1;
	}

	private long getCountByName(String name) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_supplier ");
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

	public String getSupplierList(Supplier supplier, Page page) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM cc_supplier ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (supplier != null) {
			// System.out.println("did:"+supplier.getSupplierid());
			if (!StringTool.isEmpty(supplier.getQq())) {// 条件：供应商QQ
				String qq = supplier.getQq();
				sb.append("AND qq like '%" + qq + "%' ");
			}
			if (supplier.getName() != null) {// 条件：供应商名称
				String name = supplier.getName();
				sb.append("AND name like '%" + name + "%' ");
			}
			if (supplier.getTel() != null) {// 条件：供应商电话
				sb.append("AND tel like '%" + supplier.getTel() + "%' ");
			}
			if (supplier.getEmail() != null) {// 条件：供应商Email
				sb.append("AND email like '%" + supplier.getEmail() + "%' ");
			}
			if (supplier.getLinkman() != null) {// 条件：供应商联系人
				sb.append("AND linkman like '%" + supplier.getLinkman() + "%' ");
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
		List<Supplier> list = dao.getSupplierList(sql, param);
		// 获取总记录数
		long total = getCount(supplier);
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
	private long getCount(Supplier supplier) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) FROM cc_supplier ");
		// 参数
		List<Object> param = new LinkedList<>();
		// 判断条件
		if (supplier != null) {
			// System.out.println("did:"+supplier.getSupplierid());
			if (!StringTool.isEmpty(supplier.getQq())) {// 条件：供应商编号
				String qq = supplier.getQq();
				sb.append("AND qq like '%" + qq + "%' ");
			}
			if (supplier.getName() != null) {// 条件：供应商名称
				String name = supplier.getName();
				sb.append("AND name like '%" + name + "%' ");
			}
			if (supplier.getTel() != null) {// 条件：供应商型号
				sb.append("AND tel like '%" + supplier.getTel() + "%' ");
			}
			if (supplier.getEmail() != null) {// 条件：供应商型号
				sb.append("AND email like '%" + supplier.getEmail() + "%' ");
			}
			if (supplier.getLinkman() != null) {// 条件：供应商型号
				sb.append("AND linkman like '%" + supplier.getLinkman() + "%' ");
			}

		}
		String sql = sb.toString().replaceFirst("AND", "WHERE");

		long count = dao.count(sql, param).intValue();

		return count;
	}

	public String getAllSupplierList() {
		// 获取数据
		List<Object> list = dao.getList(Supplier.class, "SELECT * FROM cc_supplier");

		// 获取总记录数

		String result = JSONArray.fromObject(list).toString();
		// System.out.println("SQL:"+result);
		// 返回
		return result;
	}

}
