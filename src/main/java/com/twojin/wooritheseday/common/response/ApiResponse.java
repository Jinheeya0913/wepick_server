package com.twojin.wooritheseday.common.response;

import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

@Getter
@Setter
@Slf4j
public class ApiResponse{


    // API 응답 결과 Response, divisionCode
    private String result;

    // API 응답 코드 Response
    private String resultCode;

    // API 응답 코드 Message
    private String resultMsg;

    private JSONObject resultData;


    @Builder
    public ApiResponse(final String result, final String resultCode, final String resultMsg, JSONObject resultData) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.resultData = resultData;
    }

    @Builder
    public ApiResponse( String  result,  String resultCode,  String resultMsg) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }


    public static ApiResponse createSuccessApiResponseWithObj(JSONObject obj) {
        return ApiResponse.builder()
                .result("SUCCESS")
                .resultCode("101")
                .resultMsg("성공하였습니다")
                .resultData(obj)
                .build();
    }

    public static ApiResponse createSuccessApiResponseAuto() {
        return new ApiResponse("SUCCESS", "101" , "성공하였습니다");
    }

    public static ApiResponse createFailApiResponseAuto() {
        return new ApiResponse("FAILED", "401" , "실패하였습니다");
    }

    public static ApiResponse createFailApiResponseAutoWithException(Exception e) {

        if (e instanceof BusinessExceptionHandler) {
            log.error("[apiResponse] >> getMessage" + ((BusinessExceptionHandler) e).getErrorCode().getMessage());

            return ApiResponse.builder()
                    .result("FAIL:" + ((BusinessExceptionHandler) e).getErrorCode().getDivisionCode()) // : 뒤에 divisionCode
                    .resultCode(Integer.toString(((BusinessExceptionHandler) e).getErrorCode().getStatus()))
                    .resultMsg(e.getMessage())
                    .build();
        } else {
            log.error("[apiResponse] >> ERROR");
            e.printStackTrace();
        }



        return new ApiResponse("FAILED", "401" , "실패하였습니다");
    }

}
