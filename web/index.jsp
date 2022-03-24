<%-- 
    Document   : index
    Created on : Feb 28, 2022, 10:06:43 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main>
    <h2 class="topic">Новинки</h2>
    
    <c:forEach items="${items}" var="unit">
        <article>
        <a href=""><img src="unit.picture.PathToFile" alt="aaa"></a>
        <p>
            Цена- ${unit.price}
        </p>
        <p>
            Год- ${unit.year}
        </p>
        <p>
            Жанры:${unit.kinds}
        </p>
        <p>
            ${unit.description}
        </p>
    </article>
    </c:forEach>
</main>