package com.wems.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class XMLServlet
 */
@WebServlet("/XMLServlet")
public class XMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB com.wems.service.IOModuleBean ioModuleBean = new com.wems.service.IOModuleBean();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XMLServlet() {
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
		
		String test = request.getQueryString();
		System.out.println("Get RAW " + request.getQueryString());
        
		String xml = null;
		xml = test.replaceAll("%3C", "<");
		xml = xml.replaceAll("%3E", ">");
		xml = xml.replaceAll("%2F", "/");
		xml = xml.replaceAll("%20", " ");
		xml = xml.replaceAll("%2E", ".");
		xml = xml.replaceAll("%2D", "-");
		xml = xml.replaceAll("%31", "1");
		xml = xml.replaceAll("%32", "2");
		xml = xml.replaceAll("%33", "3");
		xml = xml.replaceAll("%34", "4");
		xml = xml.replaceAll("%35", "5");
		xml = xml.replaceAll("%36", "6");
		xml = xml.replaceAll("%37", "7");
		xml = xml.replaceAll("%38", "8");
		xml = xml.replaceAll("%39", "9");
		xml = xml.replaceAll("%30", "0");
		

		//String xml = URLDecoder.decode(test);
		
		System.out.println("Get XML: " + xml);
		String test1 = ioModuleBean.processSensorXML(xml);
	}
	
}
