<%-- 
    Document   : unit
    Created on : Mar 24, 2022, 10:50:42 AM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main>
    <div class="main">
        <div class="img">
            <img src="insertFile/${state.picture.pathToFile}" alt="">
        </div>
        <div class="mass">
            <p class="inf">Цена - ${state.price}</p>
            <p class="inf">Дата написания: ${state.year}</p>
            <p class="inf">Жанры: ${state.kind}</p>
            <p class="inf text">${state.description}</p>
        </div>
    </div>
</main>
