package com.seapos.webapi.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.email")
public class EmailConfig {

    private Common common;
    private Map<String, Type> types;

    @Getter @Setter
    public static class Common {
        private String supportMail;
        private String templatePath;
        private String hpclUrl;
        private String downloadText;
        private String downloadLink;
    }

    @Getter @Setter
    public static class Type {
        private String subject;
        private String template;
    }
}
