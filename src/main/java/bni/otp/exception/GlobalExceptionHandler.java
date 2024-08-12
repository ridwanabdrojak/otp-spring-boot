package bni.otp.exception;

import bni.otp.dto.OtpResError;
import bni.otp.dto.generate.OtpGenerateRes;
import bni.otp.dto.validate.OtpValidateRes;
import bni.otp.repository.ErrorRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorRepository errorRepository;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OtpResError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        OtpResError responseMap = new OtpResError(errorRepository.getErrorCode("bad_request"), errorRepository.getErrorMessage("bad_request"));
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<OtpResError> handleCustomException(CustomException ex) {
        OtpResError responseMap = new OtpResError(ex.getCode(), ex.getMessage());
        Gson gson = new Gson();
        log.info("{} - End-to-End processing {} otp. Response:\n{}", ex.getLogsId(), ex.getAction(), gson.toJson(responseMap));
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OtpResError> handleGeneralException(Exception ex) {
        log.error("System Error: {} : {}", ex.getClass().getName(), ex.getMessage());
        OtpResError responseMap = new OtpResError(errorRepository.getErrorCode("internal_exception"), errorRepository.getErrorMessage("internal_exception"));
        Gson gson = new Gson();
        return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
