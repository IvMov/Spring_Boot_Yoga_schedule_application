addClassOnLoad(); //add classes when load page depends from window-size


function openCloseMobileMenu() { // open-close burger menu inline Onclick

    document.getElementById("burger-button").classList.toggle("mobile-menu-opened");

    const nav = document.querySelector("nav");
          nav.classList.toggle("mobile-menu-open");
  }

// add class in nav dropdown ul li when start page
function addClassOnLoad () { 

  const navUl = document.getElementById("nav-ul");

        if(window.innerWidth < 992) {
          navUl.classList.toggle("nav-ul-mobile");
          
        } else {
          navUl.classList.toggle("nav-ul-pc");

        }
}


  //listener when resize page for change class of dropDown ul/li and nav
  window.addEventListener("resize", ()=> {
    const navUl = document.getElementById("nav-ul");

          if(window.innerWidth < 992) {
            navUl.classList.add("nav-ul-mobile");
            navUl.classList.remove("nav-ul-pc");

          } else if (window.innerWidth >= 992) {
            navUl.classList.add("nav-ul-pc");
            navUl.classList.remove("nav-ul-mobile");
          }
  })



  //to start of page button
  //Get the button:
const myButton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    myButton.style.display = "block";
  } else {
    myButton.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}