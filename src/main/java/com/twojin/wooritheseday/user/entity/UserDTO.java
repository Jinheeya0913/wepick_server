package com.twojin.wooritheseday.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "woori_user_m")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDTO implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userCd;

    @Column(nullable = false)
    private String userNm;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String userId;
    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false, unique = true)
    private String userPhoneNum;

    @CreationTimestamp
    private Date regDate;

    private String userUseAt;

    private String userImgUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

//    @Builder
//    UserDTO(String userNm, String userId, String userPw, String userEmail, String userAddress, String userPhoneNum) {
//        this.userNm = userNm;
//        this.userId = userId;
//        this.userEmail = userEmail;
//        this.userAddress = userAddress;
//        this.userPw = userPw;
//        this.userPhoneNum = userPhoneNum;
//        this.userUseAt = "Y";
//    }

    @Builder
    UserDTO(String userNm, String userId, String userPw, String userEmail,  String userPhoneNum, String userImgUrl) {
        this.userNm = userNm;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userPhoneNum = userPhoneNum;
        this.userUseAt = "Y";
        this.userImgUrl = userImgUrl;
    }

    @Builder
    public UserDTO(String userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
