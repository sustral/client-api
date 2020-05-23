package com.sustral.clientapi.utils.email.sendgrid;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sustral.clientapi.utils.email.EmailService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of EmailService that uses the third party service SendGrid.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class SendGridEmailService implements EmailService {

    // Begin properties values

    @Value("${sendgrid.apiKey}")
    private String sendGridApiKey;

    @Value("${sendgrid.templateIdWelcome}")
    private String templateIdWelcome;

    @Value("${sendgrid.templateIdVerification}")
    private String templateIdVerification;

    @Value("${sendgrid.templateIdPasswordReset}")
    private String templateIdPasswordReset;

    @Value("${sendgrid.welcomeTemplateNameKey}")
    private String welcomeTemplateNameKey;

    @Value("${sendgrid.verificationTemplateNameKey}")
    private String verificationTemplateNameKey;

    @Value("${sendgrid.verificationTemplateLinkKey}")
    private String verificationTemplateLinkKey;

    @Value("${sendgrid.passwordResetTemplateNameKey}")
    private String passwordResetTemplateNameKey;

    @Value("${sendgrid.passwordResetTemplateLinkKey}")
    private String passwordResetTemplateLinkKey;

    @Value("${emailService.defaultEmail}")
    private String defaultEmail;

    @Value("${emailService.defaultEmailName}")
    private String defaultEmailName;

    @Value("${apiPaths.verificationBase}")
    private String apiPathsVerificationBase;

    @Value("${apiPaths.passwordResetBase}")
    private String apiPathsPasswordResetBase;

    // End properties values

    private SendGrid sendGrid;

    @Autowired
    private Logger logger;

    @PostConstruct
    public void initSendGrid() {
        sendGrid = new SendGrid(sendGridApiKey);
    }

    private int sendTemplatedEmailHelper(String email, String templateId, Map<String, String> templateCustomizationMap) {
        Mail mail = new Mail();
        mail.setFrom(new Email(defaultEmail, defaultEmailName));
        mail.setTemplateId(templateId);

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(email));

        for (Map.Entry<String,String> entry: templateCustomizationMap.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }

        mail.addPersonalization(personalization);

        String mailRepresentation = null;

        try {
            mailRepresentation = mail.build();
        } catch (IOException e) {
            logger.error("Failed to construct the string representation of mail", e);
            return -1;
        }

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mailRepresentation);

        Response response = null;

        try {
            response = sendGrid.api(request);
        } catch (IOException e) {
            logger.error("Failed to receive a response from SendGrid", e);
            return -1;
        }

        if (response.getStatusCode() != 202) {
            logger.error("A call to SendGrid returned an error");
            return -1;
        }

        return 0;
    }

    @Override
    public int sendWelcomeEmail(String email, String name) {
        Map <String,String> templateCustomizationMap = new HashMap<>();
        templateCustomizationMap.put(welcomeTemplateNameKey, name);

        return sendTemplatedEmailHelper(email, templateIdWelcome, templateCustomizationMap);
    }

    @Override
    public int sendEmailVerificationEmail(String email, String name, String token) {
        String verificationLink = apiPathsVerificationBase + token;

        Map <String,String> templateCustomizationMap = new HashMap<>();
        templateCustomizationMap.put(verificationTemplateNameKey, name);
        templateCustomizationMap.put(verificationTemplateLinkKey, verificationLink);

        return sendTemplatedEmailHelper(email, templateIdVerification, templateCustomizationMap);
    }

    @Override
    public int sendPasswordResetEmail(String email, String name, String token) {
        String passwordResetLink = apiPathsPasswordResetBase + token;

        Map <String,String> templateCustomizationMap = new HashMap<>();
        templateCustomizationMap.put(passwordResetTemplateNameKey, name);
        templateCustomizationMap.put(passwordResetTemplateLinkKey, passwordResetLink);

        return sendTemplatedEmailHelper(email, templateIdPasswordReset, templateCustomizationMap);
    }

}
