package com.twojin.wooritheseday.common.utils;

import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.UserDTO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenUtil {

    /**
     * JWT 관련된 토큰 Util
     *
     * @author lee
     * @fileName TokenUtils
     * @since 2022.12.23
     */


        //    @Value(value = "${jwt-secret-key}")
        private static final String jwtSecretKey = "tojinSoftAuthJwtManageTokenForTestButItsWeakKeyExceptionSoIHadToChangeItLongSentence";

        /**
         * 사용자 정보를 기반으로 토큰을 생성하여 반환 해주는 메서드
         *
         * @param userDto UserDto : 사용자 정보
         * @return String : 토큰
         */
        public static String generateAccessJwtToken(UserDTO userDto) {
            // 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
            JwtBuilder builder = Jwts.builder()
                    .setHeader(createHeader())                              // Header 구성
                    .setClaims(createClaims(userDto))                       // Payload - Claims 구성
                    .setSubject(String.valueOf(userDto.getUserCd()))        // Payload - Subject 구성
                    .signWith(SignatureAlgorithm.HS256, createSignature())  // Signature 구성
                    .setExpiration(createExpiredDate(2));                    // Expired Date 구성

            return builder.compact();
        }

    public static String generateRefreshJwtToken(UserDTO userDto) {
        // 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())                              // Header 구성
                .setClaims(createClaims(userDto))                       // Payload - Claims 구성
                .setSubject(String.valueOf(userDto.getUserCd()))        // Payload - Subject 구성
                .signWith(SignatureAlgorithm.HS256, createSignature())  // Signature 구성
                .setExpiration(createExpiredDate(72));                    // Expired Date 구성
        return builder.compact();
    }

        /**
         * 토큰을 기반으로 사용자 정보를 반환 해주는 메서드
         *
         * @param token String : 토큰
         * @return String : 사용자 정보
         */
        public static String parseTokenToUserInfo(String token) {
            return Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }

        /**
         * 유효한 토큰인지 확인 해주는 메서드 with Exception
         *
         * @param token String  : 토큰
         * @return boolean      : 유효한지 여부 반환
         */
        public static boolean isValidToken(String token) {
            try {
                Claims claims = getClaimsFormToken(token);

                log.info("expireTime :" + claims.getExpiration());
                log.info("userId :" + claims.get("userId"));
                log.info("userNm :" + claims.get("userNm"));

                return true;
            } catch (ExpiredJwtException exception) {
                log.error("Token Expired");
                throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
            } catch (JwtException exception) {
                log.error("Token Tampered");
                return false;
            } catch (NullPointerException exception) {
                log.error("Token is null");
                return false;
            }
        }

    public static boolean isValidTokenWithOutException(String token) {
        try {
            Claims claims = getClaimsFormToken(token);

            log.info("expireTime :" + claims.getExpiration());
            log.info("userId :" + claims.get("userId"));
            log.info("userNm :" + claims.get("userNm"));

            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

        /**
         * Header 내에 토큰을 추출합니다.
         *
         * @param header 헤더
         * @return String
         *
         * BEARE'공백'헤더토큰
         */
        public static String getTokenFromHeader(String header) {
            return header.split(" ")[1];
        }

        /**
         * 토큰의 만료기간을 지정하는 함수
         *
         * @return Calendar
         */
        private static Date createExpiredDate(int hour) {
            // 토큰 만료시간은 30일으로 설정
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, hour);     // 8시간
            // c.add(Calendar.DATE, 1);         // 1일
            return c.getTime();
        }

        /**
         * JWT의 "헤더" 값을 생성해주는 메서드
         *
         * @return HashMap<String, Object>
         */
        private static Map<String, Object> createHeader() {
            Map<String, Object> header = new HashMap<>();

            header.put("typ", "JWT");
            header.put("alg", "HS256");
            header.put("regDate", System.currentTimeMillis());
            return header;
        }

        /**
         * 사용자 정보를 기반으로 클래임을 생성해주는 메서드
         *
         * @param userDto 사용자 정보
         * @return Map<String, Object>
         */
        private static Map<String, Object> createClaims(UserDTO userDto) {
            // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
            Map<String, Object> claims = new HashMap<>();

            log.info("userId :" + userDto.getUserId());
            log.info("userNm :" + userDto.getUserNm());

            claims.put("userId", userDto.getUserId());
            claims.put("userNm", userDto.getUserNm());
            return claims;
        }

        /**
         * JWT "서명(Signature)" 발급을 해주는 메서드
         *
         * @return Key
         */
        private static Key createSignature() {
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
            return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        }

        /**
         * 토큰 정보를 기반으로 Claims 정보를 반환받는 메서드
         *
         * @param token : 토큰
         * @return Claims : Claims
         */
        private static Claims getClaimsFormToken(String token) {
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                    .parseClaimsJws(token).getBody();
        }

        /**
         * 토큰을 기반으로 사용자 정보를 반환받는 메서드
         *
         * @param token : 토큰
         * @return String : 사용자 아이디
         */
        public static String getUserIdFromToken(String token) {
            Claims claims = getClaimsFormToken(token);
            return claims.get("userId").toString();
        }

        /**
         * 헤더를 기반으로 사용자 id를 반환받는 메서드
         *
         * @param headers : 헤더
         * @return String : 사용자 아이디
         */
        public static String getUserIdFromHeader(String headers) {
            String token = getTokenFromHeader(headers);
            String userId = getUserIdFromToken(token);
            return userId;
        }
    }
