
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