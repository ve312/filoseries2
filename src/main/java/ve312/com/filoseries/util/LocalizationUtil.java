package ve312.com.filoseries.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;



@Component
public class LocalizationUtil {

    /**
     * Obtiene el idioma actual
     * @return Código del idioma actual (es, en, de)
     */
    public String getCurrentLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

}
