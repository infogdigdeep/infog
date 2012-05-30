package com.digdeep.infog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "newsupdate", urlPatterns = { "/newsupdate" })
public class NewsUpdateServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			final PrintWriter writer = response.getWriter();
			String content = request.getHeader("Accept");
		    final String idString = request.getHeader("Last-Event-Id");
			if (content.equals("text/event-stream")) {
				response.setHeader("Content-Type", "text/event-stream");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Connection", "keep-alive");
				response.setCharacterEncoding("UTF-8");
				long id = (new Date()).getTime();
				writer.write("id: " + id + "\n");
				writer.write("data: " + id + "\n\n");
			}
			Timer timer = new Timer(false);
			TimerTask tt = new TimerTask() {

				@Override
				public void run() {
					long id = (new Date()).getTime();
					writer.write("id: " + id + "\n");
					writer.write("data: " + id + "\n\n");

				}
			};
			timer.schedule(tt, 5000, 5000);
		} finally {
			//response.getWriter().close();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
