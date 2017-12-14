package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Inspectionplan;
import com.aust.bean.Page;
import com.aust.service.InspectionplanService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class InspectionplanServlet
 */
public class InspectionplanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private InspectionplanService service = new InspectionplanService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InspectionplanServlet() {
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
		if ("toInspectionplanListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/inspectionplanList.jsp").forward(request, response);
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
				if ("InspectionplanList".equalsIgnoreCase(method)) { // 获取所有人员数据
					inspectionplanList(request, response);
				} else if ("AddInspectionplan".equalsIgnoreCase(method)) { // 添加人员
					addInspectionplan(request, response);
				} else if ("DeleteInspectionplan".equalsIgnoreCase(method)) { // 删除人员
					deleteInspectionplan(request, response);
				} else if ("EditInspectionplan".equalsIgnoreCase(method)) { // 修改人员信息
					editInspectionplan(request, response);
				}
	}
	
	private void editInspectionplan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Enumeration<String> pNames = request.getParameterNames();
		Inspectionplan inspectionplan = new Inspectionplan();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);

			try {
				BeanUtils.setProperty(inspectionplan, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editInspectionplan(inspectionplan);
		response.getWriter().write("success");

	}

	private void deleteInspectionplan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("ids[]");
		// System.out.println("ids" + ids);
		try {
			service.deleteInspectionplan(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void addInspectionplan(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Inspectionplan inspectionplan = new Inspectionplan();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);

				try {
					BeanUtils.setProperty(inspectionplan, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addInspectionplan(inspectionplan);
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

	private void inspectionplanList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 年级ID
		String deviceid = request.getParameter("deviceid");

		// 班级ID
		String operatorid = request.getParameter("operatorid");
		String content = request.getParameter("content");
//		
		//String starttime =
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		// System.out.println(page+":"+operatorid);
		// // 封装参数
		Inspectionplan inspectionplan = new Inspectionplan();
		//
		if (!StringTool.isEmpty(deviceid)) {
			inspectionplan.setDeviceid(Integer.parseInt(deviceid));
		}
		if (!StringTool.isEmpty(operatorid)) {
			inspectionplan.setOperatorid(Integer.parseInt(operatorid));
		}
		if (!StringTool.isEmpty(content)) {
			inspectionplan.setContent(content);
		}

		// 获取数据
		String result = service.getInspectionplanList(inspectionplan, new Page(page, rows));
		// 返回数据
		//System.out.println("result1"+result);
		response.getWriter().write(result);

	}

}
