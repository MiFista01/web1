<%-- 
    Document   : page_change_profile
    Created on : Mar 30, 2022, 3:57:38 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main class="change_profile">
    <h2 class="topic">Изменение профиля</h2>
    <form action="change_profile">
        <input type="text" placeholder="Логин" name="login" value="${old_login}">
        <input type="text" placeholder="Имя" name="name" value="${old_name}">
        <input type="text" placeholder="Фамилия" name="surname" value="${old_surname}">
        <input type="text" placeholder="Телефон" name="phone" value="${old_phone}">
        <input type="text" placeholder="Почта" name="email" value="${old_email}">
        <input type="text" placeholder="Пароль" name="password" value="${old_password}">
        <button type="submit">Изменить</button>
    </form>
</main>
