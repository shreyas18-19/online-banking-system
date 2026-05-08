import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/updateCard")
public class UpdateCardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String status = req.getParameter("status");

        UserDAO dao = new UserDAO();
        dao.updateCardStatus(id, status);

        res.sendRedirect("admin");
    }
}