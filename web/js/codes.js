
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
function show(name){
    numb = numb+1;
    if (numb%2 == 1) {
        /*document.getElementsByClassName(name)[0].style = "opacity: 1;";*/
        document.getElementsByClassName(name)[0].style = "transform: scaleY(1);";
        /*document.getElementsByClassName(name)[0].hidden = false;*/
    } else {
        /*document.getElementsByClassName(name)[0].style = "opacity: 0;";*/
        document.getElementsByClassName(name)[0].style = "transform: scaleY(0);";
        /*document.getElementsByClassName(name)[0].hidden = true;*/
        numb = 0;
    }
}