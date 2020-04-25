function logoutInnerPages(){
    localStorage.clear();
    window.location.replace("../index.html");
}

function logout(){
    localStorage.clear();
    window.location.replace("./index.html");
}