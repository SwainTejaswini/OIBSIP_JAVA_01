

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
		PrintWriter out=response.getWriter();
	    String user_name=request.getParameter("name");
	    String password=request.getParameter("psw");  
	  try  
	  {
	   Class.forName("oracle.jdbc.driver.OracleDriver");  
	   Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");
	   PreparedStatement ps=con.prepareStatement("select *from bank where name=? and password=?");
	   ps.setString(1,user_name);   
	   ps.setString(2,password);
	   ResultSet rs=ps.executeQuery();   
	   if(rs.next())
	   {    
		   response.sendRedirect("MyHome.html");
	   }   
	   else
	   {    
		   out.print("<h1 style=\"color: red\">Please enter valied user name and password</h1>");
	   }   
	   con.close();   
	   }
	  catch(Exception ex)  {
	   out.print(ex);  
	   }
	}

}
