import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("user") == null){
            res.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        String newPassword = req.getParameter("newPassword");

        try {

            Connection con = DBConnection.getConnection();

            String q = "UPDATE users SET password=? WHERE email=?";

            PreparedStatement ps = con.prepareStatement(q);

            ps.setString(1, newPassword);
            ps.setString(2, user.getEmail());

            ps.executeUpdate();

            res.getWriter().println("<h2>Password Changed Successfully</h2>");
            res.getWriter().println("<a href='dashboard'>Dashboard</a>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}