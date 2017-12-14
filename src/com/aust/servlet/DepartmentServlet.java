package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Department;
import com.aust.service.DepartmentService;
import com.aust.bean.Page;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class DepartmentServlet
 */
public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DepartmentService service = new DepartmentService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取请求的方法
				String method = request.getParameter("method");
				if("toDepartmentListView".equalsIgnoreCase(method)){ //转发到部门列表页
					request.getRequestDispatcher("/WEB-INF/view/Employee/departmentList.jsp").forward(request, response);
				} 
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取请求的方法
				String method = request.getParameter("method");
				//请求分发
				if("DepartmentList".equalsIgnoreCase(method)){ //获取所有学生数据
					departmentList(request, response);
				} else if("AddDepartment".equalsIgnoreCase(method)){ //添加学生
					addDepartment(request, response);
				} else if("DeleteDepartment".equalsIgnoreCase(method)){ //删除学生
					deleteDepartment(request, response);
				} else if("EditDepartment".equalsIgnoreCase(method)){ //修改学生信息
					editDepartment(request, response);
				} else if("DepartmenttoEmployeeList".equalsIgnoreCase(method)){ //获取当前学生班级的所有学生
					departmenttoEmployeeList(request, response);
				}
	}

	private void departmenttoEmployeeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		//获取数据
		String result = service.getAllDepartmentList();
		//返回数据
		//System.out.println("2017年11月28日 07:38:47："+result);
        response.getWriter().write(result);
	}

	private void editDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取参数名
				Enumeration<String> pNames = request.getParameterNames();
				Department department = new Department();
				while(pNames.hasMoreElements()){
					String pName = pNames.nextElement();
					String value = request.getParameter(pName);
					//System.out.println(pName+":"+value);
					try {
						BeanUtils.setProperty(department, pName, value);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				service.editDepartment(department);
				response.getWriter().write("success");
	}

	private void deleteDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取要删除的部门
				//String[] numbers = request.getParameterValues("numbers[]");
				String[] ids = request.getParameterValues("ids[]");
				try {
					service.deleteDepartment(ids);
					response.getWriter().write("success");
				} catch (Exception e) {
					response.getWriter().write("fail");
					e.printStackTrace();
				}
	}

	private void addDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取参数名
		try {
			Enumeration<String> pNames = request.getParameterNames();
			Department department = new Department();
			while(pNames.hasMoreElements()){
				String pName = pNames.nextElement();
				String value = request.getParameter(pName);
				try {
					BeanUtils.setProperty(department, pName, value);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			int r= service.addDepartment(department);
			if(r==1){
				  response.getWriter().write("success");
				}else
				{
					response.getWriter().write("duplicate");
				}
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write("fail");
			e.printStackTrace();
		}
				
	}

	private void departmentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//查询用的，暂时不写
				String leadid = request.getParameter("leadid");
				//String departmentid = request.getParameter("departmentid");
				//String leadid = request.getParameter("leadid");
				//班级ID
				//String clazzid = request.getParameter("clazzid");
				//获取分页参数
				int page = Integer.parseInt(request.getParameter("page"));
				int rows = Integer.parseInt(request.getParameter("rows"));
				
				//封装参数
				Department department = new Department();
				
				if(!StringTool.isEmpty(leadid)){
					department.setLeadid(Integer.parseInt(leadid));
				}
//				if(!StringTool.isEmpty(departmentid)){
//					department.setClazzid(Integer.parseInt(clazzid));
//				}
				
				//获取数据
				String result = service.getDepartmentList(department, new Page(page, rows));
				//返回数据
				//System.out.println("2017-11-28："+result);
		        response.getWriter().write(result);
	}

}
