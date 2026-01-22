package com.beyond.basic.b2_board.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
//기본적으로는 Entity는 상속이 불가능한 구조. MappedSuperclass 어노테이션 사용시 상속관계 가능
@MappedSuperclass
@Getter
public class BassTimeEntity {
    @CreationTimestamp
    private LocalDateTime createdTime ;
    @UpdateTimestamp
    private LocalDateTime updatedTime ;
}
