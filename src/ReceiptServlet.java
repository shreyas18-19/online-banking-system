import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String id = request.getParameter("id");

        response.setContentType("text/html");

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM transactions WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                response.getWriter().println("<html><head><title>Transfer Receipt</title>");

                response.getWriter().println("<style>");
                response.getWriter().println("*{margin:0;padding:0;box-sizing:border-box;font-family:Arial,sans-serif}");
                response.getWriter().println("body{min-height:100vh;background:linear-gradient(135deg,#020617,#0f172a,#111827);color:white;display:flex;align-items:center;justify-content:center}");
                response.getWriter().println(".receipt{width:430px;background:rgba(255,255,255,0.1);border:1px solid rgba(255,255,255,0.15);border-radius:24px;padding:35px;box-shadow:0 25px 60px rgba(0,0,0,0.4);text-align:center}");
                response.getWriter().println(".tick{width:80px;height:80px;border-radius:50%;background:#22c55e;display:flex;align-items:center;justify-content:center;margin:0 auto 20px;font-size:45px;font-weight:bold}");
                response.getWriter().println("h2{color:#22c55e;margin-bottom:8px}.sub{color:#cbd5e1;margin-bottom:25px}");
                response.getWriter().println(".row{display:flex;justify-content:space-between;background:rgba(255,255,255,0.08);padding:13px;border-radius:12px;margin:10px 0;text-align:left}");
                response.getWriter().println(".row span{color:#94a3b8}.row b{color:white}");
                response.getWriter().println(".btns{display:grid;grid-template-columns:1fr 1fr;gap:12px;margin-top:22px}");
                response.getWriter().println("button,a{padding:12px;border-radius:10px;border:none;text-decoration:none;font-weight:bold;cursor:pointer}");
                response.getWriter().println("button{background:#2563eb;color:white}a{background:#334155;color:white}");
                response.getWriter().println("@media print{button,.btns a:last-child{display:none} body{background:white}.receipt{color:black;box-shadow:none;border:1px solid #ddd}}");
                response.getWriter().println("</style>");

                response.getWriter().println("</head><body>");

                response.getWriter().println("<div class='receipt' id='receiptBox'>");
                response.getWriter().println("<div class='tick'>✓</div>");
                response.getWriter().println("<h2>Transfer Successful</h2>");
                response.getWriter().println("<p class='sub'>Your money has been transferred successfully.</p>");

                response.getWriter().println("<div class='row'><span>Transaction ID</span><b>TXN" + rs.getInt("id") + "</b></div>");
                response.getWriter().println("<div class='row'><span>Type</span><b>" + rs.getString("type") + "</b></div>");
                response.getWriter().println("<div class='row'><span>Amount</span><b>₹ " + rs.getDouble("amount") + "</b></div>");
                response.getWriter().println("<div class='row'><span>Receiver</span><b>" + rs.getString("receiver_account") + "</b></div>");
                response.getWriter().println("<div class='row'><span>Date</span><b>" + rs.getTimestamp("date") + "</b></div>");

                response.getWriter().println("<div class='btns'>");
                response.getWriter().println("<button onclick='window.print()'>Download Receipt</button>");
                response.getWriter().println("<button onclick='shareReceipt()'>Share Receipt</button>");
                response.getWriter().println("<a href='dashboard'>Dashboard</a>");
                response.getWriter().println("<a href='history'>History</a>");
                response.getWriter().println("</div>");

                response.getWriter().println("</div>");

                response.getWriter().println("<script>");
                response.getWriter().println("function shareReceipt(){");
                response.getWriter().println("let text='Transfer Successful\\nTransaction ID: TXN" + rs.getInt("id") + "\\nAmount: ₹" + rs.getDouble("amount") + "\\nReceiver: " + rs.getString("receiver_account") + "';");
                response.getWriter().println("if(navigator.share){navigator.share({title:'Transfer Receipt',text:text});}else{navigator.clipboard.writeText(text);alert('Receipt copied to clipboard');}");
                response.getWriter().println("}");
                response.getWriter().println("</script>");

                response.getWriter().println("</body></html>");
            } else {
                response.getWriter().println("<h2>Receipt not found</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Error loading receipt</h2>");
        }
    }
}