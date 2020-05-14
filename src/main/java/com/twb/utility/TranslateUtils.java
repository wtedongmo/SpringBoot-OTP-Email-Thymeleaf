package com.twb.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TranslateUtils {

    @Autowired
    @Qualifier(value = "messageSource")
    MessageSource messageSource;

    public String  translate(String codeString) {
        Locale locale = LocaleContextHolder.getLocale();
        String result = messageSource.getMessage(codeString, null, codeString, locale);

        return  result==null? codeString:result;
    }

    public String translate(String codeString, String[] tabs) {
        Locale locale = LocaleContextHolder.getLocale();
        String result = messageSource.getMessage(codeString, tabs, codeString, locale);
        return  result==null? codeString:result;
    }
}
