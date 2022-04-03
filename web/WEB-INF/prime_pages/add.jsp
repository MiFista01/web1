<%-- 
    Document   : add
    Created on : Mar 9, 2022, 6:22:59 AM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main class="add">
    <h2 class="topic">Страница добавления картины</h2>
    <form action="add_img" method="POST" enctype="multipart/form-data">
        <div class="add_img">
            <img src="img/author/img_add.png" alt="" onclick="click_input()" id="img">
            <p><input hidden required type="file" name="image" id="input_img" onchange="change()"></p> 
        </div>
        <div class="text">
            <div class="date">
                <label for="art_name">Название картины:</label>
                <input required type="text" name="art_name" id="art_name">
                <label for="size">Размер картины:</label>
                <input required type="text" name="size" id="size">
                <label for="price">Цена:</label>
                <input required type="number" name="price" id="price">
                <label for="year">Год написания</label>
                <input required type="date" name="year" id="year">
                <label for="kinds">Жанры</label>
                <select required name="kinds" id="kinds" >
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
                <textarea required name="description" id="description" cols="30" rows="10"></textarea>
            </div>
            <button type="submit">Создать</button>
        </div>
    </form>
</main>