package net.thumbtack.buscompany.services;

import com.ibm.icu.text.Transliterator;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TranslitToLatin {

    @Named("transliterate")
    public String transliterate(String parameter) {
        if (parameter != null && Pattern.matches(".*\\p{InCyrillic}.*", parameter)) {
            Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
            return toLatinTrans.transliterate(parameter);
        }
        return parameter;
    }
}
