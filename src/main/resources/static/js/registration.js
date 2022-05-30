let button = document.getElementById("button-to-reg");

button.addEventListener('click', (e) =>{
    if(document.getElementById("password-first").value != document.getElementById("password-repeat").value) {
        document.querySelector(".password-check").classList.remove("d-none");
        e.preventDefault();
    }
    
})