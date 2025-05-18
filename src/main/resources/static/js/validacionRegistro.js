// validacionRegistro.js - Validación del formulario de registro

document.addEventListener('DOMContentLoaded', function() {
    const formularioRegistro = document.querySelector('form.needs-validation');

    if (formularioRegistro) {
        // Función para validar el formulario completo
        formularioRegistro.addEventListener('submit', function(event) {
            if (!validarFormularioCompleto()) {
                event.preventDefault();
                event.stopPropagation();
            }

            formularioRegistro.classList.add('was-validated');
        });

        // Validación en tiempo real para cada campo
        const campoNombre = document.getElementById('nombre');
        const campoUsername = document.getElementById('username');
        const campoEmail = document.getElementById('email');
        const campoPassword = document.getElementById('password');

        // Validar nombre mientras escribe
        if (campoNombre) {
            campoNombre.addEventListener('input', function() {
                validarNombre(campoNombre);
            });
        }

        // Validar username mientras escribe
        if (campoUsername) {
            campoUsername.addEventListener('input', function() {
                validarUsername(campoUsername);
            });
        }

        // Validar email mientras escribe
        if (campoEmail) {
            campoEmail.addEventListener('input', function() {
                validarEmail(campoEmail);
            });
        }

        // Validar contraseña mientras escribe
        if (campoPassword) {
            campoPassword.addEventListener('input', function() {
                validarPassword(campoPassword);
            });
        }
    }

    // Función para validar el formulario completo
    function validarFormularioCompleto() {
        const nombre = document.getElementById('nombre');
        const username = document.getElementById('username');
        const email = document.getElementById('email');
        const password = document.getElementById('password');

        let esValido = true;

        // Validar cada campo
        if (!validarNombre(nombre)) esValido = false;
        if (!validarUsername(username)) esValido = false;
        if (!validarEmail(email)) esValido = false;
        if (!validarPassword(password)) esValido = false;

        return esValido;
    }

    // Validación del nombre completo (sin números ni caracteres especiales)
    function validarNombre(campo) {
        // Eliminar mensajes de error anteriores
        limpiarErrores(campo);

        const valor = campo.value.trim();
        let esValido = true;

        // Verificar si está vacío
        if (valor === '') {
            mostrarError(campo, 'El nombre completo no puede estar vacío');
            esValido = false;
        }
        // Verificar que solo contenga letras y espacios
        else if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\s]+$/.test(valor)) {
            mostrarError(campo, 'El nombre solo puede contener letras y espacios');
            esValido = false;
        }

        return esValido;
    }

    // Validación del nombre de usuario (máximo 20 caracteres)
    function validarUsername(campo) {
        // Eliminar mensajes de error anteriores
        limpiarErrores(campo);

        const valor = campo.value.trim();
        let esValido = true;

        // Verificar si está vacío
        if (valor === '') {
            mostrarError(campo, 'El nombre de usuario no puede estar vacío');
            esValido = false;
        }
        // Verificar longitud máxima
        else if (valor.length > 20) {
            mostrarError(campo, 'El nombre de usuario no puede tener más de 20 caracteres');
            esValido = false;
        }

        return esValido;
    }

    // Validación del email
    function validarEmail(campo) {
        // Eliminar mensajes de error anteriores
        limpiarErrores(campo);

        const valor = campo.value.trim();
        let esValido = true;

        // Verificar si está vacío
        if (valor === '') {
            mostrarError(campo, 'El correo electrónico no puede estar vacío');
            esValido = false;
        }
        // Verificar formato de email
        else if (!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(valor)) {
            mostrarError(campo, 'Ingresa un correo electrónico válido');
            esValido = false;
        }

        return esValido;
    }

    // Validación de la contraseña
    function validarPassword(campo) {
        // Eliminar mensajes de error anteriores
        limpiarErrores(campo);

        const valor = campo.value;
        let esValido = true;

        // Verificar si está vacía
        if (valor === '') {
            mostrarError(campo, 'La contraseña no puede estar vacía');
            esValido = false;
        }
        // Verificar longitud mínima
        else if (valor.length < 8) {
            mostrarError(campo, 'La contraseña debe tener al menos 8 caracteres');
            esValido = false;
        }
        // Verificar que contenga al menos una letra mayúscula
        else if (!/[A-Z]/.test(valor)) {
            mostrarError(campo, 'La contraseña debe contener al menos una letra mayúscula');
            esValido = false;
        }
        // Verificar que contenga al menos una letra minúscula
        else if (!/[a-z]/.test(valor)) {
            mostrarError(campo, 'La contraseña debe contener al menos una letra minúscula');
            esValido = false;
        }
        // Verificar que contenga al menos un número
        else if (!/[0-9]/.test(valor)) {
            mostrarError(campo, 'La contraseña debe contener al menos un número');
            esValido = false;
        }
        // Verificar que contenga al menos un carácter especial
        else if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(valor)) {
            mostrarError(campo, 'La contraseña debe contener al menos un carácter especial');
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
