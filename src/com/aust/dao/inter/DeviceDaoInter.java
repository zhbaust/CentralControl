package com.aust.dao.inter;

import java.util.List;

import com.aust.bean.Device;


public interface DeviceDaoInter extends BaseDaoInter {
	/**
	 * 获取设备信息，
	 * @param sql 要执行的sql语句
	 * @param param 参数
	 * @return
	 */
	public List<Device> getDeviceList(String sql, List<Object> param);
}
