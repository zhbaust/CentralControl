package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Output;
import com.aust.bean.Output;
import com.aust.bean.Page;
import com.aust.bean.Output;
import com.aust.service.OutputService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class OutputServlet
 */
public class OutputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OutputService service = new OutputService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OutputServlet() {
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
		if ("toOutputListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/outputList.jsp").forward(request, response);
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
		if ("OutputList".equalsIgnoreCase(method)) { // 获取所有人员数据
			outputList(request, response);
		} else if ("AddOutput".equalsIgnoreCase(method)) { // 添加人员
			addOutput(request, response);
		} else if ("DeleteOutput".equalsIgnoreCase(method)) { // 删除人员
			deleteOutput(request, response);
		} else if ("EditOutput".equalsIgnoreCase(method)) { // 修改人员信息
			editOutput(request, response);
		}
	}

	private void editOutput(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Enumeration<String> pNames = request.getParameterNames();
		Output output = new Output();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);

			try {
				BeanUtils.setProperty(output, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editOutput(output);
		response.getWriter().write("success");

	}

	private void deleteOutput(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("ids[]");
		// System.out.println("ids" + ids);
		try {
			service.deleteOutput(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void addOutput(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Output output = new Output();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);

				try {
					BeanUtils.setProperty(output, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addOutput(output);
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

	private void outputList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 年级ID
		String deviceid = request.getParameter("deviceid");

		// 班级ID
		String receiveid = request.getParameter("receiveid");
		String sendid = request.getParameter("sendid");
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		// System.out.println(page+":"+operatorid);
		// // 封装参数
		Output output = new Output();
		//
		if (!StringTool.isEmpty(deviceid)) {
			output.setDeviceid(Integer.parseInt(deviceid));
		}
		if (!StringTool.isEmpty(receiveid)) {
			output.setReceiveid(Integer.parseInt(receiveid));
		}
		if (!StringTool.isEmpty(sendid)) {
			output.setSendid(Integer.parseInt(sendid));
		}

		// 获取数据
		String result = service.getOutputList(output, new Page(page, rows));
		// 返回数据
		// System.out.println("result1"+result);
		response.getWriter().write(result);

	}

}
