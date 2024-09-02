package com.example.barber_appointment.business.concretes;

import com.example.barber_appointment.business.abstracts.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsManager implements SmsService {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @Value("${twilio.verifyServiceSid}")
    private String verifyServiceSid;

    public SmsManager() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber), message)
                .create();
    }

    @Override
    public void sendVerificationSms(String toPhoneNumber) {
        Verification verification = Verification.creator(
                        verifyServiceSid,
                        toPhoneNumber,
                        "sms")
                .create();

        System.out.println("Verification SID: " + verification.getSid());
    }

    @Override
    public boolean checkVerificationCode(String toPhoneNumber, String code) {
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        verifyServiceSid,
                        code)
                .setTo(toPhoneNumber)
                .create();

        return "approved".equals(verificationCheck.getStatus());
    }
}
