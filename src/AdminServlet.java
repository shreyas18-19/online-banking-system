import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);

if(session == null || session.getAttribute("admin") == null){
    res.sendRedirect("admin-login.html");
    return;
}
        res.setContentType("text/html");

        res.getWriter().println("<h2>Admin Panel</h2>");

        try {
            Connection con = DBConnection.getConnection();

            String q = "SELECT * FROM card_applications";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            res.getWriter().println("<table border='1' cellpadding='10'>");
            res.getWriter().println("<tr><th>ID</th><th>Email</th><th>Card</th><th>Status</th><th>Action</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");

                res.getWriter().println("<tr>");
                res.getWriter().println("<td>" + id + "</td>");
                res.getWriter().println("<td>" + rs.getString("email") + "</td>");
                res.getWriter().println("<td>" + rs.getString("card_type") + "</td>");
                res.getWriter().println("<td>" + rs.getString("status") + "</td>");
                res.getWriter().println("<td>");
                res.getWriter().println("<a href='updateCard?id=" + id + "&status=APPROVED'>Approve</a> | ");
                res.getWriter().println("<a href='updateCard?id=" + id + "&status=REJECTED'>Reject</a>");
                res.getWriter().println("</td>");
                res.getWriter().println("</tr>");
            }

            res.getWriter().println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}