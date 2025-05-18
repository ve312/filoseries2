// validacionUsuarios.js - Validación del formulario de búsqueda de usuarios

document.addEventListener('DOMContentLoaded', function() {
    const formularioBusqueda = document.querySelector('form.d-flex');

    if (formularioBusqueda) {
        // Validar al enviar el formulario
        formularioBusqueda.addEventListener('submit', function(event) {
            const campoBuscarId = document.querySelector('input[name="buscarId"]');

            if (!validarCampoBusqueda(campoBuscarId)) {
                event.preventDefault();
                event.stopPropagation();
            }
        });

        // Validar mientras escribe
        const campoBuscarId = document.querySelector('input[name="buscarId"]');
        if (campoBuscarId) {
            campoBuscarId.addEventListener('input', function() {
                validarCampoBusqueda(this);
            });
        }
    }

    // Función para validar el campo de búsqueda
    function validarCampoBusqueda(campo) {
        // Eliminar mensajes de error anteriores
        limpiarErrores(campo);

        const valor = campo.value.trim();
        let esValido = true;

        // Si el campo está vacío, no es necesario validar más
        if (valor === '') {
            return true;
        }

        // Verificar que sea un número
        if (isNaN(valor)) {
            mostrarError(campo, 'Debe ser un número');
            esValido = false;
        }
        // Verificar que sea mayor que 0
        else if (parseInt(valor) <= 0) {
            mostrarError(campo, 'El ID debe ser mayor que 0');
            esValido = false;
        }

        return esValido;
    }

    // Función para mostrar errores
    function mostrarError(campo, mensaje) {
        campo.classList.add('is-invalid');

        // Buscar o crear el div de feedback
        let feedbackDiv = campo.nextElementSibling;
        if (!feedbackDiv || !feedbackDiv.classList.contains('invalid-feedback')) {
            feedbackDiv = document.createElement('div');
            feedbackDiv.className = 'invalid-feedback';
            campo.parentNode.insertBefore(feedbackDiv, campo.nextSibling);
        }

        feedbackDiv.textContent = mensaje;
    }

    // Función para limpiar errores
    function limpiarErrores(campo) {
        campo.classList.remove('is-invalid');

        // Buscar el div de feedback
        const feedbackDiv = campo.nextElementSibling;
        if (feedbackDiv && feedbackDiv.classList.contains('invalid-feedback')) {
            feedbackDiv.textContent = '';
        }
    }
});
