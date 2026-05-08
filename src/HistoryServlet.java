import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");
        response.setContentType("text/html");

        response.getWriter().println("<html><head><title>Transaction History</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif}");
        response.getWriter().println("body{min-height:100vh;background:linear-gradient(135deg,#020617,#0f172a,#111827);color:white;padding:40px}");
        response.getWriter().println(".container{max-width:1000px;margin:auto;background:rgba(255,255,255,0.09);border:1px solid rgba(255,255,255,0.15);border-radius:22px;padding:30px;box-shadow:0 25px 60px rgba(0,0,0,0.35)}");
        response.getWriter().println("h2{color:#38bdf8;margin-bottom:10px}.sub{color:#cbd5e1;margin-bottom:25px}");
        response.getWriter().println("table{width:100%;border-collapse:collapse;overflow:hidden;border-radius:14px}");
        response.getWriter().println("th{background:#2563eb;padding:15px;text-align:left}");
        response.getWriter().println("td{padding:14px;border-bottom:1px solid rgba(255,255,255,0.12);color:#e2e8f0}");
        response.getWriter().println("tr:hover{background:rgba(56,189,248,0.12)}");
        response.getWriter().println("a{display:inline-block;margin-top:22px;color:white;background:#2563eb;padding:12px 18px;border-radius:10px;text-decoration:none}");
        response.getWriter().println("</style></head><body>");

        response.getWriter().println("<div class='container'>");
        response.getWriter().println("<h2>Transaction History</h2>");
        response.getWriter().println("<p class='sub'>All your account activity in one place.</p>");

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM transactions WHERE email=? ORDER BY date DESC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getEmail());

            ResultSet rs = ps.executeQuery();

            response.getWriter().println("<table>");
            response.getWriter().println("<tr><th>ID</th><th>Type</th><th>Amount</th><th>Date</th></tr>");

            while (rs.next()) {
                response.getWriter().println("<tr>");
                response.getWriter().println("<td>" + rs.getInt("id") + "</td>");
                response.getWriter().println("<td>" + rs.getString("type") + "</td>");
                response.getWriter().println("<td>₹ " + rs.getDouble("amount") + "</td>");
                response.getWriter().println("<td>" + rs.getTimestamp("date") + "</td>");
                response.getWriter().println("</tr>");
            }

            response.getWriter().println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error loading history</h3>");
        }

        response.getWriter().println("<a href='dashboard'>Back to Dashboard</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("</body></html>");
    }
}