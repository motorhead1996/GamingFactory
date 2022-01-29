package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import org.hibernate.Session;
import hibernate.HibernateUtil;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "Singup", urlPatterns = {"/Singup"})
@MultipartConfig
public class Singup extends HttpServlet {

    private static boolean validRegex(String name, String surname, String email, String password, String city, String address, String phone) {
        String nameRegex = "^(?!-)[a-zA-Z-]*[a-zA-Z]{1,32}$";
        String emailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z'\\d]{8,32}$";
        String cityRegex = "^[a-zA-Z',.\\s-]{1,25}$";
        String addressRegex = "^[A-Za-z0-9 ]+$";
        String phoneRegex = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-'\'s'\'./0-9]{8,11}$";
        if (!name.matches(nameRegex) || name.equals(null)) {
            return false;
        }
        if (!surname.matches(nameRegex) || surname.equals(null)) {
            return false;
        }
        if (!email.matches(emailRegex) || email.equals(null)) {
            return false;
        }
        if (!password.matches(passwordRegex) || password.equals(null)) {
            return false;
        }
        if (!city.matches(cityRegex) || city.equals(null)) {
            return false;
        }
        if (!address.matches(addressRegex) || address.equals(null)) {
            return false;
        }

        return phone.matches(phoneRegex) || phone.equals(null);

    }

    private String singUp(String name, String surname, String email, String password, String city, String address, String phone) {
        String response = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Criteria c = s.createCriteria(User.class);
        c.add(Restrictions.eq("email", email));
        if (c.list().isEmpty()) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setEmail(email);
            newUser.setLvl(false);
            try {
                newUser.setPassword(util.PasswordHash.createHash(password));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(Singup.class.getName()).log(Level.SEVERE, null, ex);
            }
            newUser.setCity(city);
            newUser.setAddress(address);
            newUser.setPhone(phone);
            s.persist(newUser);
            s.getTransaction().commit();
            response = ("Successful");
        } else {
            response = ("Email arleady exists");
        }

        return response;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                response.sendRedirect("index.jsp");
            } else {
                out.print("Bad request");
            }
        } catch (Exception ex) {
            out.print("something went wrong");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String city = request.getParameter("city");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                if (!validRegex(name, surname, email, password, city, address, phone)) {
                    out.print("Wrong parameters");
                } else {
                    out.print(singUp(name, surname, email, password, city, address, phone));

                }
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (Exception ex) {
            out.print("something went wrong");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
