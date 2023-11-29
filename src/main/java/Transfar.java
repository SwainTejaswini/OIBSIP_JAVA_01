


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
 * Servlet implementation class Transfar
 */
@WebServlet("/Transfar")
public class Transfar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");			
			int account_no=Integer.parseInt(request.getParameter("ano"));
			String name=request.getParameter("name");
			String password=request.getParameter("psw");
			int targetaccountno=Integer.parseInt(request.getParameter("tno"));
			int tamount=Integer.parseInt(request.getParameter("amt"));
			
			PreparedStatement ps=con.prepareStatement("select * from bank where account_no=? and name=? and password=?");
			ps.setInt(1, account_no);
			ps.setString(2, name);
			ps.setString(3, password);
			ResultSet rs=ps.executeQuery();
			int oldbalance=0,balance=0;
			if(rs.next())
			{
				oldbalance=rs.getInt(4);
			}
			out.print("<h2>My account no is:"+account_no+"</h2>");
			out.print("<h2>My original amount:"+oldbalance+"</h2>");
			
			PreparedStatement ps1=con.prepareStatement("select * from bank where account_no=?");
			ps1.setInt(1, targetaccountno);
			
			ResultSet rs1=ps1.executeQuery();
			int oldbalance1=0,balance1=0;
			if(rs1.next())
			{
				oldbalance1=rs1.getInt(4);
			}
			out.print("<h2>Target account no is:"+targetaccountno+"</h2>");
			out.print("<h2>Traget Account's original amount:"+oldbalance1+"</h2>");
			
			out.print("<h2> Tarnsfer amount is:"+tamount+"</h2>");
			
			if(oldbalance>=tamount)
			balance=oldbalance-tamount;
			
			else
			{	
			out.print("<h1>Insufficient amount</h1>");
			balance=oldbalance;
			}
			
			PreparedStatement ps2=con.prepareStatement("update bank set amount=? where account_no=?");
			ps2.setDouble(1,balance);
			ps2.setInt(2,account_no);
			
			ps2.executeUpdate();
			out.println("<h2>After transfer my account balance: "+balance+"</h2>");
			
			if(oldbalance>=tamount)
			balance1=oldbalance1+tamount;
			
			else
			balance1=oldbalance1;	
			
			PreparedStatement ps3=con.prepareStatement("update bank set amount=? where account_no=?");
			ps3.setDouble(1,balance1);
			ps3.setInt(2,  targetaccountno);
			
			ps3.executeUpdate();
			out.println("<h2>After transfer target account balance: "+balance1+"</h2>");
			
			con.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
			
	}

}
