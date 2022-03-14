<%-- 
    Document   : reg
    Created on : Mar 1, 2022, 9:48:38 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main class="reg">
    <form action="registration" class="registration" method="POST">
        <div>
            <label for="name">Имя</label>
            <input required type="text" name="name" id="name">
        </div>
        <div>
            <label for="surname">Фамилия</label>
            <input required type="text" name="surname" id="surname">
        </div>
        <div>
            <label for="phone">Телефон</label>
            <input required type="number" name="phone" id="phone">
        </div>
        <div>
            <label for="email">Почта</label>
            <input required type="email" name="email" id="email">
        </div>
        <div>
            <label for="password">Пароль</label>
            <input required type="password" name="password" id="password">
        </div>
        <input type="submit" value="Регистрация">
    </form>
</main>

