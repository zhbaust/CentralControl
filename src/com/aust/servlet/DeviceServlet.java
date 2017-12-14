package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Device;
import com.aust.bean.Device;
import com.aust.bean.Page;
import com.aust.bean.Device;
import com.aust.service.DeviceService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class DeviceServlet
 */
public class DeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DeviceService service = new DeviceService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeviceServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 获取请求的方法
		String method = request.getParameter("method");
		if ("toDeviceListView".equalsIgnoreCase(method)) { // 转发到部门列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/deviceList.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 获取请求的方法
		String method = request.getParameter("method");
		// 请求分发
		if ("DeviceList".equalsIgnoreCase(method)) { // 获取所有学生数据
			deviceList(request, response);
		} else if ("AddDevice".equalsIgnoreCase(method)) { // 添加学生
			addDevice(request, response);
		} else if ("DeleteDevice".equalsIgnoreCase(method)) { // 删除学生
			deleteDevice(request, response);
		} else if ("EditDevice".equalsIgnoreCase(method)) { // 修改学生信息
			editDevice(request, response);
		} else if ("DeviceListQuery".equalsIgnoreCase(method)) { // 获取当前学生班级的所有学生
			deviceList(request, response);
		} else if ("DevicetoInputList".equalsIgnoreCase(method)) { // 获取当前学生班级的所有学生
			deviceToInputList(request, response);
		}
	}

	private void deviceToInputList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取数据
		String result = service.getAllDeviceList();
		// 返回数据
		
		response.getWriter().write(result);
	}

	private void editDevice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取参数名
		Enumeration<String> pNames = request.getParameterNames();
		Device device = new Device();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			System.out.println(pName + ":" + value);
			try {
				BeanUtils.setProperty(device, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editDevice(device);
		response.getWriter().write("success");
	}

	private void deleteDevice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取要删除的设备号
		// String[] numbers = request.getParameterValues("numbers[]");
		String[] ids = request.getParameterValues("ids[]");
		try {
			service.deleteDevice(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	private void addDevice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Device device = new Device();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);
				try {
					BeanUtils.setProperty(device, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addDevice(device);
			if (r == 1) {
				response.getWriter().write("success");
			} else {
				response.getWriter().write("duplicate");
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	private void deviceList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 查询用的，暂时不写
		String name = request.getParameter("name");
		String deviceid = request.getParameter("deviceid");
		String modal = request.getParameter("modal");

		// System.out.println("deviceid:"+deviceid);
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		// 封装参数
		Device device = new Device();

		if (!StringTool.isEmpty(name)) {
			device.setName(name);
		}
		if (!StringTool.isEmpty(deviceid)) {
			device.setDeviceid(deviceid);
		}
		if (!StringTool.isEmpty(modal)) {
			device.setModal(modal);
		}

		// 获取数据
		String result = service.getDeviceList(device, new Page(page, rows));
		// 返回数据
		// System.out.println("2017-12-8："+result);
		response.getWriter().write(result);
	}

}
