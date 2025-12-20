package com.seapos.webapi.Utility;


import com.seapos.webapi.Utility.enums.EmailType;
import com.seapos.webapi.config.EmailConfig;
import com.seapos.webapi.exception.EmailTemplateException;
import com.seapos.webapi.models.ChangeUserStatusRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class EmailBodyBuilder {

    private final EmailConfig emailConfig;

    public EmailBodyBuilder(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    /**
     * Builds email HTML body based on EmailType and request data.
     * No checked exception is thrown from this method.
     */
    public String build(
            EmailType type,
            ChangeUserStatusRequest r
    ) {

        try {
            // Special handling for status update mails
            if (type == EmailType.STATUS_UPDATE) {

                String template =
                        r.getUserStatusId() == 5902
                                ? "UserApprovedTemplate.html"
                                : "UserRejectedTemplate.html";

                return buildFromTemplate(template, r);
            }

            // Load type specific config
            EmailConfig.Type config =
                    emailConfig.getTypes().get(type.name());

            if (config == null) {
                throw new EmailTemplateException(
                        "Email config missing for type: " + type
                );
            }

            return buildFromTemplate(
                    config.getTemplate(),
                    r
            );

        } catch (IOException ex) {
            // Convert checked exception to runtime exception
            throw new EmailTemplateException(
                    "Failed to build email body for type: " + type,
                    ex
            );
        }
    }

    /**
     * Reads template file and replaces placeholders.
     */
    private String buildFromTemplate(
            String templateFile,
            ChangeUserStatusRequest r
    ) throws IOException {

        EmailConfig.Common commonConfig =
                emailConfig.getCommon();

        ClassPathResource resource =
                new ClassPathResource(
                        commonConfig.getTemplatePath() + "/" + templateFile
                );

        String body = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        String link = commonConfig.getHpclUrl()
                + r.getPageName()
                + CryptoUtil.encryptBase64(
                r.getEntityUserId().toString()
        );

        return body
                .replace("@UserName", r.getUserName())
                .replace("@Remarks",
                        r.getRemarks() == null ? "" : r.getRemarks())
                .replace("@hyperlink", link)
                .replace("@Download", commonConfig.getDownloadText())
                .replace("@link", commonConfig.getDownloadLink());
    }

    /**
     * Returns email subject for given EmailType.
     */
    public String getSubject(EmailType type) {

        EmailConfig.Type config =
                emailConfig.getTypes().get(type.name());

        if (config == null) {
            throw new EmailTemplateException(
                    "Email subject missing for type: " + type
            );
        }

        return config.getSubject();
    }
}



