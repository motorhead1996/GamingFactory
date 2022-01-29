<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Criteria"%>
<%@page import="hibernate.HibernateUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Game"%>
<%@page import="model.Genre"%>
<%@page import="model.Category"%>
<% ArrayList<Genre> genres = new ArrayList();
    Session s = HibernateUtil.getSessionFactory().openSession();
    s.beginTransaction();
    Criteria c = s.createCriteria(Genre.class);
    c = s.createCriteria(Genre.class);
    genres = (ArrayList) c.list();
    s.getTransaction().commit();
%>
<% ArrayList<Category> categories = new ArrayList();
    s.beginTransaction();
    c = s.createCriteria(Category.class);
    categories = (ArrayList) c.list();
    s.getTransaction().commit();
%>
<%@include file="head.jsp" %>
<%@include file="navbar.jsp" %>


<% if (!isOkuser) {
        response.sendError(404);

    } else if (!user.isLvl()) {
        response.sendError(404);
    } else {
%>
<div class="admin">
    <div class="admincontainer">
        <h1>UPLOAD GAME</h1>
        <div class="uploadgameform">
            <form id="uploadgame" method="post" action="UploadGame" enctype="multipart/form-data">
                <p>Title</p>
                <input type="text" name="title" id="title">
                <p>Studio</p>
                <input type="text" name="studio" id="studio">
                <p>Price</p>
                <input type="text" name="price" id="price">
                <p>Genre</p>
                <select name="genre" id="genre">
                    <% for (Genre genre : genres) {%>
                    <option value="<%=genre.getId()%>"><%=genre.getName()%></option>
                    <% }%>
                </select>
                <p>Category</p>
                <select name="category" id="category">
                    <% for (Category category : categories) {%>
                    <option value="<%=category.getId()%>"><%=category.getName()%></option>
                    <% }%>
                </select>
                <input type="file" name="image" id="file" accept="image/*" hidden>
                <button type="button" id="uploadimage">Upload Image</button>
            </form>
            <button id="uploadgamebutton" onclick="upload();">Submit</button>

        </div>
    </div>
</div>       
<div class="admin">
    <div class="admincontainer">
        <h1>DELETE GAME</h1>
        <div class="uploadgameform">
            <form id="deletegame" method="post" action="DeleteGame">
                <p>Title</p>
                <select name="title" id="deleteid">
                </select>
            </form>
            <button onclick="del();">Submit</button>
        </div>
    </div>
</div>   

<div class="admin">
    <div class="admincontainer">
        <h1>MAKE GAME AVALIABLE</h1>
        <div class="uploadgameform">
            <form id="avaliablegame" method="post" action="AvaliableGame">
                <p>Title</p>
                <select name="title" id="avaliableid">
                </select>
            </form>
            <button onclick="avaliable();">Submit</button>
        </div>
    </div>
</div>   

<%}
%>


<%@include file="footer.jsp" %>
<script src="js/admin.js"></script>
<%@include file="ending.jsp" %>

