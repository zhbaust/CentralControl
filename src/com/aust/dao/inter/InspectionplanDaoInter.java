package com.aust.dao.inter;

import java.util.List;

import com.aust.bean.Inspectionplan;

public interface InspectionplanDaoInter extends BaseDaoInter {
	/**
	 * 获取人员信息，这里需要将人员的部门、权限和工种等信息封装进去
	 * @param sql 要执行的sql语句
	 * @param param 参数
	 * @return
	 */
	public List<Inspectionplan> getInspectionplanList(String sql, List<Object> param);

}
