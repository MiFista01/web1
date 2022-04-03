<%-- 
    Document   : chronology
    Created on : Mar 31, 2022, 1:22:04 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main>
    <h2 class="topic">Хронология написание картин</h2>
    <c:forEach items="${items}" var="unit">
        <article>
            <c:if test="${role eq 2}">
                <div class="delete">
                    <a href="chronology?delete_id=${unit.id}"><img src="img/author/button_delete.png"></a>
                </div>
            </c:if>
            
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
                <br>
                Описание: ${unit.getDescription()}
            </p>
        </article>
    </c:forEach>
</main>
