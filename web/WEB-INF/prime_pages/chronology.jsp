<%-- 
    Document   : chronology
    Created on : Mar 31, 2022, 1:22:04 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main>
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
                Описание: ${unit.getDescription()}
            </p>
        </article>
    </c:forEach>
</main>
