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
 * Servlet implementation class DeleteAlarm
 */
@WebServlet("/DeleteAlarm")
public class DeleteAlarm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB AlarmsBean alarmBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAlarm() {
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

		String alarmid = request.getParameter("alarmID");
		System.out.print(alarmid);
		alarmBean.deleteAlarmSetting(Integer.parseInt(alarmid));
		response.sendRedirect("http://220.244.243.51:8080/WEMS_Servlets/jsp/alarms.jsp");
	}
}
