<%-- 
    Document   : add
    Created on : Mar 9, 2022, 6:22:59 AM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main class="add">
    <form action="add_img" method="POST" enctype="multipart/form-data">
        <div class="add_img">
            <img src="img/author/img_add.png" alt="" onclick="click_input()" id="img">
            <p><input required type="file" name="image" id="input_img" onchange="change()"></p> 
        </div>
        <div class="date">
            <label for="price">Цена:</label>
            <input required type="number" name="price" id="price">
            <label for="year">Год написания</label>
            <input required type="date" name="year" id="year">
            <label for="kinds">Жанры</label>
            <select required name="kinds" id="kinds" size="1" >
                <option disabled selected>выбор жанра</option>
                <option value="portrait">портрет</option>
                <option value="scenery">пейзаж</option>
                <option value="still_life">натюрморт</option>
                <option value="oily">масленный</option>
                <option value="shallow">мелковый</option>
            </select>
        </div>
        <div class="description">
            <label for="description">Описание</label>
            <textarea required name="description" id="description" cols="30" rows="10"></textarea>
        </div>
        <button type="submit">Создать</button>
    </form>
</main>