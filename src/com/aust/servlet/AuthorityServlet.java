package com.aust.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aust.bean.Authority;
import com.aust.bean.Profession;
import com.aust.service.AuthorityService;

/**
 * Servlet implementation class AuthorityServlet
 */
public class AuthorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AuthorityService service=new AuthorityService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if("toAuthorityListView".equalsIgnoreCase(method)){ //转发到部门列表页
			request.getRequestDispatcher("/WEB-INF/view/Employee/authorityList.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取请求的方法
				String method = request.getParameter("method");
				
				if("AuthorityList".equalsIgnoreCase(method)){ //获取所有部门
					authorityList(request, response);
				} else if("AddAuthority".equalsIgnoreCase(method)){ //添加部门
					addauthority(request, response);
				} else if("deleteAuthority".equalsIgnoreCase(method)){ //删除部门
					deleteauthority(request, response);
				}else if("editAuthority".equalsIgnoreCase(method)){ //修改部门信息
					editauthority(request, response);
				}
	}

	private void editauthority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取参数名
				Enumeration<String> pNames = request.getParameterNames();
				Authority authority = new Authority();
				//Teacher teacher = new Teacher();
				while(pNames.hasMoreElements()){
					String pName = pNames.nextElement();
					String value = request.getParameter(pName);
					try {
						BeanUtils.setProperty(authority, pName, value);						
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				try {
					//service.editDepartment(department);
					service.editProfession(authority);
					response.getWriter().write("success");
				} catch (Exception e) {
					response.getWriter().write("fail");
					e.printStackTrace();
				}
	}

	private void deleteauthority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int authorityid = Integer.parseInt(request.getParameter("authorityid"));
		try {
			service.deleteAuthority(authorityid);
			response.getWriter().write("success");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		}
	}

	private void addauthority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try {
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			Authority auth = new Authority();
			auth.setName(name);
			auth.setRemark(remark);
			
			int  r= service.addAuthority(auth);
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

	private void authorityList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		String authorityid = request.getParameter("authorityid");
		
		String result = service.getAuthorityList(authorityid);
		//返回数据
		//System.out.println("authority:"+result);
        response.getWriter().write(result);
	}

}
