package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Employee;
import com.aust.service.EmployeeService;
import com.aust.bean.Page;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeService service = new EmployeeService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
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
		if ("toEmployeeListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Employee/employeeList.jsp").forward(request, response);
		} else if ("toEmployeeNoteListView".equalsIgnoreCase(method)) { // 转发到人员列表页
			request.getRequestDispatcher("/WEB-INF/view/Employee/employeeNoteList.jsp").forward(request, response);
		} else if ("toEmployeePersonalView".equalsIgnoreCase(method)) { // 转发到人员个人列表页
			toPersonal(request, response);
		}
	}

	private void toPersonal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub 暂时没实现，个人登陆看到的个人主页
		Employee user = (Employee) request.getSession().getAttribute("user");
		Employee employee = service.getEmployee(user.getLoginname());
		request.getSession().setAttribute("userDetail", employee);
		request.getRequestDispatcher("/WEB-INF/view/Employee/employeePersonal.jsp").forward(request, response);

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
		if ("EmployeeList".equalsIgnoreCase(method)) { // 获取所有人员数据
			employeeList(request, response);
		} else if ("AddEmployee".equalsIgnoreCase(method)) { // 添加人员
			addEmployee(request, response);
		} else if ("DeleteEmployee".equalsIgnoreCase(method)) { // 删除人员
			deleteEmployee(request, response);
		} else if ("EditEmployee".equalsIgnoreCase(method)) { // 修改人员信息
			editEmployee(request, response);
		} else if ("EmployeeDepartmentList".equalsIgnoreCase(method)) { // 获取当前部门的所有人员
			employeeDepartmentList(request, response);
		} else if ("EmployeeAuthorityList".equalsIgnoreCase(method)) { // 获取当前权限的所有人员
			employeeAuthorityList(request, response);
		} else if ("EmployeeProfessionList".equalsIgnoreCase(method)) { // 获取当前工种的所有人员
			employeeProfessionList(request, response);
		} else if ("EmployeeListQuery".equalsIgnoreCase(method)) { // 获取当前工种的所有人员
			employeeListQuery(request, response);
		} else if ("EmployeeInputList".equalsIgnoreCase(method)) { // 获取当前工种的所有人员
			employeeInputList(request, response);
		}
	}

	private void employeeInputList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取数据
		String result = service.getAllEmployeeList();
		// 返回数据
		 //System.out.println("2017年11月28日 07:38:47："+result);
		response.getWriter().write(result);
	}

	private void employeeListQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// {name:name,tel:tel,cardnumber:cardnumber,departmentid:departmentid,professionid:professionid,authorityid:authorityid}
		String name = request.getParameter("name");
		// System.out.println("name:"+name);

		String tel = request.getParameter("tel");
		String cardnumber = request.getParameter("cardnumber");
		String departmentid = request.getParameter("departmentid");
		String professionid = request.getParameter("professionid");
		String authorityid = request.getParameter("authorityid");
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		Employee employee = new Employee();
		if (name != null)
			employee.setName(name);
		if (tel != null)
			employee.setTel(tel);
		if (cardnumber != null)
			employee.setCardnumber(cardnumber);
		if (departmentid.length() > 0)
			employee.setDepartmentid(Integer.parseInt(departmentid));
		if (professionid.length() > 0)
			employee.setProfessionid(Integer.parseInt(professionid));
		if (authorityid.length() > 0)
			employee.setAuthorityid(Integer.parseInt(authorityid));

		// clazzname="1";
		// System.out.println("clazzname"+clazzname);
		// if(StringTool.isEmpty(clazzname)){
		// return;
		// }
		String result = service.getEmployeeList(employee, new Page(page, rows));
		// System.out.println("result"+result);
		// 返回数据
		response.getWriter().write(result);
	}

	private void employeeProfessionList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void employeeAuthorityList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void employeeDepartmentList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void editEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取参数名
		String newdepartmentid = "";
		Enumeration<String> pNames = request.getParameterNames();
		Employee employee = new Employee();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			//System.out.println(pName + ":" + value);
			if (pName.equals("newdepartmentid")) {
				newdepartmentid = value;
				continue;
			}
			try {
				BeanUtils.setProperty(employee, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		Employee user = (Employee) request.getSession().getAttribute("user");
		service.editEmployee(employee, newdepartmentid, user.getId());
		response.getWriter().write("success");

	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取要删除的学号
		// String[] numbers = request.getParameterValues("numbers[]");
		String[] ids = request.getParameterValues("ids[]");
		//System.out.println("ids" + ids);
		try {
			service.deleteEmployee(ids);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Employee employee = new Employee();
			while (pNames.hasMoreElements()) {
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);

				try {
					BeanUtils.setProperty(employee, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r = service.addEmployee(employee);
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

	private void employeeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 年级ID
		String departmentid = request.getParameter("departmentid");

		// 班级ID
		String professionid = request.getParameter("professionid");
		String authorityid = request.getParameter("authorityid");
		// 获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		// System.out.println(page+":"+rows);
		// 封装参数
		Employee employee = new Employee();

		if (!StringTool.isEmpty(departmentid)) {
			employee.setDepartmentid(Integer.parseInt(departmentid));
		}
		if (!StringTool.isEmpty(professionid)) {
			employee.setProfessionid(Integer.parseInt(professionid));
		}
		if (!StringTool.isEmpty(authorityid)) {
			employee.setAuthorityid(Integer.parseInt(authorityid));
		}

		// 获取数据
		String result = service.getEmployeeList(employee, new Page(page, rows));
		// 返回数据
		// System.out.println("result1"+result);
		response.getWriter().write(result);

	}

}
