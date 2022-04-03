<%-- 
    Document   : message
    Created on : Apr 1, 2022, 8:00:34 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:choose>
    <c:when test="${role eq 1}">
        <main class="message">
            <h2 class="topic">Заказы</h2>
            <c:forEach items="${items}" var="order">
                <article>
                    <a href="unit?unit_id=${order.unit.getId()}"><img src="insertFile/${order.unit.picture.pathToFile}" alt="aaa"></a>
                    <div class="infa">
                        <p>
                            Имя - ${order.user.name}
                            <br>
                            Фамилия - ${order.user.surname}
                            <br>
                            Телефон - ${order.user.phone}
                            <br>
                            Почта - ${order.user.email}
                        </p>
                    </div>
                    
                    <div class="status">
                        <p>${order.status}</p>
                    </div>
                </article>
            </c:forEach>
        </main>
    </c:when>
    <c:when test="${role eq 2}">
        <main class="message">
            <h2 class="topic">Заказы</h2>
            <c:forEach items="${items}" var="order">
                <article>
                    <a href="unit?unit_id=${order.unit.getId()}"><img src="insertFile/${order.unit.picture.pathToFile}" alt="aaa"></a>
                    <div class="infa">
                        <p>
                            Имя - ${order.user.name}
                            <br>
                            Фамилия - ${order.user.surname}
                            <br>
                            Телефон - ${order.user.phone}
                            <br>
                            Почта - ${order.user.email}
                        </p>
                        <p>
                            Дата заказа - ${order.date}
                        </p>
                    </div>
                    <div class="status">
                        <p onclick = "show_status(${order.getId()})" >${order.status}</p>
                        <div class="change_status" id="${order.getId()}">
                            <form action="change_status" method="POST" enctype="multipart/form-data">
                                <input type="submit" name="status" value="В процессе">
                                <input type="submit" name="status" value="Закончено">
                                <input type="submit" name="status" value="Отправлено">
                                <input hidden="" type="text" name="id" value="${order.getId()}">
                            </form>
                        </div>
                    </div>
                </article>
            </c:forEach>
        </main>
    </c:when>
</c:choose>

