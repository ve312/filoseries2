package ve312.com.filoseries.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Obtiene un mensaje localizado por su clave.
     *
     * @param key Clave del mensaje en messages.properties
     * @return Texto localizado
     */
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    /**
     * Obtiene un mensaje localizado por su clave con parámetros.
     *
     * @param key Clave del mensaje en messages.properties
     * @param args Argumentos a insertar en el mensaje
     * @return Texto localizado con parámetros
     */

}
