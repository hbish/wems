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
 * Servlet implementation class AddNewAlarm
 */
@WebServlet("/ModifyAlarm")
public class ModifyAlarm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB com.wems.service.AlarmsBean alarmsBean = new com.wems.service.AlarmsBean();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyAlarm() {
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
		String alertid = request.getParameter("alertid");
		String building = request.getParameter("building");
		String level = request.getParameter("level");
		String roomid = request.getParameter("room");
		String deviceuid = request.getParameter("deviceuid");
		System.out.print("DEBUG: "+deviceuid);
		String parameter = request.getParameter("parameter");
		String lessThan = request.getParameter("less_than");
		String greaterThan = request.getParameter("greater_than");
		String ug = request.getParameter("notification");
		String priority = request.getParameter("priority");
		alarmsBean.modifyAlarmSetting(alertid, roomid, deviceuid, parameter, lessThan, greaterThan, ug, priority);
		response.sendRedirect("jsp/edit_alarm.jsp?id="+alertid);
	}

}
