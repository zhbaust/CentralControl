package com.aust.dao.inter;

import java.util.List;

import com.aust.bean.Jobtransfer;

public interface JobTransferDaoInter extends BaseDaoInter {

	/**
	 * 获取部门信息，这里需要将领导信息封装进去
	 * @param sql 要执行的sql语句
	 * @param param 参数
	 * @return
	 */
	public List<Jobtransfer> getJobtransferList(String sql, List<Object> param);

}
