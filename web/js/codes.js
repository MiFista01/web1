
function click_input(){
    let input = document.getElementById("input_img");
    input.click();
}
function change(){
    let img = document.getElementById("img");
    let input = document.getElementById("input_img").files[0];
    if (input){
        img.src = URL.createObjectURL(input);
        localStorage.setItem('my_img',img.src);
    }
    img.src = localStorage.getItem('my_img');
}
let numb = 0;
let class_name = "";
function show(name){
    if (document.getElementsByClassName(name)[0].style.transform == "scaleY(1)") {
        document.getElementsByClassName(name)[0].style.transform = "scaleY(0)";
    } else {
        document.getElementsByClassName(name)[0].style.transform = "scaleY(1)";
    }
    
}
function show_status(id){
    let unit = document.getElementById(id);
    if (unit.style.transform == "scaleY(1)") {
        unit.style.transform = "scaleY(0)";
    } else {
        unit.style.transform = "scaleY(1)";
    }
}
