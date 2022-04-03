<%-- 
    Document   : style
    Created on : Apr 1, 2022, 2:07:27 AM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main>
    <h2 class="topic">Выбор картин по стилям</h2>
    <form action="style_choice" class="style_choce">
        <select required name="kinds" id="kinds" >
            <option disabled selected>выбор жанра</option>
            <option value="портрет">портрет</option>
            <option value="пейзаж">пейзаж</option>
            <option value="натюрморт">натюрморт</option>
            <option value="бытовой">бытовой</option>
            <option value="анималистический">анималистический</option>
        </select>
        <button>Изменить выбор</button>
    </form>
    
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
                <br>
                Описание: ${unit.getDescription()}
            </p>
        </article>
    </c:forEach>
</main>