package com.seungmoo.backend.domain.aggregates.common;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    @NotAudited
    private String createdBy = "SYSTEM";

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @NotAudited
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    @NotAudited
    private String updatedBy = "SYSTEM";

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    @NotAudited
    private LocalDateTime updatedAt;

    @Column(name = "deleted_by")
    @NotAudited
    private String deletedBy;

    @Column(name = "deleted_at")
    @NotAudited
    private LocalDateTime deletedAt;
}
