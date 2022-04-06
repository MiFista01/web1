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
let numb = 0;
let step = 0

function scroll_down(name) {
    let lenght = document.getElementById("lenght").value;
    if (step < lenght - 2) {
        step = step + 1;
    }
    numb = 29 * step * (-1) + 0.5;
    document.getElementById(name).style.margin = String(numb + "vh" + " 0px" + " 0px");
    console.log(document.getElementsByClassName(name)[0].style);
}

function scroll_up(name) {
    if (step > 0) {
        step = step - 1;
    }
    numb = 29 * step * (-1) + 0.5;
    document.getElementById(name).style.margin = String(numb + "vh" + " 0px" + " 0px");
    console.log(document.getElementsByClassName(name)[0].style);
}