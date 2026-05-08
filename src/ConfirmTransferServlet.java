import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/confirmTransfer")
public class ConfirmTransferServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String receiverAccount = request.getParameter("receiverAccount");
        String amount = request.getParameter("amount");

        response.setContentType("text/html");

        response.getWriter().println("<html><head><title>Confirm Transfer</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif}");
        response.getWriter().println("body{min-height:100vh;background:linear-gradient(135deg,#020617,#0f172a,#111827);color:white;display:flex;align-items:center;justify-content:center}");
        response.getWriter().println(".box{width:460px;background:rgba(255,255,255,0.1);border:1px solid rgba(255,255,255,0.15);border-radius:22px;padding:35px;box-shadow:0 25px 60px rgba(0,0,0,0.35)}");
        response.getWriter().println("h2{margin-bottom:10px;color:#38bdf8}.sub{color:#cbd5e1;margin-bottom:25px}");
        response.getWriter().println(".detail{background:rgba(255,255,255,0.08);padding:15px;border-radius:12px;margin:12px 0}");
        response.getWriter().println(".detail span{color:#94a3b8;font-size:14px}.detail p{font-size:20px;margin-top:5px;font-weight:bold}");
        response.getWriter().println("button{width:100%;padding:14px;border:none;border-radius:10px;background:#22c55e;color:white;font-size:17px;font-weight:bold;cursor:pointer;margin-top:15px}");
        response.getWriter().println("button:hover{background:#16a34a}");
        response.getWriter().println("a{display:block;text-align:center;margin-top:18px;color:#38bdf8;text-decoration:none}");
        response.getWriter().println("</style></head><body>");

        response.getWriter().println("<div class='box'>");
        response.getWriter().println("<h2>Confirm Transfer</h2>");
        response.getWriter().println("<p class='sub'>Please verify details before transferring.</p>");

        response.getWriter().println("<div class='detail'><span>Receiver Account</span><p>" + receiverAccount + "</p></div>");
        response.getWriter().println("<div class='detail'><span>Amount</span><p>₹ " + amount + "</p></div>");

        response.getWriter().println("<form action='transfer' method='post'>");
        response.getWriter().println("<input type='hidden' name='receiverAccount' value='" + receiverAccount + "'>");
        response.getWriter().println("<input type='hidden' name='amount' value='" + amount + "'>");
        response.getWriter().println("<button type='submit'>Confirm & Transfer</button>");
        response.getWriter().println("</form>");

        response.getWriter().println("<a href='transferPage'>Cancel</a>");
        response.getWriter().println("</div>");

        response.getWriter().println("</body></html>");
    }
}