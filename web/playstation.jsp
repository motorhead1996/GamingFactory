<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="org.hibernate.Criteria"%>
<%@page import="java.util.List"%>
<%@page import="model.Game"%>
<%@page import="model.Category"%>
<%@page import="org.hibernate.Session"%>

<%@include file="head.jsp" %>
<%@include file="navbar.jsp" %>  
<% Session s = hibernate.HibernateUtil.getSessionFactory().openSession();
    s.beginTransaction();
    Criteria c = s.createCriteria(Game.class);
    c.add(Restrictions.eq("category", (Category) s.get(Category.class, 2)));
    c.add(Restrictions.eq("avaliable", true));
    List<Game> games = c.list();
    s.getTransaction().commit();
%>
<div class="games">
    <h1>PlayStation</h1>
    <div class="gamescontainer">
        <%for (Game game : games) {%>
        <div class="gameswrapper">
            <div class="gamesimg" style="background-image: url('<%=game.getImgurl()%>');">
            </div>
            <div class="gamesspec">
                <div class="title">
                    <p><%=game.getName()%></p> 
                </div>

                <p>Price :<%=game.getPrice()%>  $</p>
                <button class="button" id="add" onclick="addGame(this.value)" value="<%=game.getId()%>">Add</button>
            </div>
        </div>
        <% }%>
    </div>
</div>

<%@include file="footer.jsp" %>
<script src="js/games.js"></script>
<%@include file="ending.jsp" %>
