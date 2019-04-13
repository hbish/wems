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
 * Servlet implementation class AddNewUserGroup
 */
@WebServlet("/AddNewUserGroup")
public class AddNewUserGroup extends HttpServlet {
  private static final long serialVersionUID = 1L;
  @EJB
  com.wems.service.ConfigurationBean configBean = new com.wems.service.ConfigurationBean();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AddNewUserGroup() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    processRequest(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   * 
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    processRequest(request, response);
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    String name = request.getParameter("ugName");
    String description = request.getParameter("ugDescription");
    String email = request.getParameter("ugEmail");

    configBean.addUserGroup(name, description, email);
    response.sendRedirect(response.encodeRedirectURL("http://220.244.243.51:8080/WEMS_Servlets/jsp/new_user_group.jsp?success=1"));
  }
  
}
