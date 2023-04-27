package com.seungmoo.backend.domain.aggregates.user;

import com.seungmoo.backend.domain.aggregates.common.BaseEntity;
import com.seungmoo.backend.domain.constants.RoleType;
import com.seungmoo.backend.domain.converters.RoleTypeListConverter;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Audited
@SQLDelete(sql = "UPDATE user SET deleted_at = current_timestamp WHERE user_id = ?")
@Where(clause = "deleted_at is null")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Convert(converter = RoleTypeListConverter.class)
    @Column(name = "roles")
    private List<RoleType> roles;

}
