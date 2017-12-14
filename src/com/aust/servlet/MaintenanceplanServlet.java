package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Maintenanceplan;
import com.aust.bean.Maintenanceplan;
import com.aust.bean.Page;
import com.aust.bean.Maintenanceplan;
import com.aust.service.MaintenanceplanService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class MaintenanceplanServlet
 */
public class MaintenanceplanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MaintenanceplanService service = new MaintenanceplanService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MaintenanceplanServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的方法
		String method = request.getParameter("method");
		// System.out.println("doGet"+method);
		if ("toMaintenanceplanListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/maintenanceplanList.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的方法
		String method = request.getParameter("method");
		// 请求分发
		if ("MaintenanceplanList".equalsIgnoreCase(method)) { // 获取所有人员数据
			maintenanceplanList(request, response);
		} else if ("AddMaintenanceplan".equalsIgnoreCase(method)) { // 添加人员
			addMaintenanceplan(request, response);
		} else if ("DeleteMaintenanceplan".equalsIgnoreCase(method)) { // 删除人员
			deleteMaintenanceplan(request, response);
		} else if ("EditMaintenanceplan".equalsIgnoreCase(method)) { // 修改人员信息
			editMaintenanceplan(request, response);
		}
	}

	private void editMaintenanceplan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Enumeration<String> pNames = request.getParameterNames();
		Maintenanceplan maintenanceplan = new Maintenanceplan();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);

			try {
				BeanUtils.setProperty(maintenanceplan, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editMaintenanceplan(maintenanceplan);
		response.getWriter().write("success");

	}

	private void deleteMaintenanceplan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("ids[]");
		// System.out.println("ids" + ids);
		try {
			service.deleteMaintenanceplan(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void addMaintenanceplan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Maintenanceplan maintenanceplan = new Maintenanceplan();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);

				try {
					BeanUtils.setProperty(maintenanceplan, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addMaintenanceplan(maintenanceplan);
			if (r == 1) {
				response.getWriter().write("success");
			} else {
				response.getWriter().write("duplicate");
			}
		} catch (IOException e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void maintenanceplanList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 年级ID
		String deviceid = request.getParameter("deviceid");

		// 班级ID
		String operatorid = request.getParameter("operatorid");
		String content = request.getParameter("content");
		
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		//String starttime =
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		// System.out.println(page+":"+operatorid);
		// // 封装参数
		Maintenanceplan maintenanceplan = new Maintenanceplan();
		//
		if (!StringTool.isEmpty(deviceid)) {
			maintenanceplan.setDeviceid(Integer.parseInt(deviceid));
		}
		if (!StringTool.isEmpty(operatorid)) {
			maintenanceplan.setOperatorid(Integer.parseInt(operatorid));
		}
		if (!StringTool.isEmpty(content)) {
			maintenanceplan.setContent(content);
		}
		if (!StringTool.isEmpty(starttime)&&!StringTool.isEmpty(endtime)) {
			maintenanceplan.setRemindtime(starttime+"@"+endtime);
		}

		// 获取数据
		String result = service.getMaintenanceplanList(maintenanceplan, new Page(page, rows));
		// 返回数据
		//System.out.println("result1"+result);
		response.getWriter().write(result);

	}

}
