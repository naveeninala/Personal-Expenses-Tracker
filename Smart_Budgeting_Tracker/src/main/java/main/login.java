package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendRedirect("DB.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		boolean bo=false;
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/Personal_Expenses_DB","root","Welcome@123");
			PreparedStatement ps=con1.prepareStatement("select * from Register");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) 
			{
				String validemail=rs.getString(3);
				String validpassword=rs.getString(4);
				if(email.equals(validemail) && pass.equals(validpassword))
				{
					bo=true;
					break;
				}
			}
			if(bo==true) 
			{
				HttpSession user = request.getSession(true);
				user.setAttribute("email", email);
				user.setAttribute("pass", pass);
				RequestDispatcher rd = request.getRequestDispatcher("DB.html");
				rd.include(request, response);
			}
			else
			{
				String m = "Please Enter Valid Login Credentials";
				PrintWriter out = response.getWriter();
				out.println(m);
				response.setContentType("text/html");
				out.println("<html> <body> <a href='Login.html'>Go Back</a> </body> </html>");
			}
		}
		catch(Exception obj)
		{
			obj.printStackTrace();
		}
	}

}
