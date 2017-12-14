package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Page;
import com.aust.bean.Maintenance;
import com.aust.service.MaintenanceService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class MaintenanceServlet
 */
public class MaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MaintenanceService service = new MaintenanceService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MaintenanceServlet() {
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
		if ("toMaintenanceListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/maintenanceList.jsp").forward(request, response);
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
		if ("MaintenanceList".equalsIgnoreCase(method)) { // 获取所有人员数据
			maintenanceList(request, response);
		} else if ("AddMaintenance".equalsIgnoreCase(method)) { // 添加人员
			addMaintenance(request, response);
		} else if ("DeleteMaintenance".equalsIgnoreCase(method)) { // 删除人员
			deleteMaintenance(request, response);
		} else if ("EditMaintenance".equalsIgnoreCase(method)) { // 修改人员信息
			editMaintenance(request, response);
		}
	}

	private void editMaintenance(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Enumeration<String> pNames = request.getParameterNames();
		Maintenance maintenance = new Maintenance();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);

			try {
				BeanUtils.setProperty(maintenance, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editMaintenance(maintenance);
		response.getWriter().write("success");

	}

	private void deleteMaintenance(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("ids[]");
		// System.out.println("ids" + ids);
		try {
			service.deleteMaintenance(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void addMaintenance(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Maintenance maintenance = new Maintenance();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);

				try {
					BeanUtils.setProperty(maintenance, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addMaintenance(maintenance);
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

	private void maintenanceList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 年级ID
		String deviceid = request.getParameter("deviceid");

		// 班级ID
		String operatorid = request.getParameter("operatorid");
		String dispose = request.getParameter("dispose");
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
//
//		 System.out.println(page+":"+operatorid);
//		// 封装参数
		Maintenance maintenance = new Maintenance();
//
		if (!StringTool.isEmpty(deviceid)) {
			maintenance.setDeviceid(Integer.parseInt(deviceid));
		}
		if (!StringTool.isEmpty(operatorid)) {
			maintenance.setOperatorid(Integer.parseInt(operatorid));
		}
		if (!StringTool.isEmpty(dispose)) {
			maintenance.setDispose(dispose);
		}

		// 获取数据
		String result = service.getMaintenanceList(maintenance, new Page(page, rows));
		// 返回数据
		 //System.out.println("result1"+result);
		response.getWriter().write(result);


	}

}
