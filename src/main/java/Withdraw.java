

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
 * Servlet implementation class Withdraw
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
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
			int amount=Integer.parseInt(request.getParameter("amt"));
			
			PreparedStatement ps=con.prepareStatement("select *from bank where account_no=? and name=? and password=?");
			ps.setInt(1, account_no);
			ps.setString(2, name);
			ps.setString(3, password);
			ResultSet rs=ps.executeQuery();
			double oldbalance=0.0,balance=0.0;
			if(rs.next())
			{
				oldbalance=rs.getDouble(4);
			}
			out.println("<h1>My account no is:"+account_no+"</h2>");
			out.println("<h2>My original amount:"+oldbalance+"</h2>");
			double withdraw=amount;
			if(withdraw<=oldbalance)
				balance=oldbalance-withdraw;
			else
			{
				balance=oldbalance;
				out.println("<h1 style=\"color: red;\">Insufficient amount...Please input valid amount...</h1>");
			}
			
			PreparedStatement ps2=con.prepareStatement("update bank set amount=? where account_no=?");
			ps2.setDouble(1,balance);
			ps2.setInt(2, account_no);
			
			ps2.executeUpdate();
			out.println("<h2>My Withdraw amount: "+withdraw+"</h2>");
			out.println("<h2>After withdrawal amount: "+balance+"</h2>");
			
			con.close();
			
			}
			
			catch(Exception e)
			{
				System.out.println(e);
			}
	}

}
