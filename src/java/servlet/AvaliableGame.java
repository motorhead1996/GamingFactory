package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Game;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "AvaliableGame", urlPatterns = {"/AvaliableGame"})
public class AvaliableGame extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null && user.isLvl()) {
                Session s = hibernate.HibernateUtil.getSessionFactory().openSession();
                s.beginTransaction();
                ArrayList<Game> games = new ArrayList<>();
                Criteria c = s.createCriteria(Game.class);
                c.add(Restrictions.eq("avaliable", false));
                List<Game> list = c.list();
                s.getTransaction().commit();
                for (Game g : list) {
                    Game newGame = util.Unproxy.unProxy(g);
                    games.add(newGame);
                }
                Gson gsonBuilder = new GsonBuilder().create();
                String json = gsonBuilder.toJson(games);
                out.println(json);
            } else {
                response.sendError(404);
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
            User user = (User) request.getSession().getAttribute("user");
            if (user != null && user.isLvl()) {

                String title = request.getParameter("title");
                if (isInteger(title)) {
                    int id = Integer.parseInt(title);
                    Session s = hibernate.HibernateUtil.getSessionFactory().openSession();
                    s.beginTransaction();
                    Game game = (Game) s.get(Game.class, id);
                    if (game == null || game.isAvaliable()) {
                        out.print("Bad request");
                        s.getTransaction().commit();
                    } else {
                        game.setAvaliable(true);
                        s.update(game);
                        s.getTransaction().commit();
                        out.print("Game is avaliable again");
                    }

                } else {
                    out.print("Bad Request");
                }

            } else {
                response.sendError(404);
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
