package com.aust.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Profession;
import com.aust.service.ProfessionService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class ProfessionServlet
 */
public class ProfessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ProfessionService service=new ProfessionService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if("toProfessionListView".equalsIgnoreCase(method)){ //转发到部门列表页
			request.getRequestDispatcher("/WEB-INF/view/Employee/professionList.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取请求的方法
		String method = request.getParameter("method");
		
		if("ProfessionList".equalsIgnoreCase(method)){ //获取所有部门
			professionList(request, response);
		} else if("AddProfession".equalsIgnoreCase(method)){ //添加部门
			addprofession(request, response);
		} else if("deleteProfession".equalsIgnoreCase(method)){ //删除部门
			deleteprofession(request, response);
		}else if("editProfession".equalsIgnoreCase(method)){ //修改部门信息
			editprofession(request, response);
		}
	}

	private void editprofession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		//获取参数名
		Enumeration<String> pNames = request.getParameterNames();
		Profession profession = new Profession();
		//Teacher teacher = new Teacher();
		while(pNames.hasMoreElements()){
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			try {
				BeanUtils.setProperty(profession, pName, value);						
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		try {
			//service.editDepartment(department);
			service.editProfession(profession);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
		
	}

	private void deleteprofession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		int professionid = Integer.parseInt(request.getParameter("professionid"));
		try {
			service.deleteProfession(professionid);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	private void addprofession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try {
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			Profession pro = new Profession();
			pro.setName(name);
			pro.setRemark(remark);
			
			int r = service.addProfession(pro);
			if(r==1){
				  response.getWriter().write("success");
				}else
				{
					response.getWriter().write("duplicate");
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	private void professionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		String professionid = request.getParameter("professionid");
		//String departmentid = request.getParameter("departmentid");
		//System.out.println("professionid"+professionid);
		String result = service.getProfessionList(professionid);
		//返回数据

        response.getWriter().write(result);
		
	}
	
}
