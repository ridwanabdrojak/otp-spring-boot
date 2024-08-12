package bni.otp.service.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceUtil {

    public String generateOTP() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10); // Generates a random number between 0 and 9
            sb.append(randomNumber);
        }

        return sb.toString();
    }
}
