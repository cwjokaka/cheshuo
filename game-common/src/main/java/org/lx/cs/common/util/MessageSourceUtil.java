package org.lx.cs.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceUtil implements MessageSourceAware {
    private static MessageSource messageSource;

    public MessageSourceUtil() {
    }

    public static String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, (Object[])null, locale);
    }

    public static String getMessage(String code, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, (Object[])null, defaultMessage, locale);
    }

    public void setMessageSource(MessageSource messageSource) {
        MessageSourceUtil.messageSource = messageSource;
    }
}
