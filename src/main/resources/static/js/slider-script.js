changeSlidersImages();

function changeSlidersImages() { // change size of images on slider when first load page

    if (window.innerWidth < 992) {
        //   change images for small and big view
        document.getElementById("change-pic-1").src = "/images/pic-1-m.jpg";
        document.getElementById("change-pic-2").src = "/images/pic-2-m.jpg";
        document.getElementById("change-pic-3").src = "/images/pic-3-m.jpg";

    } else {
        //   change images for small and big view
        document.getElementById("change-pic-1").src = "/images/pic-1.jpg";
        document.getElementById("change-pic-2").src = "/images/pic-2.jpg";
        document.getElementById("change-pic-3").src = "/images/pic-3.jpg";
    }
}


window.addEventListener("resize", () => { //listener when resize page for change images

    if (window.innerWidth < 992) {
    
        document.getElementById("change-pic-1").src = "/images/pic-1-m.jpg";
        document.getElementById("change-pic-2").src = "/images/pic-2-m.jpg";
        document.getElementById("change-pic-3").src = "/images/pic-3-m.jpg";

    } else if (window.innerWidth >= 992) {
        
        document.getElementById("change-pic-1").src = "/images/pic-1.jpg";
        document.getElementById("change-pic-2").src = "/images/pic-2.jpg";
        document.getElementById("change-pic-3").src = "/images/pic-3.jpg";
    }
})
