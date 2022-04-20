let role = 0;
let username = "";
document.getElementById("up").addEventListener("load",start())
function start(){
    if (role == 0){
        document.getElementById("up").innerHTML = v0;
    }
    
}


let v0 = `
<div class="head">
    <a href="index" class="headline">Tmart.ee</a>
    <nav class="menu">
        <ul class="tmenu">
            <li onclick = "show('dmenu')"><p>Категории</p> </li>
        </ul>
        <ul class="dmenu">
            <li> <a href="chronology?user=`+username+`">Хронология</a></li>
            <li> <a href="style">Стиль</a></li>
            <li> <a href="size">Размер</a></li>
            <li> |</li>
            <li> <a href="biography">Биография</a></li>
            <li> <a href="reg">Регистрация</a></li>                          
        </ul>
    </nav>
    <div class="authorize">
        <form action="authorize" method="POST" onsubmit="return Post()">
            <div class="inp">
                <label for="name">Пользователь</label>
                <input required type="text" name="user" id="user">
            </div>
            <div class="inp">
                <label for="password">Пароль</label>
                <input required type="password" name="password" id="password">
            </div>
            <p id="info"> </p>
            <input type="submit" value="Вход" onclick="send_auth()">
        </form>
    </div>
</div>`;

let v1 = `
<header id="up">
    <div class="head">
        <a href="index" class="headline">Tmart.ee</a>
        <nav class="menu">
            <ul class="tmenu">
                <li onclick = "show('dmenu')"><p>Категории</p> </li>
            </ul>
            <ul class="dmenu">
                <li> <a href="chronology">Хронология</a></li>
                <li> <a href="style">Стиль</a></li>
                <li> <a href="size">Размер</a></li>
                <li> |</li>
                <li> <a href="biography">Биография</a></li>                      
            </ul>
        </nav>
        <div class="icon user">
            <div class="icons">
                <img src="img/author/ico_author.png" alt="aaa" onclick="show('functions')">
                <p>${user.name} ${user.surname}</p>
            </div>
            <div class="functions">
                <hr>
                <p><a href="page_change_profile">Редактирование профиля</a></p>
                <hr>
                <p><a href="message">Мои заказы</a></p>
                <hr>
                <p><a href="index?out=${0}">Выход</a></p>
                <hr>
            </div>
        </div>
    </div>
</header>`;

let v2 = `
<div class="head">
    <a href="index" class="headline">Tmart.ee</a>
    <nav class="menu">
        <ul class="tmenu">
            <li onclick = "show('dmenu')"><p>Категории</p> </li>
        </ul>
        <ul class="dmenu">
            <li> <a href="chronology">Хронология</a></li>
            <li> <a href="style">Стиль</a></li>
            <li> <a href="size">Размер</a></li>
            <li> |</li>
            <li> <a href="biography">Биография</a></li>                      
        </ul>
    </nav>
    <div class="icon">
        <div class="icons">
            <img src="img/author/ico_author.png" alt="aaa" onclick="show('functions')">
            <p>${user.name} ${user.surname}</p>
        </div>
        <div class="functions">
            <hr>
            <p><a href="add">Добавление статьи</a></p>
            <hr>
            <p><a href="page_change_profile">Редактирование профиля</a></p>
            <hr>
            <p><a href="message">Заказы</a></p>
            <hr>
            <p><a href="index?out=${0}">Выход</a></p>
            <hr>
        </div>
    </div>
</div>`;

function head(){
    const promise =  fetch('authorize', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset:utf8'
        },
        body: JSON.stringify(user)
    });
    promise.then(response => response.json())
    .then(response => {
        console.log(response.role)
    });

}