import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // hardcoded admin
        if(email.equals("admin@sdbank.com") && password.equals("admin123")) {

            HttpSession session = req.getSession();
            session.setAttribute("admin", email);

            res.sendRedirect("admin");

        } else {

            res.getWriter().println("<h2>Invalid Admin Credentials</h2>");
        }
    }
}