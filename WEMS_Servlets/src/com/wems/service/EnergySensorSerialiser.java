package com.wems.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EnergySensorSerialiser
 */
@WebServlet("/EnergySensorSerialiser")
public class EnergySensorSerialiser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB com.wems.service.IOModuleBean ioModuleBean = new com.wems.service.IOModuleBean();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnergySensorSerialiser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return "Serialise Energy Sensor Data"; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Map<String, String[]> sensorData = request.getParameterMap();
		
		try {
			out.println("<html>");
            out.println("<head>");
            out.println("<title>WEMS Energy Sensor Serialiser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>"+ request.getParameter("name") +"</p>");
            /*
            if (ioModuleBean.storeSensorData(sensorData)) {
            	out.println("<P>Energy data stored</P>");
            } else {
            	out.println("<P>Error while trying to store energy data</P>");
            }
            */
            out.println(ioModuleBean.getEstablishmentIdList());
            out.println("</body>");
            out.println("</html>");
		} finally {
			out.close();
		}
	}

}
