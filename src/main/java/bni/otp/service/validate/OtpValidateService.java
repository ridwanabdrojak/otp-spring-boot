package bni.otp.service.validate;

import bni.otp.config.CommonConfig;
import bni.otp.dto.validate.OtpValidateReq;
import bni.otp.dto.validate.OtpValidateRes;
import bni.otp.exception.CustomException;
import bni.otp.repository.ErrorRepository;
import bni.otp.repository.OtpRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpValidateService {

    private final ErrorRepository errorRepository;
    private final OtpRepository otpRepository;
    private final CommonConfig commonConfig;

    public ResponseEntity<?> validateOtp(OtpValidateReq req) {

//        get request
        String logsId = req.getLogsId();
        String nik = req.getNik();
        String key = req.getKey();
        String otp = req.getOtpValue();
        String action = commonConfig.getValidateString();
//        prepared to request string
        Gson gson = new Gson();
        String reqString = gson.toJson(req);
        log.info("{} - start to validate otp. request:\n{}", logsId, reqString);
//        prepared to select attempt fail
        LocalDateTime intervalFail = LocalDateTime.now().minusMinutes(commonConfig.getIntervalFailOtp());
        Integer attemptFailOrNull = otpRepository.selectAttemptFail(nik, intervalFail);
        int attemptFail = attemptFailOrNull != null ? attemptFailOrNull : commonConfig.getAttemptFailOtp()+1;
        if(attemptFail >= commonConfig.getLimitFailOtp()) {
            throw new CustomException(errorRepository.getErrorCode("otp_fail"), errorRepository.getErrorMessage("otp_fail"), logsId, action);
        }
//        validate otp
        LocalDateTime intervalValidate = LocalDateTime.now().minusMinutes(commonConfig.getIntervalValidateOtp());
        boolean isUsed = otpRepository.selectIsUsed(nik);
        String otpValue = otpRepository.selectOtpCode(nik, key, intervalValidate);
        if(otpValue == null) {
            attemptFail += 1;
            otpRepository.updateAttemptFailOtp(attemptFail, nik);
            throw new CustomException(errorRepository.getErrorCode("otp_exp"), errorRepository.getErrorMessage("otp_exp"), logsId, action);
        }
        if(!otpValue.equals(otp)) {
            attemptFail += 1;
            otpRepository.updateAttemptFailOtp(attemptFail, nik);
            if(attemptFail == commonConfig.getLimitFailOtp()) {
                throw new CustomException(errorRepository.getErrorCode("otp_fail"), errorRepository.getErrorMessage("otp_fail"), logsId, action);
            }
            throw new CustomException(errorRepository.getErrorCode("fail_validate"), errorRepository.getErrorMessage("fail_validate"), logsId, action);
        }
        if(isUsed) {
            attemptFail += 1;
            otpRepository.updateAttemptFailOtp(attemptFail, nik);
            throw new CustomException(errorRepository.getErrorCode("otp_used"), errorRepository.getErrorMessage("otp_used"), logsId, action);
        }
        otpRepository.updateOtpIsUsed(nik);
//        response mapping
        OtpValidateRes responseMap = new OtpValidateRes();
        responseMap.setLogsId(logsId);
        responseMap.setNik(nik);
        responseMap.setKey(key);
        responseMap.setAttemptFail(String.valueOf(attemptFail));
        responseMap.setStatusValidate(String.valueOf(true));
        log.info("{} - End-to-End processing validate otp. Response:\n{}", logsId, gson.toJson(responseMap));
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }
}
