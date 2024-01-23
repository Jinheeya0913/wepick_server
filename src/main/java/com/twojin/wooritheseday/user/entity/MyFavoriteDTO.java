package com.twojin.wooritheseday.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twojin.wooritheseday.common.enums.ProductClass;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "woori_user_favorite_cart")
public class MyFavoriteDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long favoriteRegCd;

    private Long productCd;

    private String userId;

    @Enumerated(EnumType.STRING)
    private ProductClass productClass;

    private String productName;

    @Column(nullable = false)
    private String productSubName;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    private Date regDate;








}
