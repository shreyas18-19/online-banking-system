import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String email = req.getParameter("email");
        String newPassword = req.getParameter("newPassword");

        try {

            Connection con = DBConnection.getConnection();

            String q = "UPDATE users SET password=? WHERE email=?";

            PreparedStatement ps = con.prepareStatement(q);

            ps.setString(1, newPassword);
            ps.setString(2, email);

            int i = ps.executeUpdate();

            if(i > 0){
                res.getWriter().println("<h2>Password Updated Successfully</h2>");
                res.getWriter().println("<a href='login.html'>Login</a>");
            }
            else{
                res.getWriter().println("<h2>Email Not Found</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}