import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        String senderEmail = user.getEmail();
        String receiverAccount = request.getParameter("receiverAccount");
        double amount = Double.parseDouble(request.getParameter("amount"));

        UserDAO dao = new UserDAO();
        boolean success = dao.transferMoney(senderEmail, receiverAccount, amount);

        if (success) {
            int transactionId = dao.saveTransactionAndGetId(senderEmail, "TRANSFER", amount, receiverAccount);

            User updatedUser = dao.getUserByEmail(senderEmail);
            session.setAttribute("user", updatedUser);

            response.sendRedirect("receipt?id=" + transactionId);
        } else {
            response.getWriter().println("<h2>Transfer Failed</h2>");
            response.getWriter().println("<p>Check receiver account number or insufficient balance.</p>");
            response.getWriter().println("<a href='dashboard'>Go Back</a>");
        }
    }
}