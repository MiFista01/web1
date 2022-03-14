<%-- 
    Document   : index
    Created on : Feb 28, 2022, 10:06:43 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main>
    <h2 class="topic">Новинки</h2>
    <!--<article>
        <a href=""><img src="img/post1.jpg" alt="aaa"></a>
        <p>
            Год-
            <br>
            Жанры:
            <br>
            Текст...
        </p>
    </article>-->
    <c:forEach items="${items}" var="unit">
        <article>
        <a href=""><img src="img/post1.jpg" alt="aaa"></a>
        <p>
            Год- ${unit.year}
            <br>
            Жанры:${unit.kinds}
            <br>
            ${unit.description}
        </p>
    </article>
    </c:forEach>
</main>