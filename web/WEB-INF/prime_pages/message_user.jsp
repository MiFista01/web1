<%-- 
    Document   : message_user
    Created on : Apr 2, 2022, 2:33:12 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main class="message">
    <c:forEach items="${items}" var="order">
        <article>
            <a href="unit_admin?unit_id=${order.unit.getId()}"><img src="insertFile/${order.unit.picture.pathToFile}" alt="aaa"></a>
            <p>
                Название картины - ${order.unit.getArt_name()}
                <br>
                Размер - ${order.unit.getSize()}
                <br>
                Цена - ${order.unit.getPrice()}
                <br>
                Год - ${order.unit.getYear()}
                <br>
                Жанры: ${order.unit.getKind()}
                <br>
                Описание: ${unit.getDescription()}
            </p>
            <p>
                Имя - ${order.user.name}
                <br>
                Фамилия - ${order.user.surname}
                <br>
                Телефон - ${order.user.phone}
                <br>
                Почта - ${order.user.email}
            </p>
            <div class="status">
                <p>${order.status}</p>
            </div>
        </article>
    </c:forEach>
</main>