package com.twojin.wooritheseday.common.codes;

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

    USER_PROFILE_IMG_UPLOAD(200, "F001", "이미지 저장에 실패하였습니다."),
    USER_PROFILE_IMG_NOT_FOUND(200, "F002", "이미지를 찾지 못했습니다."),
    USER_PROFILE_IMG_DOWNLOAD(200, "F003", "이미지 불러오기에 실패하였습니다."),


    /**
     * Token Error List
     */

    Expired_Token(200, "T001", "만료된 토큰입니다. 토큰 재발급이 필요합니다"),
    Expired_Token_Need_Login(200, "T002", "만료된 토큰입니다. 다시 로그인 해주세요"),

    /**
     * Partner Error
     */

    PARTNER_NOT_EXIST(200,"P001", "등록된 파트너가 없습니다"),
    PARTNER_SELF(200,"P002", "다른 사용자의 코드를 입력해주시길 바랍니다."),

    PARTNER_REQUEST_FAILED(200, "P003", "요청 실패하였습니다."),
    PARTNER_REQUEST_PROGRESSED(200, "P004", "처리 중인 요청 건이 있습니다."),
    PARTNER_REQUEST_USED_TEMPCD(200, "P004", "요청 실패하였습니다."),
    PARTNER_REQUEST_SELECT_FAIL(200, "P005", "요청 목록을 불러오던 중 실패하였습니다."),
    PARTNER_REGIST_QUEUE_FAIL(200,"P001", "등록 실패하였습니다"),
    PARTNER_REGIST_NON_EXIST(200,"P001", "등록돼 있지 않은 코드입니다"),

    PARTNER_REGIST_CANT_USE(200,"P001", "사용 불가능한 코드입니다"),
    PARTNER_REGIST_NOT_FOUND(200,"P001", "조회되지 않는 파트너입니다."),



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
