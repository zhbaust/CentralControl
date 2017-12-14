package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Input;
import com.aust.bean.Input;
import com.aust.bean.Page;
import com.aust.bean.Input;
import com.aust.service.InputService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class InputServlet
 */
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private InputService service = new InputService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InputServlet() {
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
		if ("toInputListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/inputList.jsp").forward(request, response);
		} else if ("toInputNoteListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Input/inputNoteList.jsp").forward(request, response);
		} else if ("toInputPersonalView".equalsIgnoreCase(method)) { // 转发到人员个人列表页
			// toPersonal(request, response);
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
		if ("InputList".equalsIgnoreCase(method)) { // 获取所有人员数据
			inputList(request, response);
		} else if ("AddInput".equalsIgnoreCase(method)) { // 添加人员
			addInput(request, response);
		} else if ("DeleteInput".equalsIgnoreCase(method)) { // 删除人员
			deleteInput(request, response);
		} else if ("EditInput".equalsIgnoreCase(method)) { // 修改人员信息
			editInput(request, response);
		}
		// else if ("InputDepartmentList".equalsIgnoreCase(method)) { //
		// 获取当前部门的所有人员
		// inputDepartmentList(request, response);
		// } else if ("InputAuthorityList".equalsIgnoreCase(method)) { //
		// 获取当前权限的所有人员
		// inputAuthorityList(request, response);
		// } else if ("InputProfessionList".equalsIgnoreCase(method)) { //
		// 获取当前工种的所有人员
		// inputProfessionList(request, response);
		// }
		else if ("InputListQuery".equalsIgnoreCase(method)) { // 获取当前工种的所有人员
			inputListQuery(request, response);
		}
	}

	private void inputListQuery(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void editInput(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		// String newdepartmentid = "";
		Enumeration<String> pNames = request.getParameterNames();
		Input input = new Input();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			// System.out.println(pName + ":" + value);
			// if (pName.equals("newdepartmentid")) {
			// newdepartmentid = value;
			// continue;
			// }
			try {
				BeanUtils.setProperty(input, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		// Input user = (Input) request.getSession().getAttribute("user");
		service.editInput(input);
		response.getWriter().write("success");

	}

	private void deleteInput(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("ids[]");
		System.out.println("ids" + ids);
		try {
			service.deleteInput(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void addInput(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Input input = new Input();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);

				try {
					BeanUtils.setProperty(input, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addInput(input);
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

	private void inputList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 年级ID
		String deviceid = request.getParameter("deviceid");

		// 班级ID
		String operatorid = request.getParameter("operatorid");
		String supplierid = request.getParameter("supplierid");
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
//
//		 System.out.println(page+":"+operatorid);
//		// 封装参数
		Input input = new Input();
//
		if (!StringTool.isEmpty(deviceid)) {
			input.setDeviceid(Integer.parseInt(deviceid));
		}
		if (!StringTool.isEmpty(operatorid)) {
			input.setOperatorid(Integer.parseInt(operatorid));
		}
		if (!StringTool.isEmpty(supplierid)) {
			input.setSupplierid(Integer.parseInt(supplierid));
		}

		// 获取数据
		String result = service.getInputList(input, new Page(page, rows));
		// 返回数据
		 //System.out.println("result1"+result);
		response.getWriter().write(result);

	}

}
