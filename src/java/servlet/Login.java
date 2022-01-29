package servlet;

import hibernate.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    // Done
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (Exception ex) {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {

            request.getSession().removeAttribute("user");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            Criteria c = s.createCriteria(User.class);
            c.add(Restrictions.eq("email", email));
            s.getTransaction().commit();
            if (c.list().isEmpty()) {
                out.print("Bad username");
            } else {
                User user = (User) c.list().get(0);
                try {
                    if (util.PasswordHash.validatePassword(password, user.getPassword())) {
                        request.getSession().setAttribute("user", user);
                        out.print("Successful");
                    } else {
                        out.print("Bad password");
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (Exception ex) {
            out.print("Something went wrong");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
