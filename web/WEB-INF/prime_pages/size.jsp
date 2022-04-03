<%-- 
    Document   : style
    Created on : Apr 1, 2022, 2:07:27 AM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main>
    <h2 class="topic">Выбор картинк по размерам</h2>
    <form action="size_choise" class="style_choce">
        <select required name="size" id="kinds" >
            <option disabled selected>выбор жанра</option>
            <c:forEach items="${sizes}" var="size">
                <option value="${size}">${size}</option>
            </c:forEach>
        </select>
        <button>Изменить выбор</button>
    </form>
    
    <c:forEach items="${items}" var="unit">
        <article>
            <c:if test="${role eq 2}">
                <div class="delete">
                    <a href="size_choise?delete_id=${unit.id}"><img src="img/author/button_delete.png"></a>
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