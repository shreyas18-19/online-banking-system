import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")   // 🔥 URL mapping
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Create user object
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        // Save to DB
        UserDAO dao = new UserDAO();
        boolean result = dao.registerUser(user);

        // Response
        if (result) {
            response.getWriter().println("Registration Successful");
        } else {
            response.getWriter().println("Registration Failed");
        }
    }
}