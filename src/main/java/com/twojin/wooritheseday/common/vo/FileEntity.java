package com.twojin.wooritheseday.common.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * MappedSuperclass :: DB는 따로 쓰고, 객체 입장으로서의 속성만 상속 받아서 사용할 때
 * 그냥 extends로 상속받을 경우, 상속하는 객체로 db가 생성되며 하나로서 관리 됨
 */
@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    protected String fileName;

    protected String filePath;

    @Column(nullable = false)
    @NotBlank
    protected String fileExtension;


    @CreationTimestamp
    protected Date createDt;

    @UpdateTimestamp
    protected  Date updateDt;


}
