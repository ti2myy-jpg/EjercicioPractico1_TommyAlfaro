document.addEventListener("DOMContentLoaded", () => {
    const ventana = document.getElementById("modalBorrar");

    if (ventana) {
        ventana.addEventListener("show.bs.modal", (evento) => {
            const boton = evento.relatedTarget;
            document.getElementById("codigoBorrar").value = boton.getAttribute("data-codigo");
            document.getElementById("nombreBorrar").textContent = boton.getAttribute("data-nombre");
        });
    }
});
