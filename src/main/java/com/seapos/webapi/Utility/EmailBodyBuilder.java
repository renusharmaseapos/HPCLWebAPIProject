package com.seapos.webapi.Utility;


import com.seapos.webapi.Utility.enums.EmailType;
import com.seapos.webapi.config.EmailConfig;
import com.seapos.webapi.models.ChangeUserStatusRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class EmailBodyBuilder {

    private final EmailConfig emailConfig;

    public EmailBodyBuilder(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public String build(EmailType type,
                        ChangeUserStatusRequest r) throws Exception {
        if (type == EmailType.STATUS_UPDATE) {

            String template =
                    r.getUserStatusId() == 5902
                            ? "UserApprovedTemplate.html"
                            : "UserRejectedTemplate.html";

            return buildFromTemplate(template, r);
        }

        EmailConfig.Type config =
                emailConfig.getTypes().get(type.name());

        if (config == null) {
            throw new IllegalStateException(
                    "Email config missing for type: " + type
            );
        }

        return buildFromTemplate(
                config.getTemplate(),
                r
        );
    }

    private String buildFromTemplate(
            String templateFile,
            ChangeUserStatusRequest r
    ) throws Exception {

        EmailConfig.Common c =
                emailConfig.getCommon();

        ClassPathResource resource =
                new ClassPathResource(
                        c.getTemplatePath() + "/" + templateFile
                );

        String body = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        String link = c.getHpclUrl()
                + r.getPageName()
                + CryptoUtil.encryptBase64(
                r.getEntityUserId().toString()
        );

        return body
                .replace("@UserName", r.getUserName())
                .replace("@Remarks",
                        r.getRemarks() == null ? "" : r.getRemarks())
                .replace("@hyperlink", link)
                .replace("@Download", c.getDownloadText())
                .replace("@link", c.getDownloadLink());
    }

    public String getSubject(EmailType type) {

        EmailConfig.Type config =
                emailConfig.getTypes().get(type.name());

        if (config == null) {
            throw new IllegalStateException(
                    "Email subject missing for type: " + type
            );
        }

        return config.getSubject();
    }
}


