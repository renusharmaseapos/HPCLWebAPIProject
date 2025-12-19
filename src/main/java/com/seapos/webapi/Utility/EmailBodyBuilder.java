package com.seapos.webapi.Utility;


import com.seapos.webapi.models.ChangeUserStatusRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailBodyBuilder {

    @Value("${app.email.template-path}")
    private String templatePath;

    @Value("${app.email.hpcl-url}")
    private String hpclUrl;

    @Value("${app.email.download-text}")
    private String downloadText;

    @Value("${app.email.download-link}")
    private String downloadLink;

    public String build(ChangeUserStatusRequest r) throws Exception {

        String templateFile =
                r.getUserStatusId() == 5902
                        ? "UserApprovedTemplate.html"
                        : "UserRejectedTemplate.html";

        ClassPathResource resource =
                new ClassPathResource(templatePath + "/" + templateFile);

        String body = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        String link = hpclUrl + r.getPageName()
                + CryptoUtil.encryptBase64(r.getEntityUserId().toString());

        return body.replace("@UserName", r.getUserName())
                .replace("@Remarks", r.getRemarks())
                .replace("@hyperlink", link)
                .replace("@Download", downloadText)
                .replace("@link", downloadLink);
    }
}
