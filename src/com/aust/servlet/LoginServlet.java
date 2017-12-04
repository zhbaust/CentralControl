package com.aust.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aust.bean.Employee;
import com.aust.service.SystemService;
import com.aust.tools.VCodeGenerator;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SystemService service = new SystemService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			String method = request.getParameter("method");
		
		if("GetVCode".equalsIgnoreCase(method)){
			getVCode(request, response);
		}
	}

	private void getVCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//创建验证码生成器对象
				VCodeGenerator vcGenerator = new VCodeGenerator();
				//生成验证码
				String vcode = vcGenerator.generatorVCode();
				//将验证码保存在session域中,以便判断验证码是否正确
				request.getSession().setAttribute("vcode", vcode);
				//生成验证码图片
				BufferedImage vImg = vcGenerator.generatorRotateVCodeImage(vcode, true);
				//输出图像
				ImageIO.write(vImg, "gif", response.getOutputStream());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取请求的方法
				String method = request.getParameter("method");
				
				if("Login".equals(method)){ //验证登录
					login(request, response);
				}

}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取用户输入的账户
				String account = request.getParameter("account");
				//获取用户输入的密码
				String password = request.getParameter("password");
				//获取用户输入的验证码
				String vcode = request.getParameter("vcode");
				//获取登录类型
				int type = Integer.parseInt(request.getParameter("type"));
				
				//返回信息
				String msg = "";
				
				//获取session中的验证码
				String sVcode = (String) request.getSession().getAttribute("vcode");
				if(!sVcode.equalsIgnoreCase(vcode)){//先判断验证码是否正确
					msg = "vcodeError";
				} else{	//判断用户名和密码是否正确
					//将账户和密码封装
					Employee user= new Employee();
					user.setLoginname(account);
					user.setPassword(password);
					user.setAuthorityid(Integer.parseInt(request.getParameter("type")));
					//创建系统数据层对象
					
					//查询用户是否存在
					Employee loginUser = service.getAdmin(user);
					if(loginUser == null){//如果用户名或密码错误
						msg = "loginError";
					} else{ //正确
//						if(Employee.USER_ADMIN == type){
							msg = "admin";
//						} else if(Employee.USER_STUDENT == type){
//							msg = "student";
//						} else if(Employee.USER_TEACHER == type){
//							msg = "teacher";
//						}
						//将该用户名保存到session中
						request.getSession().setAttribute("user", loginUser);
					}
				}
				//返回登录信息
				response.getWriter().write(msg);
	}
}
