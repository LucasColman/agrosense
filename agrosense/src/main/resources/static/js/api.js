


document.getElementById("logout").addEventListener("click", () => {
    localStorage.removeItem("authTtoken");
    window.location.href = "/login";
});


