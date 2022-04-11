<%-- 
    Document   : reg
    Created on : Mar 1, 2022, 9:48:38 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main class="reg">
    <h2 class="topic">Регистрация</h2>
    <form action="registration" class="registration" id="reg" method="POST" onsubmit=" return Post()">
        <div>
            <label for="login">Логин</label>
            <input required type="text" name="login" id="login" value="">
        </div>
        <div>
            <label for="name">Имя</label>
            <input required type="text" name="name" id="name" value="">
        </div>
        <div>
            <label for="surname">Фамилия</label>
            <input required type="text" name="surname" id="surname" value="">
        </div>
        <div>
            <label for="phone">Телефон</label>
            <input required type="number" name="phone" id="phone" value="">
        </div>
        <div>
            <label for="email">Почта</label>
            <input required type="email" name="email" id="email" value="">
        </div>
        <div>
            <label for="password">Пароль</label>
            <input required type="password" name="password" id="password" value="" oninput="check(this)">
        </div>
        <div class="check">
            <p id="count">Количество символов 5-12</p>
            <p id="case">Иметь символы верхнего регистра</p>
        </div>
        <p>${info}</p>
        <input type="submit" value="Регистрация" id="btn" onclick="coord_scroll()">
    </form>
</main>

