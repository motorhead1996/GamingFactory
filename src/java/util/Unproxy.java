package util;

import model.Category;
import model.Game;
import model.Genre;

public class Unproxy {

    // Json ne moze da parsuje Genre & Category zbog toga se rucno vade
    public static Game unProxy(Game proxy) {
        Game game = new Game();
        Category category = new Category();
        category.setId(proxy.getCategory().getId());
        category.setName(proxy.getCategory().getName());
        game.setCategory(category);
        Genre genre = new Genre();
        genre.setId(proxy.getGenre().getId());
        genre.setName(proxy.getGenre().getName());
        game.setGenre(genre);
        game.setId(proxy.getId());
        game.setImgurl(proxy.getImgurl());
        game.setName(proxy.getName());
        game.setPrice(proxy.getPrice());
        game.setStudio(proxy.getStudio());
        game.setAvaliable(proxy.isAvaliable());
        
        return game;        
    }
    
}
