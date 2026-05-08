import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/withdrawPage")
public class WithdrawPageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");

        response.getWriter().println("<html><head><title>Withdraw Money</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif}");
        response.getWriter().println("body{min-height:100vh;background:linear-gradient(135deg,#020617,#0f172a,#111827);color:white;display:flex;align-items:center;justify-content:center}");
        response.getWriter().println(".box{width:420px;background:rgba(255,255,255,0.1);border:1px solid rgba(255,255,255,0.15);border-radius:22px;padding:35px;box-shadow:0 25px 60px rgba(0,0,0,0.35)}");
        response.getWriter().println("h2{margin-bottom:10px;color:#f97316}.sub{color:#cbd5e1;margin-bottom:25px}");
        response.getWriter().println("input{width:100%;padding:14px;border-radius:10px;border:none;margin:12px 0;font-size:16px}");
        response.getWriter().println("button{width:100%;padding:14px;border:none;border-radius:10px;background:#f97316;color:white;font-size:17px;font-weight:bold;cursor:pointer}");
        response.getWriter().println("a{display:block;text-align:center;margin-top:18px;color:#38bdf8;text-decoration:none}");
        response.getWriter().println("</style></head><body>");

        response.getWriter().println("<div class='box'>");
        response.getWriter().println("<h2>Withdraw Money</h2>");
        response.getWriter().println("<p class='sub'>Withdraw money from your account safely.</p>");
        response.getWriter().println("<form action='withdraw' method='post'>");
        response.getWriter().println("<input type='number' name='amount' placeholder='Enter amount' required>");
        response.getWriter().println("<button type='submit'>Withdraw Now</button>");
        response.getWriter().println("</form>");
        response.getWriter().println("<a href='dashboard'>Back to Dashboard</a>");
        response.getWriter().println("</div>");

        response.getWriter().println("</body></html>");
    }
}