/*проверка пароля*/
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
        let check_numb = parseInt(pass[i], 10)

        if (pass[i] === pass[i].toUpperCase() && isNaN(check_numb)) {
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
/*проверка пароля*/



function Post() {
    return false;
}


function send_reg() {
    let infoElement = document.getElementById("info");
    let login = document.getElementById('login');
    let name = document.getElementById('name');
    let surname = document.getElementById('surname');
    let phone = document.getElementById('phone');
    let email = document.getElementById('email');
    let password = document.getElementById('password');
    let user = {
        "login": login.value,
        "name": name.value,
        "surname": surname.value,
        "phone": phone.value,
        "email": email.value,
        "password": password.value,
    }
    const promise =  fetch('registration', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset:utf8'
        },
        body: JSON.stringify(user)
    });
    promise.then(response => response.json())
        .then(response => {
            infoElement.innerHTML = response.info;
            if(response.info != ""){
                login.value = "";
                name.value = "";
                surname.value = "";
                phone.value = "";
                email.value = "";
                password.value = "";
            }
        });
}

function send_auth() {
    let login = document.getElementById('user');
    let password = document.getElementById('password');
    let infoElement = document.getElementById("info");
    let user = {
        "login": login.value,
        "password": password.value,
    }
    const promise =  fetch('authorize', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset:utf8'
        },
        body: JSON.stringify(user)
    });
    promise.then(response => response.json())
    .then(response => {
        infoElement.innerHTML = response.info;
    });
}