package com.aust.dao.inter;

import java.util.List;

import com.aust.bean.Supplier;

public interface SupplierDaoInter extends BaseDaoInter {
	/**
	 * 获取供应商信息，
	 * @param sql 要执行的sql语句
	 * @param param 参数
	 * @return
	 */
	public List<Supplier> getSupplierList(String sql, List<Object> param);
}
