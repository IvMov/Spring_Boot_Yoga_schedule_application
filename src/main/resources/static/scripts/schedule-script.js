let openCreation = document.getElementById("open-event-create-form");
    openCreation.addEventListener("click", (e) => {
        e.preventDefault;
        showHideForm();
    })

function showHideForm() {
    let form = document.getElementById("event-create-form");
    let openCreation = document.getElementById("open-event-create-form")
        form.classList.toggle("hide-form");
        form.classList.toggle("show-form");

        if(form.classList.contains("show-form")){
            openCreation.textContent="Close creating event"
        } else{
            openCreation.textContent="Open back creating form";
        }
        
}