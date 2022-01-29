package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Game;
import model.Purchase;
import model.User;
import org.hibernate.Session;

@WebServlet(name = "Cart", urlPatterns = {"/Cart"})
public class Cart extends HttpServlet {
// Done

    private static boolean isInteger(String requestId) {
        boolean isOk = true;
        try {
            int id = Integer.parseInt(requestId);
        } catch (NumberFormatException ex) {
            isOk = false;
            
        } catch (NullPointerException ex) {
            isOk = false;
            
        }
        return isOk;
    }
    
    private static void sendEmail(User user, HashMap<Game, Integer> cart, Purchase purchase) {
        
        String to = user.getEmail();
        
        String from = "gamingfactorystore@gmail.com";
        
        String host = "smtp.gmail.com";
        
        Properties properties = System.getProperties();
        
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
            
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                
                return new PasswordAuthentication("gamingfactorystore@gmail.com", "gamingfactory");
                
            }
            
        });
        
        session.setDebug(true);
        
        try {
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(from));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            
            message.setSubject("Gaming Factory Confirmation");
            
            String cartItems = null;
            ArrayList<Game> games = new ArrayList<>();
            for (Game game : cart.keySet()) {
                games.add(game);
            }
            for (int i = 0; i < games.size(); i++) {
                if (i == 0) {
                    cartItems = games.get(i).getName() + ", Quantity: " + cart.get(games.get(i))+".";
                } else {
                    cartItems = cartItems + "\n" + games.get(i).getName() + ", Quantity: " + cart.get(games.get(i)) +".";
                }
            }
            message.setText("Dear " + user.getName() + " " + user.getSurname() + ", \n" + "\n" + "Your purchase is registered with id " + purchase.getId() + ". \n"
                    + "List of your Items: \n"
                    + cartItems + "\n" + "Total:" + purchase.getTotal() + "$.\n"   +"Thank you for choosing Gaming Factory."+" \nBest Regards,"+"\nGaming Factory.");
            System.out.println("Dear " + user.getName() + " " + user.getSurname() + ", \n" + "\n" + "Your purchase is registered with id " + purchase.getId() + ". \n"
                    + "List of your Items: \n"
                    + cartItems + "\n" + "Total:" + purchase.getTotal() + "$.\n\n"   +"Thank you for choosing Gaming Factory."+" \n\nBest Regards,"+"\nGaming Factory.");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
    private String json(HashMap<Game, Integer> cart) {
        Gson gsonBuilder = new GsonBuilder().create();
        String json = null;
        int bracterCounter = 0;
        for (Game game : cart.keySet()) {
            if (cart.keySet().isEmpty()) {
                break;
            }
            String jsonGame = gsonBuilder.toJson(game);
            String jsonValue = gsonBuilder.toJson(cart.get(game));
            if (bracterCounter == 0) {
                if (cart.keySet().size() == 1) {
                    json = "[" + jsonGame.substring(0, jsonGame.length() - 1) + ",\"quantity\":" + jsonValue + "}]";
                } else {
                    json = "[" + jsonGame.substring(0, jsonGame.length() - 1) + ",\"quantity\":" + jsonValue + "},";
                }
                bracterCounter++;
                continue;
            }
            if (bracterCounter == cart.keySet().size() - 1) {
                json = json + jsonGame.substring(0, jsonGame.length() - 1) + ",\"quantity\":" + jsonValue + "}]";
                bracterCounter++;
                continue;
                
            }
            
            json = json + jsonGame.substring(0, jsonGame.length() - 1) + ",\"quantity\":" + jsonValue + "},";
            bracterCounter++;
            
        }
        return json;
    }
    
    private String add(String request) {
        String response = null;
        int id = Integer.parseInt(request);
        Session s = hibernate.HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Game game = (Game) s.get(Game.class, id);
        s.getTransaction().commit();
        if (game == null || !game.isAvaliable()) {
            response = ("Bad request");
        } else {
            Game g = util.Unproxy.unProxy(game);
            if (cart.isEmpty()) {
                cart.put(g, cart.getOrDefault(g, 0) + 1);
                response = ("Game Added");
            } else if (cart.containsKey(g)) {
                if (cart.get(g) == 10) {
                    response = ("Max 10 same games");
                } else {
                    cart.put(g, cart.getOrDefault(g, 0) + 1);
                    response = ("Game Added");
                }
            } else if (cart.size() == 10) {
                response = ("Max 10 items in cart");
            } else if (!cart.containsKey(g)) {
                cart.put(g, cart.getOrDefault(g, 0) + 1);
                response = ("Game added");
            }
            
        }
        
        return response;
    }
    
    private String delete(String request) {
        String response = null;
        int id = Integer.parseInt(request);
        Session s = hibernate.HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Game game = (Game) s.get(Game.class, id);
        s.getTransaction().commit();
        if (game == null || !game.isAvaliable()) {
            response = ("Bad request");
            
        } else if (!cart.containsKey(game) || cart.isEmpty()) {
            response = ("Bad request");
        } else {
            int quantity = cart.get(game);
            if (quantity == 1) {
                cart.remove(game);
                response = ("Game removed");
            } else {
                cart.put(game, cart.getOrDefault(game, 0) - 1);
                response = ("Game deleted");
            }
        }
        return response;
    }
    
    private String remove(String request) {
        String response = null;
        
        int id = Integer.parseInt(request);
        Session s = hibernate.HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Game game = (Game) s.get(Game.class, id);
        s.getTransaction().commit();
        if (game == null || !game.isAvaliable()) {
            response = ("Bad request");
        } else if (!cart.containsKey(game) || cart.isEmpty()) {
            response = ("Bad request");
        } else {
            cart.remove(game);
            response = ("Game removed");
        }
        
        return response;
    }
    
    private String buy(String request, User user) {
        String response = null;
        Session s = hibernate.HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        if (cart.isEmpty()) {
            response = ("Bad request, cart is empty");
        } else {
            int total = 0;
            List<Game> list = new ArrayList<>();
            for (Game game : cart.keySet()) {
                total = total + game.getPrice() * cart.get(game);
                for (int i = 0; i < cart.get(game); i++) {
                    list.add(game);
                }
            }
            purchase.setGames(list);
            purchase.setTotal(total);
            s.persist(purchase);
            s.getTransaction().commit();
            sendEmail(user, cart, purchase);
            cart.clear();
            response = ("Successful");
        }
        return response;
    }
    
    private HashMap<Game, Integer> cart = new HashMap<>();
    
    @Override
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            if (request.getSession().getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
            } else {
                
                String json = json(cart);
                out.print(json);
            }
        } catch (Exception ex) {
            out.print("Something went wrong");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            if (request.getSession().getAttribute("user") == null) {
                out.print("Please Log In");
            } else {
                
                String addGame = request.getParameter("addGame");
                String deleteGame = request.getParameter("deleteGame");
                String removeGame = request.getParameter("removeGame");
                String restartCart = request.getParameter("restartCart");
                String buyGames = request.getParameter("buyGames");
                if (addGame != null & isInteger(addGame)) {
                    out.print(add(addGame));
                    
                } else if (deleteGame != null && isInteger(deleteGame)) {
                    out.print(delete(deleteGame));
                    
                } else if (removeGame != null && isInteger(removeGame)) {
                    out.print(remove(removeGame));
                    
                } else if (restartCart != null && isInteger(restartCart)) {
                    
                } else if (buyGames != null && isInteger(buyGames)) {
                    User user = (User) request.getSession().getAttribute("user");
                    out.print(buy(buyGames, user));
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
