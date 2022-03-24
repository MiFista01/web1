<%-- 
    Document   : admin
    Created on : Mar 4, 2022, 2:09:39 AM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main>
    <h2 class="topic">Новинки</h2>
    
    <c:forEach items="${items}" var="unit">
        <article>
            <a href="unit?unit_id=${unit.getId()}"><img src="insertFile/${unit.picture.pathToFile}" alt="aaa"></a>
            <p>
                Цена - ${unit.getPrice()}
                <br>
                Год - ${unit.getYear()}
                <br>
                Жанры: ${unit.getKind()}
                <br>
                Описание: ${unit.getDescription().substring(0,200)}...
            </p>
        </article>
    </c:forEach>
</main>