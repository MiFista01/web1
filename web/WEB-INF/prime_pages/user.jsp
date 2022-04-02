<%-- 
    Document   : user
    Created on : Apr 1, 2022, 3:22:23 AM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main>
    <h2 class="topic">Новинки</h2>
    
    <c:forEach items="${items}" var="unit">
        <article>
            <a href="unit?unit_id=${unit.getId()}"><img src="insertFile/${unit.picture.pathToFile}" alt="aaa"></a>
            <p>
                Название картины - ${unit.getArt_name()}
                <br>
                Размер - ${unit.getSize()}
                <br>
                Цена - ${unit.getPrice()}
                <br>
                Год - ${unit.getYear()}
                <br>
                Жанры: ${unit.getKind()}
                <br>
                Описание: ${unit.getDescription()}
            </p>
        </article>
    </c:forEach>
</main>
