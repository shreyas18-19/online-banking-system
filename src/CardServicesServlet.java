import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/cards")
public class CardServicesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.html");
            return;
        }

        res.setContentType("text/html");

        res.getWriter().println("""
<html>
<head>
<title>Cards & Services</title>

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Arial,sans-serif;
}

body{
    background:linear-gradient(135deg,#020617,#0f172a,#111827);
    color:white;
    padding:40px;
}

.container{
    max-width:1200px;
    margin:auto;
}

.top{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:35px;
}

.top h1{
    color:#38bdf8;
    font-size:38px;
}

.top a{
    background:#334155;
    color:white;
    padding:12px 18px;
    border-radius:10px;
    text-decoration:none;
    font-weight:bold;
}

.cards{
    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(300px,1fr));
    gap:25px;
}

.card{
    background:rgba(255,255,255,0.08);
    border:1px solid rgba(255,255,255,0.12);
    border-radius:22px;
    padding:28px;
    box-shadow:0 20px 40px rgba(0,0,0,0.35);
    transition:0.3s;
}

.card:hover{
    transform:translateY(-8px);
}

.card h2{
    margin-bottom:12px;
    font-size:28px;
}

.price{
    color:#22c55e;
    font-size:24px;
    font-weight:bold;
    margin-bottom:18px;
}

ul{
    list-style:none;
}

ul li{
    margin:12px 0;
    color:#cbd5e1;
    font-size:15px;
}

button{
    width:100%;
    padding:14px;
    margin-top:20px;
    border:none;
    border-radius:12px;
    background:#2563eb;
    color:white;
    font-size:16px;
    font-weight:bold;
    cursor:pointer;
}

button:hover{
    background:#1d4ed8;
}

.info{
    margin-top:45px;
    background:rgba(255,255,255,0.08);
    border-radius:22px;
    padding:30px;
}

.info h3{
    color:#38bdf8;
    margin-bottom:12px;
}

.info p{
    margin:8px 0;
    color:#cbd5e1;
}

.footerBtns{
    margin-top:30px;
    display:flex;
    gap:15px;
}

.footerBtns a{
    background:#334155;
    color:white;
    padding:12px 18px;
    border-radius:10px;
    text-decoration:none;
    font-weight:bold;
}

</style>
</head>

<body>

<div class='container'>

<div class='top'>
    <h1>Cards & Services</h1>
    <a href='dashboard'>Dashboard</a>
</div>

<div class='cards'>
""");

        card(res,
                "Silver Card",
                "₹199 / year",
                "₹20,000",
                "₹50,000",
                "Basic Support");

        card(res,
                "Gold Card",
                "₹499 / year",
                "₹50,000",
                "₹1,00,000",
                "Priority Support");

        card(res,
                "Platinum Card",
                "₹999 / year",
                "₹1,00,000",
                "₹2,00,000",
                "24x7 Premium Support");

        res.getWriter().println("""

</div>

<div class='info'>

<h3>Help & Support</h3>

<p>📞 Helpline: +91 9876543210</p>
<p>📧 support@sdbank.com</p>
<p>🏢 Pune, Maharashtra</p>

<br>

<h3>Available Cities</h3>

<p>Pune • Mumbai • Nashik • Nagpur • Bangalore • Delhi</p>

</div>

<div class='footerBtns'>
    <a href='cardStatus'>View Card Status</a>
    <a href='dashboard'>Back</a>
</div>

</div>

</body>
</html>
""");
    }

    private void card(HttpServletResponse res,
                      String name,
                      String charge,
                      String dailyLimit,
                      String onlineLimit,
                      String support) throws IOException {

        res.getWriter().println("""

<div class='card'>
""");

        res.getWriter().println("<h2>" + name + "</h2>");

        res.getWriter().println("<div class='price'>" + charge + "</div>");

        res.getWriter().println("""

<ul>
""");

        res.getWriter().println("<li>💳 Daily Limit: " + dailyLimit + "</li>");
        res.getWriter().println("<li>🌐 Online Limit: " + onlineLimit + "</li>");
        res.getWriter().println("<li>⭐ " + support + "</li>");
        res.getWriter().println("<li>🔒 Secure Banking Protection</li>");

        res.getWriter().println("</ul>");

        res.getWriter().println("""

<form action='applyCard' method='post'>
""");

        res.getWriter().println("<input type='hidden' name='cardType' value='" + name + "'>");
        res.getWriter().println("<input type='hidden' name='annualCharge' value='" + charge.replace("₹","").replace("/ year","").trim() + "'>");

        res.getWriter().println("""

<button type='submit'>Apply Now</button>

</form>

</div>
""");
    }
}