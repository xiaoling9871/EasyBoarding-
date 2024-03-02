package com.example.authserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_description")
    private String roleDescription;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date createDate;

    @Column(name = "last_modified_date")
    @UpdateTimestamp
    private Date lastModifiedDate;

    @Column(name = "active_flag")
    private Boolean activeFlag;
}
