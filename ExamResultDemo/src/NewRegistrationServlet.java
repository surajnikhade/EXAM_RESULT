
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBConnection.DBConnection;

public class NewRegistrationServlet extends HttpServlet {
	PrintWriter pw = null;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("inside RegistrationServlet ");
		response.setContentType("text/html");
		pw = response.getWriter();
		if(!checkUserExists(request.getParameter("seatnumber"))) {
			Connection conn = DBConnection.getDBConnection();
			PreparedStatement pstmt = null;
			String seat_no 	= request.getParameter("seatnumber");
			String fname 	= request.getParameter("fname");
			String lname    = request.getParameter("lname");
			String clgname  = request.getParameter("clgname");
			String egmark   = request.getParameter("englishmark");
			String mtmark   = request.getParameter("mathematicsmark");
			String scmark 	= request.getParameter("sciencemark");
			String geomark = request.getParameter("geographymark");
	
			try {
	
				pstmt = conn.prepareStatement("insert into student_details values(?,?,?,?,?,?,?,?)");
				pstmt.setString(1, seat_no);
				pstmt.setString(2, fname);
				pstmt.setString(3, lname);
				pstmt.setString(4, clgname);
				pstmt.setString(5, egmark);
				pstmt.setString(6, mtmark);
				pstmt.setString(7, scmark);
				pstmt.setString(8, geomark);
				int i = pstmt.executeUpdate();
				if(i>0) {
					pw.write("Registration Successfully");
					pw.write("<br><a href=\"index.html\"> Home</a>");
				}else {
					pw.write("Registration Failed");
					pw.write("<br><a href=\"newregistration.html\"> Back</a>");
				}
				//System.out.println(i + " records inserted");
	
			} catch (Exception se) {
				se.printStackTrace();
			} finally {
				try {
					pstmt.close();
					//rs.close();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			pw.write("User Already Present or Exist.");
			pw.write("<br><a href=\"newregistration.html\"> Back</a>");
		}
	}
	private boolean checkUserExists(String seat_no) 	{
		Connection con = DBConnection.getDBConnection();
		Statement stmt = null;
		ResultSet rs = null;
		boolean isExist = false;

		try {
			if(null != con) {
				stmt = con.createStatement();
				String sql = "SELECT * FROM student_details where seat_no = '" + seat_no + "'";
				rs = stmt.executeQuery(sql);
	
				if (rs.next()) {
					isExist = true;
				} else {
					isExist = false;
				}
			}else{
				pw.println("<h1> Error while Connecting DB </h1>");
				pw.write("<br><a href=\"register.html\"> Back</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1> Error while checking User </h1>");
			pw.write("<br><a href=\"register.html\"> Back</a>");
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isExist;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
}
