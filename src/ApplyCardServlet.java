import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/applyCard")
public class ApplyCardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        String cardType = req.getParameter("cardType");
        double charge = Double.parseDouble(req.getParameter("annualCharge"));

        UserDAO dao = new UserDAO();
        dao.applyForCard(user.getEmail(), cardType, charge);

        res.sendRedirect("cardStatus");
    }
}