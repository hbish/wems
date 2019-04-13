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
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUserGroup")
public class EditUserGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB com.wems.service.ConfigurationBean configBean = new com.wems.service.ConfigurationBean();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserGroup() {
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
		String ugOLD = request.getParameter("old");
		String ugname = request.getParameter("ugname");
		String email = request.getParameter("email");
		String desc = request.getParameter("desc");

		//configBean.addUser(firstName, lastName, userName, password, contact, email, rawr, 1);		
		configBean.updateUserGroup(ugOLD, ugname, email, desc);
		response.sendRedirect(response.encodeRedirectURL("http://220.244.243.51:8080/WEMS_Servlets/jsp/user_groups.jsp"));
	}
}
