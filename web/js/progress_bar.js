setInterval(bar, 10);

function bar() {
    let h_screen = document.documentElement.clientHeight;
    let h_document = document.body.scrollHeight;
    let scrolls = window.pageYOffset || document.documentElement.scrollTop;
    let progress = (scrolls / (h_document - h_screen)) * 100;
    document.getElementById("progress_bar").style.width = String(progress) + "%";
}