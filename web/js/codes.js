let numb = 0;
let step = 0;
var post = false;

function click_input() {
    let input = document.getElementById("input_img");
    input.click();
}

function change() {
    let img = document.getElementById("img");
    let input = document.getElementById("input_img").files[0];
    if (input) {
        img.src = URL.createObjectURL(input);
        localStorage.setItem('my_img', img.src);
    }
    img.src = localStorage.getItem('my_img');
}

function show(name) {
    if (document.getElementsByClassName(name)[0].style.transform == "scaleY(1)") {
        document.getElementsByClassName(name)[0].style.transform = "scaleY(0)";
    } else {
        document.getElementsByClassName(name)[0].style.transform = "scaleY(1)";
    }

}

function show_status(id) {
    let unit = document.getElementById(id);
    if (unit.style.transform == "scaleY(1)") {
        unit.style.transform = "scaleY(0)";
    } else {
        unit.style.transform = "scaleY(1)";
    }
}


function scroll_down(name) {
    let lenght = document.getElementById("lenght").value;
    if (step < lenght - 2) {
        step = step + 1;
    }
    numb = 29 * step * (-1) + 0.5;
    document.getElementById(name).style.margin = String(numb + "vh" + " 0px" + " 0px");
}

function scroll_up(name) {
    if (step > 0) {
        step = step - 1;
    }
    numb = 29 * step * (-1) + 0.5;
    document.getElementById(name).style.margin = String(numb + "vh" + " 0px" + " 0px");
}

function moveto(bool, name) {
    let page_scroll = JSON.parse(localStorage.getItem("myKey"));
    if (bool == 'true' && page_scroll != 0) {
        window.scrollBy(0,page_scroll);
    }
    localStorage.setItem("myKey", JSON.stringify(0));
}

function coord_scroll() {
    page_scroll = window.pageYOffset || document.documentElement.scrollTop;
    localStorage.setItem("myKey", JSON.stringify(page_scroll));
}

function check(el) {
    let res = false;
    let pass = el.value;
    let count = 0;
    let cases = 0;
    if (pass.length >= 5 && pass.length <= 12) {
        document.getElementById("count").style.color = "green";
        if (count == 0) {
            count = 1;
        }
    } else {
        document.getElementById("count").style.color = "red";
        count = 0;
    }

    for (i = 0; i < pass.length; i++) {
        numb = parseInt(pass[i], 10)

        if (pass[i] === pass[i].toUpperCase() && isNaN(numb)) {
            document.getElementById("case").style.color = "green";
            cases = 1;
            break;
        } else {
            document.getElementById("case").style.color = "red";
        }
        if (pass.length - 1 == i) {
            cases = 0;
        }
    }
    if (cases == 1 && count == 1) {
        res = true;
        post = res
    }
    return res;
}

function Post() {
    
    return post;
}
function send_reg(){
    let login = document.getElementById('login').value;
    let name = document.getElementById('name').value;
    let surname = document.getElementById('surname').value;
    let phone = document.getElementById('phone').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let unit = {
        "login":login,
        "name":name,
        "surname":surname,
        "phone":phone,
        "email":email,
        "password":password,
    }
}