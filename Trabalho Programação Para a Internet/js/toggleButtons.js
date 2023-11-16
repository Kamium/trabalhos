/* NAVBAR HAMBURGER MENU */
function openHamburgerMenu() {
  document.querySelector(".navbar__cta--responsive").classList.toggle("open");
}

/* NAVBAR DROPDOWN */
function openDropdown() {
  document.querySelector(".navbar__cta--user-logged").classList.toggle("open");
}

/* SEARCH FILTER DROPDOWN */
function openFilterDropdown() {
  document.querySelector(".search-filter").classList.toggle("show");
}