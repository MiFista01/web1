<%-- 
    Document   : add
    Created on : Mar 9, 2022, 6:22:59 AM
    Author     : aleksei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<main class="add">
            <form action="add">
                <div class="add_img">
                    <input type="file" name="image" id="input_img" onchange="change()">
                    <img src="img/author/img_add.png" alt="" onclick="click_input()" id="img">
                </div>
                <div class="date">
                    <label for="price">Цена:</label>
                    <input type="number" name="price" id="price">
                    <label for="year">Год написания</label>
                    <input type="date" name="year" id="year">
                    <label for="kinds">Жанры</label>
                    <select name="kinds" id="kinds" size="1" >
                        <option disabled selected>выбор жанра</option>
                        <option value="портрет">портрет</option>
                        <option value="пейзаж">пейзаж</option>
                        <option value="натюрморт">натюрморт</option>
                        <option value="масленный">масленный</option>
                        <option value="мелковый">мелковый</option>
                    </select>
                </div>
                <div class="description">
                    <label for="description">Описание</label>
                    <textarea name="description" id="description" cols="30" rows="10"></textarea>
                </div>
                <button type="submit">Создать</button>
            </form>
        </main>