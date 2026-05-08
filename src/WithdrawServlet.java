import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");
        double amount = Double.parseDouble(request.getParameter("amount"));

        UserDAO dao = new UserDAO();
        boolean success = dao.withdrawMoney(user.getEmail(), amount);

        if (success) {
            dao.saveTransaction(user.getEmail(), "WITHDRAW", amount);

            User updatedUser = dao.getUserByEmail(user.getEmail());
            session.setAttribute("user", updatedUser);

            response.sendRedirect("dashboard");
        } else {
            response.getWriter().println("<h2>Insufficient Balance</h2>");
            response.getWriter().println("<a href='dashboard'>Go Back</a>");
        }
    }
}