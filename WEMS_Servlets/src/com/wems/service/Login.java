package com.wems.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB com.wems.service.LoginBean loginBean = new com.wems.service.LoginBean();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String userName = request.getParameter("user-name");
		String password = request.getParameter("password");
		String sIPAddress = request.getRemoteAddr();
		//wrong username:
		if(!loginBean.isUser(userName)){
			response.sendRedirect("http://220.244.243.51:8080/WEMS_Servlets/jsp/index.jsp?login=1");
			System.out.println("wrong username");
		}
		//right username but wrong password:
		else if(!loginBean.checkPassword(userName, password)){
			response.sendRedirect("http://220.244.243.51:8080/WEMS_Servlets/jsp/index.jsp?login=2");
			System.out.println("wrong password");
		}
		//right username and right password:
		else{
			loginBean.createSession(userName, sIPAddress);
		response.sendRedirect("http://220.244.243.51:8080/WEMS_Servlets/jsp/home.jsp");
		System.out.println("right username and password");
		}
		//test:
		System.out.println("username: " + userName + " password: " + password); 
	}
}
