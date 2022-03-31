<%-- 
    Document   : page_change_profile
    Created on : Mar 30, 2022, 3:57:38 PM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main class="change_profile">
    <form action="change_profile">
        <input type="text" placeholder="Логин" name="login">
        <input type="text" placeholder="Имя" name="name">
        <input type="text" placeholder="Фамилия" name="surname">
        <input type="text" placeholder="Телефон" name="phone">
        <input type="text" placeholder="Почта" name="email">
        <input type="text" placeholder="Пароль" name="password">
        <button type="submit">Изменить</button>
    </form>
</main>
