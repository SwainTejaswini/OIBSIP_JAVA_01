

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Newaccount
 */
@WebServlet("/Newaccount")
public class Newaccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Newaccount() {
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
		 long account_no=Long.parseLong(request.getParameter("ano"));
		  String name=request.getParameter("name");  
		  String password=request.getParameter("psw"); 
		  double amount=Double.parseDouble(request.getParameter("amt"));
		  String address=request.getParameter("address");
		  long mobileno=Long.parseLong(request.getParameter("mno"));  
		  String status=request.getParameter("status");
		  
		  try  
		  {
		   Class.forName("oracle.jdbc.driver.OracleDriver");  
		   Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");
		   PreparedStatement ps=con.prepareStatement("Insert into Bank values(?,?,?,?,?,?,?)");
		   ps.setLong(1,account_no);   
		   ps.setString(2, name);
		   ps.setString(3,password);
		   ps.setDouble(4, amount);
		   ps.setString(5, address);
		   ps.setLong(6, mobileno);
		   ps.setString(7, status);
		   
		   int i=ps.executeUpdate();
		   out.print(i+"New user register successfully");		   
		   //response.sendRedirect("home.html");
		   con.close();   
		   }
		  catch(Exception ex)  {
		   out.print(ex);  
		   }

	}

}
