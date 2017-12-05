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
import com.aust.bean.Employee;
import com.aust.bean.Jobtransfer;
import com.aust.bean.Page;
import com.aust.service.JobtransferService;
import com.aust.tools.StringTool;

/**
 * Servlet implementation class JobtransferServlet
 */
public class JobtransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private JobtransferService service = new JobtransferService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobtransferServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if("toJobtransferListView".equalsIgnoreCase(method)){ //转发到部门列表页
			request.getRequestDispatcher("/WEB-INF/view/Employee/jobTransferList.jsp").forward(request, response);
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
		if("JobtransferList".equalsIgnoreCase(method)){ //获取所有学生数据
			jobtransferList(request, response);
		} else if("AddJobtransfer".equalsIgnoreCase(method)){ //添加学生
			addJobtransfer(request, response);
		} else if("DeleteJobtransfer".equalsIgnoreCase(method)){ //删除学生
			deleteJobtransfer(request, response);
		} else if("EditJobtransfer".equalsIgnoreCase(method)){ //修改学生信息
			editJobtransfer(request, response);
		} else if("JobtransferListQuery".equalsIgnoreCase(method)){ //获取当前学生班级的所有学生
			jobtransferListQuery(request, response);
		}
	}

	private void jobtransferListQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		
		//System.out.println("reson1:"+name);
		String reson = request.getParameter("reson");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		
		String olddepartmentid = request.getParameter("olddepartmentid");
		String departmentid = request.getParameter("departmentid");
		
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		//System.out.println("reson:"+reson);
		Jobtransfer jobtransfer =new Jobtransfer();
//		if(name!=null)
//			jobtransfer.setEmployeeid(employeeid);
		if(reson!=null)
			jobtransfer.setTransferreson(reson);
		if(departmentid.length()>0)
			jobtransfer.setDepartmentid(Integer.parseInt(departmentid));
		if(olddepartmentid.length()>0)
			jobtransfer.setOlddepartmentid(Integer.parseInt(olddepartmentid));
	
		String result = service.getJobtransferList(jobtransfer,name,startdate,enddate, new Page(page, rows));
		//System.out.println("result"+result);	
		//返回数据
        response.getWriter().write(result);
	}

	private void editJobtransfer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取参数名
		Enumeration<String> pNames = request.getParameterNames();
		Jobtransfer jobtransfer = new Jobtransfer();
		while(pNames.hasMoreElements()){
			String pName = pNames.nextElement();
			String value = request.getParameter(pName);
			System.out.println(pName+":"+value);
			try {
				BeanUtils.setProperty(jobtransfer, pName, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		service.editJobtransfer(jobtransfer);
		response.getWriter().write("success");

	}

	private void deleteJobtransfer(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void addJobtransfer(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void jobtransferList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//查询用的，暂时不写
		String employeeid = request.getParameter("employeeid");
		String olddepartmentid =request.getParameter("olddepartmentid");
		String departmentid = request.getParameter("departmentid");
		String operatorid = request.getParameter("operatorid");
		//班级ID
		//String clazzid = request.getParameter("clazzid");
		//获取分页参数
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		//封装参数
		Jobtransfer jobtransfer = new Jobtransfer();
		
		if(!StringTool.isEmpty(employeeid)){
			jobtransfer.setEmployeeid(Integer.parseInt(employeeid));
		}
		if(!StringTool.isEmpty(olddepartmentid)){
			jobtransfer.setOlddepartmentid(Integer.parseInt(olddepartmentid));
		}
		if(!StringTool.isEmpty(departmentid)){
			jobtransfer.setDepartmentid(Integer.parseInt(departmentid));
		}
		if(!StringTool.isEmpty(operatorid)){
			jobtransfer.setEmployeeid(Integer.parseInt(operatorid));
		}
//		if(!StringTool.isEmpty(departmentid)){
//			department.setClazzid(Integer.parseInt(clazzid));
//		}
		
		//获取数据
		String result = service.getJobtransferList(jobtransfer, new Page(page, rows));
		//返回数据
		//System.out.println("2017-11-28："+result);
        response.getWriter().write(result);

	}

}
