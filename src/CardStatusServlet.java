import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/cardStatus")
public class CardStatusServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        res.setContentType("text/html");

        res.getWriter().println("<h2>Your Card Status</h2>");

        try {
            Connection con = DBConnection.getConnection();

            String q = "SELECT * FROM card_applications WHERE email=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, user.getEmail());

            ResultSet rs = ps.executeQuery();

            res.getWriter().println("<table border='1' cellpadding='10'>");
            res.getWriter().println("<tr><th>ID</th><th>Card</th><th>Charge</th><th>Status</th><th>Date</th></tr>");

            while (rs.next()) {
                res.getWriter().println("<tr>");
                res.getWriter().println("<td>CARD" + rs.getInt("id") + "</td>");
                res.getWriter().println("<td>" + rs.getString("card_type") + "</td>");
                res.getWriter().println("<td>₹" + rs.getDouble("annual_charge") + "</td>");
                res.getWriter().println("<td>" + rs.getString("status") + "</td>");
                res.getWriter().println("<td>" + rs.getTimestamp("applied_date") + "</td>");
                res.getWriter().println("</tr>");
            }

            res.getWriter().println("</table>");
            res.getWriter().println("<br><a href='cards'>Apply New Card</a>");
            res.getWriter().println("<br><a href='dashboard'>Dashboard</a>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}