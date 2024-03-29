package com.twojin.wooritheseday.common.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode {


    /** Custom Error List  */

    BUSINESS_EXCEPTION_ERROR(200, "B999", "Business Exception Error"),

    BUSINESS_ALREADY_PROGRESSED(200, "B999", "이미 처리되었습니다."),

    /** Custom Login Error List */

    USER_NOT_FOUND(200, "B001", "로그인을 실패했습니다. 아이디와 비밀번호를 확인해주세요."),
    USER_PASSWORD_NOT_CORRECT(200, "B002", "비밀번호가 일치하지 않습니다. 다시 입력해주시기 바랍니다."),

    USER_NOT_SELECTED(200, "B003", "사용자 검색에 실패하였습니다."),

    /**
     *  File Error
     */

    FILE_WRONG_FORMAT(200, "F001", "올바른 형식의 파일이 아닙니다."),
    FILE_IMG_UPLOAD_FAIL(200, "F011", "이미지 저장에 실패하였습니다."),
    FILE_IMG_NOT_FOUND(200, "F012", "이미지를 찾지 못했습니다."),
    FILE_IMG_DOWNLOAD(200, "F013", "이미지 불러오기에 실패하였습니다."),




    /**
     * Token Error List
     */

    Expired_Token(200, "T001", "만료된 토큰입니다. 토큰 재발급이 필요합니다"),
    Expired_Token_Need_Login(200, "T002", "만료된 토큰입니다. 다시 로그인 해주세요"),

    /**
     * Partner Error
     */

    PARTNER_FAILED(200,"P000", "처리 중 실패하였습니다. 다시 시도 바랍니다."),
    PARTNER_NOT_EXIST(200,"P001", "등록된 파트너가 없습니다"),
    PARTNER_SELF(200,"P002", "다른 사용자의 코드를 입력해주시길 바랍니다."),

    PARTNER_REQUEST_FAILED(200, "P003", "요청 실패하였습니다."),
    PARTNER_REQUEST_PROGRESSED(200, "P004", "처리 중인 요청 건이 있습니다."),
    PARTNER_REQUEST_USED_TEMPCD(200, "P004", "요청 실패하였습니다."),
    PARTNER_REQUEST_SELECT_FAIL(200, "P005", "요청 목록을 불러오던 중 실패하였습니다."),
    PARTNER_REQUEST_REFUSE_FAIL(200, "P006", "요청 거부를 실패하였습니다. 다시 시도 바랍니다."),
    PARTNER_REGIST_IMPOSIBLE(200, "P007", "등록 실패하였습니다. 파트너 초기화가 필요합니다"),
    PARTNER_UPDATE_MEETDT(200, "P008", "변경 실패하였습니다. 다시 시도해주시길 바랍니다."),

    PARTNER_UPDATE_ALIAS(200, "P009", "변경 실패하였습니다. 다시 시도해주시길 바랍니다."),
    PARTNER_REGIST_QUEUE_FAIL(200,"P001", "등록 실패하였습니다"),
    PARTNER_REGIST_NON_EXIST(200,"P001", "등록돼 있지 않은 코드입니다"),

    PARTNER_REGIST_CANT_USE(200,"P001", "사용 불가능한 코드입니다"),
    PARTNER_REGIST_NOT_FOUND(200,"P001", "조회되지 않는 파트너입니다."),

    /**
     * EstimateError
     */

    ESTIMATE_WRONG_FORM(200,"E001", "올바르지 않은 양식입니다. 다시 작성해주세요"),

    ESTIMATE_EXIST_ALREADY(200,"E002", "이미 작성한 홀의 견적입니다."),

    /**
     * Review Eerror
     */

    REVIEW_REGIST_FAIL(200, "R001", "업로드 중 에러가 발생하였습니다. 다시 작성해주세요"),

    REVIEW_REGIST_ALREADY(200, "R002", "이미 리뷰를 작성한 이력이 있습니다."),

    REVIEW_SELECT_LIST_FAILE(200, "R003", "리스트 조회에 실패하였습니다. 다시 시도 바랍니다."),

    /**
     * Product Error
     */

    PRODUCT_SELECT_FAIL(200, "P001", "조회 중 오류가 발생하였습니다. 다시 시도 바랍니다."),

    PRODUCT_SELECT_NO_RESULT(200, "P002", "조회 결과가 없습니다. 다시 시도 바랍니다."),


    /**
     * * HTTP Status Code
     * * 400 : Bad Request
     * * 401 : Unauthorized
     * * 403 : Forbidden
     * * 404 : Not Found
     * * 500 : Internal Server Error
     */

    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "G001", "Bad Request Exception"),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body is missing"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "G003", " Invalid Type Value"),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "Missing Servlet RequestParameter Exception"),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "G005", "I/O Exception"),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "G006", "JsonParseException"),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "G007", "com.fasterxml.jackson.core Exception"),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "G008", "Forbidden Exception"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "G009", "Not Found Exception"),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "G010", "Null Point Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "G011", "handle Validation Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(404, "G012", "Header에 데이터가 존재하지 않는 경우 "),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "G999", "Internal Server Error Exception"),


    /**
     * ******************************* Custom Error CodeList ***************************************
     */
    // Transaction Insert Error
    INSERT_ERROR(200, "9999", "Insert Transaction Error Exception"),

    // Transaction Update Error
    UPDATE_ERROR(200, "9999", "Update Transaction Error Exception"),

    // Transaction Delete Error
    DELETE_ERROR(200, "9999", "Delete Transaction Error Exception")

    ; // End




    /**
     * ******************************* Error Code Constructor ***************************************
     */
    // 에러 코드의 '코드 상태'을 반환한다.
    private final int status;

    // 에러 코드의 '코드간 구분 값'을 반환한다.
    private final String divisionCode;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
