

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Balance
 */
@WebServlet("/Balance")
public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Balance() {
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
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ghdb","ghdb");
			int account_no=Integer.parseInt(request.getParameter("ano"));
			String name=request.getParameter("name");
			String password=request.getParameter("psw");
			String status=request.getParameter("status");
			//out.print(status);
			PreparedStatement ps1=con.prepareStatement("update bank set status=? where account_no=?");
			ps1.setString(1,status);
			ps1.setInt(2, account_no);
			ps1.executeUpdate();
			
			PreparedStatement ps=con.prepareStatement("select * from bank where account_no=? and password=?");
			ps.setInt(1,account_no);
			ps.setString(2, password);
			
	        ResultSet rs=ps.executeQuery();
	        ResultSetMetaData rsmd=ps.getMetaData();
	        out.print("<table border='1'>");
	        int n=rsmd.getColumnCount();
	        for(int i=1;i<=n;i++)
	        {
	        	out.println("<td><font color=Blue size 3>"+"<br>"+rsmd.getColumnName(i));
	        }
	        	out.println("<tr>");
	        while(rs.next())
	        {
	        	for(int i=1;i<=n;i++)
	        	{
	        		out.println("<td><br>"+rs.getString(i));
	        	}
	        	out.println("<tr>");
	        }
	        out.print("</table>");
	        out.print("<h1>register successfully"+name+"</h1>");
	        con.close();
		}
	        catch (Exception e) {
				// TODO: handle exception
	        	System.out.println(e);
			}	 
	}

}
