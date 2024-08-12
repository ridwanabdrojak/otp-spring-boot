package bni.otp.service.generate;

import bni.otp.config.CommonConfig;
import bni.otp.dto.generate.OtpGenerateReq;
import bni.otp.dto.generate.OtpGenerateRes;
import bni.otp.exception.CustomException;
import bni.otp.repository.ErrorRepository;
import bni.otp.repository.OtpRepository;
import bni.otp.service.util.OtpServiceUtil;
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
public class OtpGenerateService {

    private final OtpServiceUtil otpServiceUtil;
    private final ErrorRepository errorRepository;
    private final OtpRepository otpRepository;
    private final CommonConfig commonConfig;

    public ResponseEntity<?> generateOtp(OtpGenerateReq req) {

//        get request
        String logsId = req.getLogsId();
        String nik = req.getNik();
        String key = req.getKey();
        String action = commonConfig.getGenerateString();
//        prepared to request string
        Gson gson = new Gson();
        String reqString = gson.toJson(req);
        log.info("{} - start to generate otp. request:\n{}", logsId, reqString);
//        prepared to select attempt fail
        LocalDateTime intervalFail = LocalDateTime.now().minusMinutes(commonConfig.getIntervalFailOtp());
        Integer attemptFailOrNull = otpRepository.selectAttemptFail(nik, intervalFail);
        int attemptFail = attemptFailOrNull != null ? attemptFailOrNull : commonConfig.getAttemptFailOtp();
        if(attemptFail >= commonConfig.getLimitFailOtp()) {
            throw new CustomException(errorRepository.getErrorCode("otp_fail"), errorRepository.getErrorMessage("otp_fail"), logsId, action);
        }
//        prepared to select attempt generate
        LocalDateTime intervalReq = LocalDateTime.now().minusMinutes(commonConfig.getIntervalReqOtp());
        Integer attemptReqOrNull = otpRepository.selectAttemptReq(nik, intervalReq);
        int attemptReq = attemptReqOrNull != null ? attemptReqOrNull+1 : commonConfig.getAttemptReqOtp();
        if (attemptReq > commonConfig.getLimitReqOtp()) {
            throw new CustomException(errorRepository.getErrorCode("otp_req"), errorRepository.getErrorMessage("otp_req"), logsId, action);
        }
//        generate otp
        String otp = otpServiceUtil.generateOTP();
        if (otp.length() != 6) {
            throw new CustomException(errorRepository.getErrorCode("generate_otp"), errorRepository.getErrorMessage("generate_otp"), logsId, action);
        }
//        prepared to insert or update db
        if(attemptReq > 1) {
            otpRepository.updateAttemptReqOtp(key, otp, attemptReq, nik);
        } else {
            Integer selectExist = otpRepository.selectNikExist(nik);
            if (selectExist == null) {
                otpRepository.insertOtp(nik, key, otp, attemptReq, attemptFail);
            } else {
                otpRepository.resetOtp(key, otp, attemptReq, nik);
            }
        }
//        mapping response
        OtpGenerateRes responseMap = new OtpGenerateRes();
        responseMap.setLogsId(logsId);
        responseMap.setNik(nik);
        responseMap.setKey(key);
        responseMap.setOtp(otp);
        responseMap.setAttemptReq(String.valueOf(attemptReq));
        log.info("{} - End-to-End processing generate otp. Response:\n{}", logsId, gson.toJson(responseMap));
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }
}
