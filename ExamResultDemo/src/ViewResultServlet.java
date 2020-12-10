import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBConnection.DBConnection;

public class ViewResultServlet extends HttpServlet {
	PrintWriter pw = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("INSIDE VIEWRESULT");
		pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String mySeatNo = (String) session.getAttribute("seatnumber");
		System.out.println(mySeatNo);

		Connection con = DBConnection.getDBConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (null != con) {
				stmt = con.createStatement();
				String sql = "SELECT * FROM student_details where seat_no = '" + mySeatNo + "'";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					String seatNumber = rs.getString("seat_no");
					String firstName = rs.getString("fname");
					String lastName = rs.getString("lname");
					String engilshMark = rs.getString("english");
					String mathMark = rs.getString("mathematics");
					String scienceMark = rs.getString("science");
					String geoMark = rs.getString("geography");

					pw.write("<html><body>");
					pw.write("<h1 text-align=center > RESULT </h1>");
					pw.write("<table border=\"2\" width=\"50%\">");
					pw.write("<tr width=\"40%\"><td width=\"40%\">" + "Seat Number : " + "</td><td>" + seatNumber
							+ "</td></tr>");
					pw.write("<tr width=\"40%\"><td>" + "First Name : " + "</td><td>" + firstName + "</td></tr>");
					pw.write("<tr width=\"40%\"><td>" + "Last Name : " + "</td><td>" + lastName + "</td></tr>");
					pw.write("<tr width=\"40%\"><td>" + "English : " + "</td><td>" + engilshMark + "</td></tr>");
					pw.write("<tr width=\"40%\"><td>" + "Math : " + "</td><td>" + mathMark + "</td></tr>");
					pw.write("<tr width=\"40%\"><td>" + "Science : " + "</td><td>" + scienceMark + "</td></tr>");
					pw.write("<tr width=\"40%\"><td>" + "Geography : " + "</td><td>" + geoMark + "</td></tr>");
					pw.write("</table>");
					pw.write("</body></html>");

				}
			} else {
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

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

}
