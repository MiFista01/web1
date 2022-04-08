<%-- 
    Document   : biography
    Created on : Apr 3, 2022, 12:24:33 AM
    Author     : aleksei
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:choose>
    <c:when test="${role eq 1 or role eq 0}">
        <main class="biography">
            <h2 class="topic">Биография</h2>
            <form>
            <div class="add_img">
                <c:if test="${biography.picture.pathToFile eq NULL}">
                    <img src="img/author/ico_author.png" alt="">
                </c:if>
                <c:if test="${not empty biography.picture.pathToFile}">
                    <img src="insertFile/${biography.picture.pathToFile}" alt="" id="img">
                </c:if>
            </div>
            <div class="text">
                <div class="description">
                    <p name="description">${biography.biography}</p>
                </div>
            </div>
            </form>
        </main>
    </c:when>
    <c:when test="${role eq 2}">
        <main class="biography">
            <h2 class="topic">Биография</h2>
            <form action="change_biography" method="POST" enctype="multipart/form-data">
                <div class="add_img">
                    <c:if test="${biography.picture.pathToFile eq NULL}">
                        <img src="img/author/img_add.png" alt="" onclick="click_input()" id="img">
                    </c:if>
                    <c:if test="${not empty biography.picture.pathToFile}">
                        <img src="insertFile/${biography.picture.pathToFile}" alt="" onclick="click_input()" id="img">
                    </c:if>
                    <p><input hidden type="file" name="image" id="input_img" onchange="change()"></p> 
                </div>
                <div class="text">
                    <div class="description">
                        <textarea name="description" id="description" cols="30" rows="10" >${biography.biography}</textarea>
                        <input hidden="" type="text" name="id" value="${state.id}">
                        
                    </div>
                    <button type="submit" onclick="coord_scroll()">Изменить</button>
                </div>
            </form>
        </main>
    </c:when>
</c:choose>
