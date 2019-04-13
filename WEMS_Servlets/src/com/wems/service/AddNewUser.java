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
 * Servlet implementation class AddNewUser
 */
@WebServlet("/AddNewUser")
public class AddNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB com.wems.service.ConfigurationBean configBean = new com.wems.service.ConfigurationBean();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewUser() {
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
		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String userName = request.getParameter("username");
		String password = request.getParameter("pass");
		String contact = request.getParameter("contact-number");
		String email = request.getParameter("email");
		String userGroup = request.getParameter("business-unit");
		String userType = request.getParameter("user-access");
		configBean.addUser(firstName, lastName, userName, password, contact, email, userGroup, userType);		
		response.sendRedirect(response.encodeRedirectURL("http://220.244.243.51:8080/WEMS_Servlets/jsp/new_user.jsp?success=1"));
	}
}
