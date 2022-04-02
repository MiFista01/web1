<%-- 
    Document   : unit
    Created on : Mar 24, 2022, 10:50:42 AM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main>
    <div class="page_unit">
        <div class="img">
            <img src="insertFile/${state.picture.pathToFile}" alt="aaa">
        </div>
        <div class="mass">
            <p class="inf" name="">Название картины- ${state.art_name}</p>
            <p class="inf">Размер: ${state.size}</p>
            <p class="inf">Цена - ${state.price}</p>
            <p class="inf">Дата написания: ${state.year}</p>
            <p class="inf">Жанры: ${state.kind}</p>
            <p class="inf">Описание</p>
            <p class="inf textarea">${state.description}</p>
            <form action="order" method="POST" enctype="multipart/form-data">
                <input hidden="" name="id" value="${state.id}">
                <button>Заказать</button>
            </form>
        </div>
    </div>
</main>

