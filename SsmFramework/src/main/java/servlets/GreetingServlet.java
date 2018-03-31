package servlets;

/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GreetingServlet
 */
public class GreetingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see
	 */
	public String getServletInfo() {
		return "The Hello servlet says hello.";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setBufferSize(8192);

		PrintWriter out = response.getWriter();

		// then write the data of the response
		out.println("<html>" + "<head><title>Hello</title></head>");

		// then write the data of the response
		out.println("<body  bgcolor=\"#ffffff\">" + "<img src=\"duke.waving.gif\" alt=\"Duke waving\">"
				+ "<h2>Hello, my name is Duke. What's yours?</h2>" + "<form method=\"get\">"
				+ "<input type=\"text\" name=\"username\" size=\"25\">" + "<p></p>"
				+ "<input type=\"submit\" value=\"Submit\">"  + "</form>");

		String username = request.getParameter("username");

		if ((username != null) && (username.length() > 0)) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ResponseServlet");

			if (dispatcher != null) {
				dispatcher.include(request, response);
			}
		}

		out.println("</body></html>");
		out.close();	}

}
