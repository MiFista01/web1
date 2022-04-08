<%-- 
    Document   : unit
    Created on : Mar 24, 2022, 10:50:42 AM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${role eq 1}">
        <main>
            <h2 class="topic">Страница картины</h2>
            <div class="page_unit">
                <div class="img">
                    <img src="insertFile/${state.picture.pathToFile}" alt="aaa">
                </div>
                <div class="mass">
                    <p class="inf" name="">Название картины- ${state.art_name}</p>
                    <p class="inf">Размер: ${state.size}</p>
                    <p class="inf">Цена - ${state.price}€</p>
                    <p class="inf">Дата написания: ${state.year}</p>
                    <p class="inf">Жанры: ${state.kind}</p>
                    <p class="inf">Описание</p>
                    <p class="inf textarea">${state.description}</p>
                    <form action="order" method="POST" enctype="multipart/form-data">
                        <input hidden type="text" name="id" value="${state.id}">
                        <button>Заказать</button>
                    </form>
                </div>
            </div>
        </main>
    </c:when>
    <c:when test="${role eq 2}">
        <main class="add admin_form">
            <h2 class="topic">Страница картины</h2>
            <form action="change_img" method="POST" enctype="multipart/form-data">
                <div class="add_img">
                    <img src="insertFile/${state.picture.pathToFile}" alt="aaa">
                </div>
                <div class="text">
                    <div class="date">
                        <label for="art_name">Название картины:</label>
                        <input type="text" name="art_name" id="art_name" value="${state.art_name}">
                        <label for="size">Размер картины:</label>
                        <input type="text" name="size" id="size" value="${state.size}">
                        <label for="price">Цена:</label>
                        <input type="number" name="price" id="price" value="${state.price}">
                        <label for="year">Год написания</label>
                        <input type="date" name="year" id="year" value="${state.year}">
                        <label for="kinds">Жанры</label>
                        <select name="kinds" id="kinds" value="${state.kind}">
                            <option disabled selected>выбор жанра</option>
                            <option value="портрет">портрет</option>
                            <option value="пейзаж">пейзаж</option>
                            <option value="натюрморт">натюрморт</option>
                            <option value="бытовой">бытовой</option>
                            <option value="анималистический">анималистический</option>
                        </select>
                    </div>
                    <div class="description">
                        <label for="description">Описание</label>
                        <textarea name="description" id="description" cols="30" rows="10" >${state.description}</textarea>
                    </div>
                        <input hidden="" type="text" name="id" value="${state.id}">
                    <button type="submit" onclick="coord_scroll()">Изменить</button>
                </div>
            </form>
        </main>
    </c:when>
    <c:when test="${role eq 0}">
        <main>
            <h2 class="topic">Страница картины</h2>
            <div class="page_unit">
                <div class="img">
                    <img src="insertFile/${state.picture.pathToFile}" alt="aaa">
                </div>
                <div class="mass">
                    <p class="inf" name="">Название картины- ${state.art_name}</p>
                    <p class="inf">Размер: ${state.size}</p>
                    <p class="inf">Цена - ${state.price}€</p>
                    <p class="inf">Дата написания: ${state.year}</p>
                    <p class="inf">Жанры: ${state.kind}</p>
                    <p class="inf">Описание</p>
                    <p class="inf textarea">${state.description}</p>
                </div>
            </div>
        </main>
    </c:when>
</c:choose>

