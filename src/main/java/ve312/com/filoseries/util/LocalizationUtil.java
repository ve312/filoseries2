package ve312.com.filoseries.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

@Component
public class LocalizationUtil {

    /**
     * Obtiene el sufijo del idioma actual (_es, _en, _de)
     * @return El sufijo del idioma
     */

    public String getCurrentLanguageSuffix() {
        String language = LocaleContextHolder.getLocale().getLanguage();

        return switch (language) {
            case "en" -> "_en";
            case "de" -> "_de";
            default -> "_es"; // Español por defecto
        };
    }

    /**
     * Obtiene el valor de un campo localizado de acuerdo al idioma actual
     * @param entity El objeto que contiene los campos localizados
     * @param fieldPrefix El prefijo del campo (sin el sufijo _es, _en, _de)
     * @return El valor del campo en el idioma actual
     */

    public <T> String getLocalizedField(T entity, String fieldPrefix) {
        String suffix = getCurrentLanguageSuffix();

        try {
            String fieldName = fieldPrefix + suffix;
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(entity);
        } catch (Exception e) {
            // Si hay un error, intenta con el getter
            try {
                String getterName = "get" + fieldPrefix.substring(0, 1).toUpperCase() +
                        fieldPrefix.substring(1) + suffix.substring(0, 1).toUpperCase() +
                        suffix.substring(1);
                Method method = entity.getClass().getMethod(getterName);
                return (String) method.invoke(entity);
            } catch (Exception ex) {
                // Si también falla, devuelve null
                return null;
            }
        }
    }

    /**
     * Determina si el idioma actual es el especificado
     * @param language Código de idioma (es, en, de)
     * @return true si es el idioma actual
     */

    public boolean isCurrentLanguage(String language) {
        return LocaleContextHolder.getLocale().getLanguage().equals(language);
    }

    /**
     * Obtiene el idioma actual
     * @return Código del idioma actual (es, en, de)
     */

    public String getCurrentLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    /**
     * Obtiene el Locale actual
     * @return Locale actual
     */

    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
