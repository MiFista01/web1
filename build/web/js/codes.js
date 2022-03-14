
function a(){
    let input = document.getElementById("image");
    input.click();
}
function b(){
    let img = document.getElementById("img_adds");
    let input = document.getElementById("image");
    img.src = "/"+input.src;
    console.log(img.src)
}