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
            <a href="unit?unit_id=${unit.getId()}&user_role=${user.role}"><img src="insertFile/${unit.picture.pathToFile}" alt="aaa"></a>
            <p>
                Название картины - ${unit.getArt_name()}
                <br>
                Размер - ${unit.getSize()}
                <br>
                Цена - ${unit.getPrice()}€
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