import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/statement")
public class StatementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        String month = request.getParameter("month");
        String year = request.getParameter("year");

        response.setContentType("text/html");

        response.getWriter().println("<html><head><title>Bank Statement</title>");

        response.getWriter().println("<style>");
        response.getWriter().println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif}");
        response.getWriter().println("body{background:#020617;color:white;padding:35px}");
        response.getWriter().println(".box{max-width:1100px;margin:auto;background:rgba(255,255,255,0.09);padding:30px;border-radius:22px;border:1px solid rgba(255,255,255,0.15)}");
        response.getWriter().println("h2{color:#38bdf8;margin-bottom:10px}");
        response.getWriter().println(".filter{display:flex;gap:12px;margin:25px 0}");
        response.getWriter().println("select,button{padding:12px;border-radius:10px;border:none;font-size:15px}");
        response.getWriter().println("button{background:#2563eb;color:white;font-weight:bold;cursor:pointer}");
        response.getWriter().println("table{width:100%;border-collapse:collapse;margin-top:20px}");
        response.getWriter().println("th{background:#2563eb;padding:14px;text-align:left}");
        response.getWriter().println("td{padding:13px;border-bottom:1px solid rgba(255,255,255,0.12)}");
        response.getWriter().println(".actions{margin-top:22px;display:flex;gap:12px}");
        response.getWriter().println("a{color:white;background:#334155;padding:12px 18px;border-radius:10px;text-decoration:none}");
        response.getWriter().println("@media print{.filter,.actions{display:none} body{background:white;color:black}.box{box-shadow:none;border:none}}");
        response.getWriter().println("</style>");

        response.getWriter().println("</head><body>");
        response.getWriter().println("<div class='box'>");

        response.getWriter().println("<h2>Bank Statement</h2>");
        response.getWriter().println("<p>Account Holder: " + user.getName() + "</p>");
        response.getWriter().println("<p>Account Number: " + user.getAccountNumber() + "</p>");

        response.getWriter().println("<form class='filter' action='statement' method='get'>");

        response.getWriter().println("<select name='month' required>");
        response.getWriter().println("<option value=''>Select Month</option>");
        response.getWriter().println("<option value='1'>January</option>");
        response.getWriter().println("<option value='2'>February</option>");
        response.getWriter().println("<option value='3'>March</option>");
        response.getWriter().println("<option value='4'>April</option>");
        response.getWriter().println("<option value='5'>May</option>");
        response.getWriter().println("<option value='6'>June</option>");
        response.getWriter().println("<option value='7'>July</option>");
        response.getWriter().println("<option value='8'>August</option>");
        response.getWriter().println("<option value='9'>September</option>");
        response.getWriter().println("<option value='10'>October</option>");
        response.getWriter().println("<option value='11'>November</option>");
        response.getWriter().println("<option value='12'>December</option>");
        response.getWriter().println("</select>");

        response.getWriter().println("<select name='year' required>");
        response.getWriter().println("<option value=''>Select Year</option>");
        response.getWriter().println("<option value='2026'>2026</option>");
        response.getWriter().println("<option value='2025'>2025</option>");
        response.getWriter().println("<option value='2024'>2024</option>");
        response.getWriter().println("</select>");

        response.getWriter().println("<button type='submit'>Generate Statement</button>");
        response.getWriter().println("</form>");

        if (month != null && year != null) {

            try {
                Connection con = DBConnection.getConnection();

                String query = "SELECT * FROM transactions WHERE email=? AND MONTH(date)=? AND YEAR(date)=? ORDER BY date DESC";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, user.getEmail());
                ps.setInt(2, Integer.parseInt(month));
                ps.setInt(3, Integer.parseInt(year));

                ResultSet rs = ps.executeQuery();

                response.getWriter().println("<h3>Statement for " + month + "/" + year + "</h3>");

                response.getWriter().println("<table>");
                response.getWriter().println("<tr><th>ID</th><th>Type</th><th>Amount</th><th>Receiver</th><th>Date</th></tr>");

                boolean hasData = false;

                while (rs.next()) {
                    hasData = true;

                    response.getWriter().println("<tr>");
                    response.getWriter().println("<td>TXN" + rs.getInt("id") + "</td>");
                    response.getWriter().println("<td>" + rs.getString("type") + "</td>");
                    response.getWriter().println("<td>₹ " + rs.getDouble("amount") + "</td>");
                    response.getWriter().println("<td>" + rs.getString("receiver_account") + "</td>");
                    response.getWriter().println("<td>" + rs.getTimestamp("date") + "</td>");
                    response.getWriter().println("</tr>");
                }

                response.getWriter().println("</table>");

                if (!hasData) {
                    response.getWriter().println("<p style='margin-top:20px;color:#facc15;'>No transactions found for this month.</p>");
                }

                response.getWriter().println("<div class='actions'>");
                response.getWriter().println("<button onclick='window.print()'>Download / Print Statement</button>");
                response.getWriter().println("<a href='dashboard'>Back to Dashboard</a>");
                response.getWriter().println("</div>");

            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("<h3>Error loading statement</h3>");
            }
        }

        response.getWriter().println("</div>");
        response.getWriter().println("</body></html>");
    }
}