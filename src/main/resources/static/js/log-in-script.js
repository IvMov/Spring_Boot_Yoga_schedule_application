
hideShowButtonRegistration(); //hide registration form and unused buttons
openRegistrationForm(); //show registration form and hide log inelements

    
function openRegistrationForm() {
    let buttonToRegistation = document.getElementById("button-to-sign");
    let logWrapp = document.getElementsByClassName("button-to-log-wrapp");
    let signWrapp = document.getElementsByClassName("button-to-sign-wrapp");
    let changeH1 = document.getElementById("change-log-in-text");

    buttonToRegistation.addEventListener("click", (e)=> {
        
    e.preventDefault;
    hideShowButtonRegistration();
    changeH1.textContent = "Sign Up";
    signWrapp[0].classList.toggle("d-flex");
    signWrapp[0].classList.toggle("d-none");
    logWrapp[0].classList.toggle("d-flex");
    logWrapp[0].classList.toggle("d-none");
    })
}

function hideShowButtonRegistration() {
    let registrationWrapp = document.getElementsByClassName("button-to-reg-wrapp");
        registrationWrapp[0].classList.toggle("d-flex");
        registrationWrapp[0].classList.toggle("d-none");

        let hiden = document.getElementsByClassName("hide");

    for(i = 0; i<hiden.length; i++){
        hiden[i].classList.toggle("d-flex");
        hiden[i].classList.toggle("d-none");
    }

    if(registrationWrapp[0].classList.contains("d-flex")){
        let buttonRegistration = document.getElementById("button-to-reg");
        //event listener for submit
    }
} 
