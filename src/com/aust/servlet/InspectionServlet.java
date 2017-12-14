package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Inspection;
import com.aust.bean.Page;
import com.aust.service.InspectionService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class InspectionServlet
 */
public class InspectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private InspectionService service = new InspectionService();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InspectionServlet() {
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
		if ("toInspectionListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/inspectionList.jsp").forward(request, response);
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
				if ("InspectionList".equalsIgnoreCase(method)) { // 获取所有人员数据
					inspectionList(request, response);
				} else if ("AddInspection".equalsIgnoreCase(method)) { // 添加人员
					addInspection(request, response);
				} else if ("DeleteInspection".equalsIgnoreCase(method)) { // 删除人员
					deleteInspection(request, response);
				} else if ("EditInspection".equalsIgnoreCase(method)) { // 修改人员信息
					editInspection(request, response);
				}
	}
	
	private void editInspection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Enumeration<String> pNames = request.getParameterNames();
		Inspection inspection = new Inspection();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);

			try {
				BeanUtils.setProperty(inspection, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editInspection(inspection);
		response.getWriter().write("success");

	}

	private void deleteInspection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("ids[]");
		// System.out.println("ids" + ids);
		try {
			service.deleteInspection(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void addInspection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Inspection inspection = new Inspection();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);

				try {
					BeanUtils.setProperty(inspection, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addInspection(inspection);
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

	private void inspectionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		Inspection inspection = new Inspection();
//
		if (!StringTool.isEmpty(deviceid)) {
			inspection.setDeviceid(Integer.parseInt(deviceid));
		}
		if (!StringTool.isEmpty(operatorid)) {
			inspection.setOperatorid(Integer.parseInt(operatorid));
		}
		if (!StringTool.isEmpty(dispose)) {
			inspection.setDispose(dispose);
		}

		// 获取数据
		String result = service.getInspectionList(inspection, new Page(page, rows));
		// 返回数据
		 //System.out.println("result1"+result);
		response.getWriter().write(result);


	}

}
