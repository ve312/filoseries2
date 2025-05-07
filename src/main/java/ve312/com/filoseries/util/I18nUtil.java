package ve312.com.filoseries.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class I18nUtil {

    private final MessageSource messageSource;

    @Autowired
    public I18nUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Obtiene un mensaje internacionalizado basado en la clave proporcionada
     * y usando el idioma actual de la sesión.
     *
     * @param code Clave del mensaje
     * @return El mensaje en el idioma actual
     */

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * Obtiene un mensaje internacionalizado basado en la clave y los argumentos proporcionados,
     * usando el idioma actual de la sesión.
     *
     * @param code Clave del mensaje
     * @param args Argumentos para formatear el mensaje
     * @return El mensaje formateado en el idioma actual
     */

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * Obtiene un mensaje internacionalizado basado en la clave proporcionada
     * y usando el idioma especificado.
     *
     * @param code Clave del mensaje
     * @param locale Idioma específico
     * @return El mensaje en el idioma especificado
     */

    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    /**
     * Obtiene un mensaje internacionalizado basado en la clave y los argumentos proporcionados,
     * usando el idioma especificado.
     *
     * @param code Clave del mensaje
     * @param args Argumentos para formatear el mensaje
     * @param locale Idioma específico
     * @return El mensaje formateado en el idioma especificado
     */

    public String getMessage(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, locale);
    }

    /**
     * Obtiene el idioma actual de la sesión
     *
     * @return Locale actual
     */

    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * Determina si el idioma actual es el idioma especificado
     *
     * @param language Código de idioma (es, en, de)
     * @return true si es el idioma actual
     */

    public boolean isCurrentLanguage(String language) {
        return getCurrentLocale().getLanguage().equals(language);
    }
}
