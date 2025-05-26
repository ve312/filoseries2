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

// Confirmación para eliminar usuarios en la tabla
document.addEventListener('DOMContentLoaded', function() {
    // Event delegation para manejar todos los botones de eliminar de la tabla
    document.addEventListener('click', function(event) {
        // Verificar si se clickeó en un botón de eliminar o su icono
        const deleteButton = event.target.closest('form[action*="/eliminar"] button[type="submit"]');

        if (deleteButton) {
            // Prevenir el envío automático del formulario
            event.preventDefault();

            // Obtener información del usuario para personalizar el mensaje
            const row = deleteButton.closest('tr');
            const username = row.querySelector('td:nth-child(2)').textContent.trim();
            const userId = row.querySelector('td:nth-child(1)').textContent.trim();

            // Mensaje personalizado con el nombre del usuario
            const confirmMessage = `¿Estás seguro de que deseas eliminar al usuario "${username}" (ID: ${userId})?\n\nEsta acción no se puede deshacer.`;

            // Mostrar confirmación
            if (confirm(confirmMessage)) {
                // Si confirma, enviar el formulario
                deleteButton.closest('form').submit();
            }
        }
    });
});
