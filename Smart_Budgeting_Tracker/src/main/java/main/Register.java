package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Register() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
		try
		{
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String pass = request.getParameter("password1");
			String newpass = request.getParameter("password2");
			if(!name.isEmpty()||!email.isEmpty()||!pass.isEmpty()||!newpass.isEmpty())
			{
				if(emailverification(email)==true)
				{
						if(passwordVerification(pass)==true)
						{
							if(newpass.equals(pass))
							{
								try
								{
									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Personal_Expenses_DB","root","Welcome@123");
									PreparedStatement pst = con.prepareStatement("insert into Register(name,email,password) values(?,?,?)");
									pst.setString(1, name);
									pst.setString(2, email);
									pst.setString(3, pass);
									pst.executeUpdate();
									PrintWriter out = response.getWriter();
									out.println("Registered SucessFully Please Login");
									RequestDispatcher rd = request.getRequestDispatcher("Login.html");
									rd.forward(request, response);
								}
								catch(Exception obj)
								{
									obj.printStackTrace();
								}
							}
							else
							{
								String m = "Password Doesn't Match";
								PrintWriter out = response.getWriter();
								out.println(m);
								response.setContentType("text/html");
								out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
							}
						}
						else
						{
							PrintWriter out = response.getWriter();
							out.println("Password Should Contain  9 characters and  must at least one capital,  one small, one number and one special character");
							response.setContentType("text/html");
							out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
						}
				}
				else
				{
					String m = "Please Enter Valid E-mail Adress";
					PrintWriter out = response.getWriter();
					out.println(m);
					response.setContentType("text/html");
					out.println("<html> <body> <a href='register.html'>Go Back</a> </body> </html>");
				}
			}
			else
			{
				PrintWriter out = response.getWriter();
				out.println("Fields Must Not Be Empty");
				RequestDispatcher rd = request.getRequestDispatcher("register.html");
				rd.forward(request, response);
			}
		}
		catch(Exception obj)
		{
			obj.printStackTrace();
		}
	}
	private boolean emailverification(String s) 
	{
		int y = s.indexOf("@");
		int u= s.indexOf("@",y+1);
		int p= s.lastIndexOf("@",y-1);
		String errorM = "";
		if(p==-1&&u==-1&&y!=-1&&s.length()>=15&&s.length()<=50)
		{
		    if(!Character.isUpperCase(s.charAt(0)))
    		  {
    		      return false;
    		  }
    	    if(!Character.isLetter(s.charAt(y-1))&&!Character.isDigit(s.charAt(y-1)))
    		  {
    		      return false; 
    		  }
    		if(errorM.isEmpty()) 
    		{
        		for(int i=1;i<y-1;i++)
        		{
        		    if(!Character.isLetter(s.charAt(i))&&!Character.isDigit(s.charAt(i)))
        		    {
        		        if(s.charAt(i)!='.'&&s.charAt(i)!='_')
        		        {
        		            return false;
        		        }
        		    }
        		}
        		for(int i=y+1;i<s.length();i++)
        		{
        		    if(Character.isDigit(s.charAt(i)))
        		    {
        		        return false;
        		    }
        		    else if(!Character.isLetter(s.charAt(i))&&(s.charAt(i)!='.'))
        		    {
        		        return false;
        		    }
        		}
    		}
    		if(errorM.isEmpty())
    		{
    		    return true;
    		}
    		else
    			return false;
		}
		else
			return false;
	}
	private boolean passwordVerification(String s)
	{
		int n=s.length();
	    int cc=0,sc=0,dc=0,spc=0,sp=0;
	    if(n>=8)
	    {
	        for(int i=0;i<n;i++)
	        {
	            char ch=s.charAt(i);
	            if(Character.isUpperCase(ch))
	            {
	                cc++;
	            }
	            else if(Character.isLowerCase(ch))
	            {
	                sc++;
	            }
	            else if(Character.isDigit(ch))
	            {
	                dc++;
	            }
	            else if(ch==' ')
	            {
	                sp++;
	            
	            }
	            else
	            {
	                spc++;
	            }
	        }
	        if(cc>=1&&sc>=1&&dc>=1&&sp==0&&spc>=1)
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
	    else
	    	return false;
	}
}
