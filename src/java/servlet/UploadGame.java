package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Game;
import model.Genre;
import model.Category;
import org.hibernate.Session;
import hibernate.HibernateUtil;
import java.io.PrintWriter;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "UploadGame", urlPatterns = {"/UploadGame"})
@MultipartConfig
public class UploadGame extends HttpServlet {
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

    private static boolean validRegex(String title, String studio, String genre, String category, String price, String image) {
        String titlestudioRegex = "^[A-Za-z0-9 ]{2,32}$";
        if (!title.matches(titlestudioRegex)) {
            return false;
        }
        if (!studio.matches(titlestudioRegex)) {
            return false;
        }
        if (isInteger(genre)) {
            int g = Integer.parseInt(genre);
            if (g < 1 || g > 6) {
                return false;
            }
        } else {
            return false;
        }
        if (isInteger(category)) {
            int c = Integer.parseInt(category);
            if (c < 1 || c > 3) {
                return false;
            }
        } else {
            return false;
        }
        return "image".equals(image);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null && user.isLvl()) {
                out.print("Bad request");
            } else {
                response.sendError(404);
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
            if (user != null && user.isLvl()) {
                String title = request.getParameter("title");
                String studio = request.getParameter("studio");
                String requestPrice = request.getParameter("price");
                String requestGenre = request.getParameter("genre");
                String requestCategory = request.getParameter("category");
                Part url = request.getPart("image");
                if (!validRegex(title, studio, requestGenre, requestCategory, requestPrice, url.getContentType().split("/")[0])) {
                    out.print("Wrong Parameters");
                } else {
                    int price = Integer.parseInt(request.getParameter("price"));
                    int genre = Integer.parseInt(request.getParameter("genre"));
                    int categoryId = Integer.parseInt(request.getParameter("category"));

                    Session s = HibernateUtil.getSessionFactory().openSession();
                    s.beginTransaction();
                    Category category = (Category) s.get(Category.class, categoryId);
                    Criteria c = s.createCriteria(Game.class);
                    c.add(Restrictions.eq("name", title));
                    c.add(Restrictions.eq("category", category));
                    if (c.list().isEmpty()) {

                        InputStream is = url.getInputStream();
                        ServletContext context = getServletContext();
                        String fullPath = context.getRealPath("");
                        Path path = Paths.get(fullPath);

                        File file = new File(path.toAbsolutePath().getParent().getParent() + "/web/images/games/" + category.getName() + "/" + url.getSubmittedFileName());
                        file.createNewFile();
                        java.nio.file.Files.copy(url.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        String imgurl = "images/games/" + category.getName() + "/" + file.getName();
                        Game game = new Game();
                        game.setName(title);
                        game.setStudio(studio);
                        game.setPrice(price);
                        game.setImgurl(imgurl);
                        game.setGenre((Genre) s.get(Genre.class, genre));
                        game.setCategory(category);
                        game.setAvaliable(true);
                        s.persist(game);
                        s.getTransaction().commit();
                        out.print("Game addedd");
                    } else {
                        Game game = (Game) c.list().get(0);
                        if (game.isAvaliable()) {
                            out.print("Game exists");
                        }
                    }
                }
            } else {
                response.sendError(404);
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
