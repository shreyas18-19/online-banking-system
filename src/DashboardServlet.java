import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        response.setContentType("text/html");

        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html><head><title>SD Bank Dashboard</title>");

        response.getWriter().println("<style>");
        response.getWriter().println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif;}");
        response.getWriter().println("body{min-height:100vh;background:linear-gradient(135deg,#020617,#0f172a,#111827);color:white;}");
        response.getWriter().println(".navbar{height:75px;padding:0 50px;display:flex;align-items:center;justify-content:space-between;background:rgba(255,255,255,0.08);backdrop-filter:blur(14px);border-bottom:1px solid rgba(255,255,255,0.15);}");
        response.getWriter().println(".logo{font-size:26px;font-weight:800;color:#38bdf8;letter-spacing:1px;}");
        response.getWriter().println(".logout{background:#ef4444;color:white;padding:11px 20px;border-radius:10px;text-decoration:none;font-weight:bold;}");
        response.getWriter().println(".main{padding:45px 60px;}");
        response.getWriter().println(".hero{display:flex;justify-content:space-between;align-items:center;margin-bottom:35px;}");
        response.getWriter().println(".hero h1{font-size:36px;margin-bottom:8px;}");
        response.getWriter().println(".hero p{color:#cbd5e1;font-size:16px;}");
        response.getWriter().println(".bank-card{background:linear-gradient(135deg,#2563eb,#0f172a);border-radius:25px;padding:32px;box-shadow:0 25px 60px rgba(37,99,235,0.35);margin-bottom:35px;position:relative;overflow:hidden;}");
        response.getWriter().println(".bank-card:after{content:'';position:absolute;width:220px;height:220px;background:rgba(255,255,255,0.12);border-radius:50%;right:-70px;top:-70px;}");
        response.getWriter().println(".bank-card h2{font-size:24px;margin-bottom:35px;}");
        response.getWriter().println(".acc{font-size:26px;letter-spacing:3px;font-weight:bold;margin-bottom:25px;}");
        response.getWriter().println(".holder{color:#e0f2fe;font-size:16px;}");
        response.getWriter().println(".cards{display:grid;grid-template-columns:repeat(3,1fr);gap:25px;margin-bottom:35px;}");
        response.getWriter().println(".card{background:rgba(255,255,255,0.09);border:1px solid rgba(255,255,255,0.14);border-radius:20px;padding:25px;box-shadow:0 20px 45px rgba(0,0,0,0.25);}");
        response.getWriter().println(".card h3{color:#94a3b8;font-size:15px;margin-bottom:12px;}");
        response.getWriter().println(".card p{font-size:24px;font-weight:bold;}");
        response.getWriter().println(".actions{display:grid;grid-template-columns:repeat(4,1fr);gap:22px;}");
        response.getWriter().println(".action{background:rgba(255,255,255,0.09);border:1px solid rgba(255,255,255,0.14);border-radius:18px;padding:28px;text-align:center;transition:0.3s;}");
        response.getWriter().println(".action:hover{transform:translateY(-7px);background:rgba(56,189,248,0.16);}");
        response.getWriter().println(".action a{color:white;text-decoration:none;font-size:18px;font-weight:bold;}");
        response.getWriter().println(".footer{margin-top:35px;color:#64748b;text-align:center;font-size:14px;}");
        response.getWriter().println("@media(max-width:850px){.cards,.actions{grid-template-columns:1fr}.main{padding:25px}.navbar{padding:0 22px}.hero h1{font-size:28px}}");
        response.getWriter().println("</style>");

        response.getWriter().println("</head><body>");

        response.getWriter().println("<div class='navbar'>");
        response.getWriter().println("<div class='logo'>SD Secure Bank</div>");
response.getWriter().println("<div style='display:flex;gap:10px;'>");
response.getWriter().println("<a style='background:#2563eb;color:white;padding:10px 18px;border-radius:10px;text-decoration:none;font-weight:bold;' href='statement'>Statement</a>");
response.getWriter().println("<a class='logout' href='logout'>Logout</a>");
response.getWriter().println("</div>");        response.getWriter().println("</div>");

        response.getWriter().println("<div class='main'>");

        response.getWriter().println("<div class='hero'>");
        response.getWriter().println("<div>");
        response.getWriter().println("<h1>Welcome, " + user.getName() + "</h1>");
        response.getWriter().println("<p>Manage your account, transfers and transactions securely.</p>");
        response.getWriter().println("</div>");
        response.getWriter().println("</div>");
        
        response.getWriter().println("<div class='action'><a href='cards'>Cards & Services</a></div>");
        response.getWriter().println("<div class='bank-card'>");
        response.getWriter().println("<h2>Premium Savings Account</h2>");
        response.getWriter().println("<div class='acc'>" + user.getAccountNumber() + "</div>");
        response.getWriter().println("<div class='holder'>Account Holder: " + user.getName() + "</div>");
        response.getWriter().println("</div>");

        response.getWriter().println("<div class='cards'>");
        response.getWriter().println("<div class='card'><h3>Current Balance</h3><p>₹ " + user.getBalance() + "</p></div>");
        response.getWriter().println("<div class='card'><h3>Email</h3><p style='font-size:17px;'>" + user.getEmail() + "</p></div>");
        response.getWriter().println("<div class='card'><h3>Account Status</h3><p style='color:#22c55e;'>ACTIVE</p></div>");
        response.getWriter().println("</div>");

        response.getWriter().println("<div class='actions'>");
        response.getWriter().println("<div class='action'><a href='depositPage'>Deposit</a></div>");
        response.getWriter().println("<div class='action'><a href='withdrawPage'>Withdraw</a></div>");
        response.getWriter().println("<div class='action'><a href='transferPage'>Transfer</a></div>");
        response.getWriter().println("<div class='action'><a href='history'>History</a></div>");
        response.getWriter().println("</div>");

        response.getWriter().println("<div class='footer'>© 2026 SD Secure Bank | Java Servlet Banking System</div>");
        response.getWriter().println("</div>");

        response.getWriter().println("</body></html>");
        response.getWriter().println("<div class='action'><a href='change-password.html'>Change Password</a></div>");
    }
}