

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBConnection.DBConnection;

public class LoginServlet extends HttpServlet {
	PrintWriter pw = null;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		//HttpSession session = request.getSession("seatnumber");
		 pw = response.getWriter();
		String seatno = request.getParameter("seatnumber");
		String firstname = request.getParameter("fname");
		HttpSession session = request.getSession();
		 session.setAttribute("seatnumber",seatno);
		if (isSeatExist(seatno)) {
			if (isValidStudent(seatno, firstname)) {
				pw.println("<h1> </h1>");
				pw.write("<br><h1>Display or Load or Navigate to Home Page of Application.</h1>");
				pw.write("<br><h1><a href=\"/ExamResultDemo/viewResult\" color-style=red>   VIEW RESULT</a></h1>");
			} else {
				pw.println("<h1> Login Failed </h1>");
				pw.write("<br><a href=\"index.html\"> Back</a>");
			}

		} else {
			pw.println("<h1> Login Failed, User doesn't Exist Please do Registration. </h1>");
			pw.write("<br><a href=\"index.html\"> Back</a>");
		}

	}

	//this method is use to check wheither username exists in DB or not 
	private boolean isSeatExist(String demoseatnumber) 	{
		Connection con = DBConnection.getDBConnection();
		Statement stmt = null;
		ResultSet rs = null;
		boolean isExist = false;

		try {
			if(null != con) {
				stmt = con.createStatement();
				String sql = "SELECT * FROM student_details where seat_no = '" + demoseatnumber   + "'";
				rs = stmt.executeQuery(sql);
	
				if (rs.next()) {
					isExist = true;
				} else {
					isExist = false;
				}
			}else{
				pw.println("<h1> Error while Connecting DB </h1>");
				pw.write("<br><a href=\"index.html\"> Back</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1> Error while checking Seat Number </h1>");
			pw.write("<br><a href=\"index.html\"> Back</a>");
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
	
	// This method is use to check username and password in DB 
	private boolean isValidStudent(String seat_number, String firstname) {
		Connection con = DBConnection.getDBConnection();
		Statement stmt = null;
		ResultSet rs = null;
		boolean isValidStudent = false;
		String demoseat_number = "";
		String f_name = "";

		try {
			if(null != con) {
				stmt = con.createStatement();
				String sql = "SELECT  seat_no, fname FROM student_details where seat_no = '" + seat_number + "'";
				rs = stmt.executeQuery(sql);
	
				if (rs.next()) {
					demoseat_number = rs.getString("seat_no");//
					f_name = rs.getString("fname");
					if (seat_number.equals(demoseat_number) && firstname.equals(f_name)) {
						isValidStudent = true;
					} else {
						isValidStudent = false;
					}
				} else {
					isValidStudent = false;
				}
			}else {
				pw.println("<h1> Error while Connecting DB </h1>");
				pw.write("<br><a href=\"index.html\"> Back</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1> Error while Authenticating User </h1>");
			pw.write("<br><a href=\"index.html\"> Back</a>");
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
				pw.println("<h1> Here </h1>");
			}
		}
		return isValidStudent;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}
}
