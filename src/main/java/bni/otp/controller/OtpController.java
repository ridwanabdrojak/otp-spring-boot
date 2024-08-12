package bni.otp.controller;

import bni.otp.dto.generate.OtpGenerateReq;
import bni.otp.dto.validate.OtpValidateReq;
import bni.otp.service.generate.OtpGenerateService;
import bni.otp.service.validate.OtpValidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class OtpController {

    private final OtpGenerateService otpGenerateService;
    private final OtpValidateService otpvalidateService;

    @PostMapping("/generate/otp")
    public ResponseEntity<?> generateOtp(@Valid @RequestBody OtpGenerateReq req) {
        return otpGenerateService.generateOtp(req);
    }

    @PostMapping("/validate/otp")
    public ResponseEntity<?> validateOtp(@Valid @RequestBody OtpValidateReq req) {
        return otpvalidateService.validateOtp(req);
    }

}
