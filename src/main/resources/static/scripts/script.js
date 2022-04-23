addClassOnLoad(); //add classes when load page depends from window-size


function openCloseMobileMenu() { // open-close burger menu inline Onclick

    document.getElementById("burger-button").classList.toggle("mobile-menu-opened");

    const nav = document.querySelector("nav");
          nav.classList.toggle("mobile-menu-open");
  }

// add class in nav dropdown ul li when start page
function addClassOnLoad () { 

  const liWrapper = document.querySelector(".nav-li-dropdown-wrapper");
  const navUl = document.getElementById("nav-ul");
  const aCastomizeForDroplist = document.querySelector('.nav-a.open-dropDownA');

        if(window.innerWidth < 992) {
          liWrapper.classList.add("nav-li-dropdown-wrapper-mobile");
          navUl.classList.toggle("nav-ul-mobile");
          aCastomizeForDroplist.textContent = "Jogos pratimai ►";
          
        } else {
          liWrapper.classList.add("nav-li-dropdown-wrapper-pc");
          aCastomizeForDroplist.textContent = "Jogos pratimai ▼";
          navUl.classList.toggle("nav-ul-pc");

        }
}


  //listener when resize page for change class of dropDown ul/li and nav
  window.addEventListener("resize", ()=> { 
    const liWrapper = document.querySelector(".nav-li-dropdown-wrapper");
    const navUl = document.getElementById("nav-ul");
    const aCastomizeForDroplist = document.querySelector('.nav-a.open-dropDownA');

          if(window.innerWidth < 992 && liWrapper.classList.contains("nav-li-dropdown-wrapper-pc")) {
            liWrapper.classList.remove("nav-li-dropdown-wrapper-pc");
            liWrapper.classList.add("nav-li-dropdown-wrapper-mobile");
            navUl.classList.add("nav-ul-mobile");
            aCastomizeForDroplist.textContent = "Jogos pratimai ►";
            navUl.classList.remove("nav-ul-pc");

          } else if (window.innerWidth >= 992 && liWrapper.classList.contains("nav-li-dropdown-wrapper-mobile")) {
            liWrapper.classList.add("nav-li-dropdown-wrapper-pc");
            liWrapper.classList.remove("nav-li-dropdown-wrapper-mobile");
            aCastomizeForDroplist.textContent = "Jogos pratimai ▼";
            navUl.classList.add("nav-ul-pc");
            navUl.classList.remove("nav-ul-mobile");

          }
  })

  function openCloseMobileDroplist() {
    
    const openDrop = document.querySelector(".dropdown-nav-ul");
    const navLiDropListWrapper = document.querySelector('.nav-li-dropdown-wrapper-mobile');
    const aCastomizeForDroplist = document.querySelector('.nav-a.open-dropDownA');

    if(window.innerWidth < 992) {
      navLiDropListWrapper.classList.toggle('pos-top');
      openDrop.classList.toggle("open-dropDown");

      if (openDrop.classList.contains("open-dropDown")){
        aCastomizeForDroplist.textContent = "◄ Atgal į menu";
      } else {
        aCastomizeForDroplist.textContent = "Jogos pratimai ►";
      }
    } 
    
  }

  //to start of page button
  //Get the button:
mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}