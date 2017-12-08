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
import com.aust.bean.Supplier;
import com.aust.service.SupplierService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class SupplierServlet
 */
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SupplierService service = new SupplierService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierServlet() {
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
		if ("toSupplierListView".equalsIgnoreCase(method)) { // 转发到部门列表页
			request.getRequestDispatcher("/WEB-INF/view/Device/supplierList.jsp").forward(request, response);
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
		if ("SupplierList".equalsIgnoreCase(method)) { // 获取所有学生数据
			supplierList(request, response);
		} else if ("AddSupplier".equalsIgnoreCase(method)) { // 添加学生
			addSupplier(request, response);
		} else if ("DeleteSupplier".equalsIgnoreCase(method)) { // 删除学生
			deleteSupplier(request, response);
		} else if ("EditSupplier".equalsIgnoreCase(method)) { // 修改学生信息
			editSupplier(request, response);
		} else if ("SupplierListQuery".equalsIgnoreCase(method)) { // 获取当前学生班级的所有学生
			supplierList(request, response);
		}
	}

	private void editSupplier(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		Enumeration<String> pNames = request.getParameterNames();
		Supplier supplier = new Supplier();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			System.out.println(pName + ":" + value);
			try {
				BeanUtils.setProperty(supplier, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editSupplier(supplier);
		response.getWriter().write("success");

	}

	private void deleteSupplier(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取要删除的设备号
		// String[] numbers = request.getParameterValues("numbers[]");
		String[] ids = request.getParameterValues("ids[]");
		try {
			service.deleteSupplier(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}

	}

	private void addSupplier(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Supplier supplier = new Supplier();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);
				try {
					BeanUtils.setProperty(supplier, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addSupplier(supplier);
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

	private void supplierList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 查询用的，暂时不写
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String qq = request.getParameter("qq");
		String linkman = request.getParameter("linkman");

		// System.out.println("supplierid:"+supplierid);
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		// 封装参数
		Supplier supplier = new Supplier();

		if (!StringTool.isEmpty(name)) {
			supplier.setName(name);
		}
		if (!StringTool.isEmpty(tel)) {
			supplier.setTel(tel);
		}
		if (!StringTool.isEmpty(email)) {
			supplier.setEmail(email);
		}
		if (!StringTool.isEmpty(qq)) {
			supplier.setQq(qq);
		}
		if (!StringTool.isEmpty(linkman)) {
			supplier.setLinkman(linkman);
		}

		// 获取数据
		String result = service.getSupplierList(supplier, new Page(page, rows));
		// 返回数据
		// System.out.println("2017-12-8："+result);
		response.getWriter().write(result);

	}

}
