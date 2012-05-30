package com.digdeep.infog.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="newsupdate", urlPatterns={"/newsupdate"})
public class NewsUpdateServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.print(request.getHeader("Accept"));

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



}
