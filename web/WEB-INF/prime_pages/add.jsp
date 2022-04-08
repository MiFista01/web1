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
            <p><input hidden required type="file" name="image" id="input_img" onchange="change()" size="1"></p>
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
                <div class="kinds">
                    <div>
                        <input id="портрет" type="checkbox" value="портрет" name="kinds[]">
                        <label for="портрет">портрет</label>
                    </div>

                    <div>
                        <input id="пейзаж" type="checkbox" value="пейзаж" name="kinds[]">
                        <label for="пейзаж">пейзаж</label>
                    </div>

                    <div>
                        <input id="натюрморт" type="checkbox" value="натюрморт" name="kinds[]">
                        <label for="натюрморт">натюрморт</label>
                    </div>

                    <div>
                        <input id="бытовой" type="checkbox" value="бытовой" name="kinds[]">
                        <label for="бытовой">бытовой</label>
                    </div>

                    <div>
                        <input id="анималистический" type="checkbox" value="анималистический" name="kinds[]">
                        <label for="анималистический">анималистический</label>
                    </div>
                </div>
            </div>
            <div class="description">
                <label for="description">Описание</label>
                <textarea required name="description" id="description" cols="30" rows="10"></textarea>
            </div>
            <button type="submit" id="btn" onclick="coord_scroll()">Создать</button>
        </div>
    </form>
    <div class="scroll">
        <img src="img/up.png" onclick="scroll_up('imgs')">
        <div class="imgs_cont">
            <div class="imgs" id="imgs">
                <c:forEach items="${pictures}" var="unit">
                    <img src="insertFile/${unit.picture.pathToFile}">
                </c:forEach>
            </div>
        </div>
        <img src="img/down.png" onclick="scroll_down('imgs')">
        <input hidden type="text" value="${lenght}" id="lenght">
    </div>
</main>