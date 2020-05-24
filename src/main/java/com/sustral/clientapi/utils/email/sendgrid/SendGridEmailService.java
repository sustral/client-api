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
    private final String templateIdWelcome;
    private final String templateIdVerification;
    private final String templateIdPasswordReset;
    private final String welcomeTemplateNameKey;
    private final String verificationTemplateNameKey;
    private final String verificationTemplateLinkKey;
    private final String passwordResetTemplateNameKey;
    private final String passwordResetTemplateLinkKey;
    private final String defaultEmail;
    private final String defaultEmailName;
    private final String apiPathsVerificationBase;
    private final String apiPathsPasswordResetBase;
    // End properties values

    private final SendGrid sendGrid;
    private final Logger logger;

    @Autowired
    public SendGridEmailService(Logger logger,
                                @Value("${sendgrid.apiKey}") String sendGridApiKey,
                                @Value("${sendgrid.templateIdWelcome}") String templateIdWelcome,
                                @Value("${sendgrid.templateIdVerification}") String templateIdVerification,
                                @Value("${sendgrid.templateIdPasswordReset}") String templateIdPasswordReset,
                                @Value("${sendgrid.welcomeTemplateNameKey}") String welcomeTemplateNameKey,
                                @Value("${sendgrid.verificationTemplateNameKey}") String verificationTemplateNameKey,
                                @Value("${sendgrid.verificationTemplateLinkKey}") String verificationTemplateLinkKey,
                                @Value("${sendgrid.passwordResetTemplateNameKey}") String passwordResetTemplateNameKey,
                                @Value("${sendgrid.passwordResetTemplateLinkKey}") String passwordResetTemplateLinkKey,
                                @Value("${emailService.defaultEmail}") String defaultEmail,
                                @Value("${emailService.defaultEmailName}") String defaultEmailName,
                                @Value("${apiPaths.verificationBase}") String apiPathsVerificationBase,
                                @Value("${apiPaths.passwordResetBase}") String apiPathsPasswordResetBase) {

        this.templateIdWelcome = templateIdWelcome;
        this.templateIdVerification = templateIdVerification;
        this.templateIdPasswordReset = templateIdPasswordReset;
        this.welcomeTemplateNameKey = welcomeTemplateNameKey;
        this.verificationTemplateNameKey = verificationTemplateNameKey;
        this.verificationTemplateLinkKey = verificationTemplateLinkKey;
        this.passwordResetTemplateNameKey = passwordResetTemplateNameKey;
        this.passwordResetTemplateLinkKey = passwordResetTemplateLinkKey;
        this.defaultEmail = defaultEmail;
        this.defaultEmailName = defaultEmailName;
        this.apiPathsVerificationBase = apiPathsVerificationBase;
        this.apiPathsPasswordResetBase = apiPathsPasswordResetBase;
        this.logger = logger;

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
