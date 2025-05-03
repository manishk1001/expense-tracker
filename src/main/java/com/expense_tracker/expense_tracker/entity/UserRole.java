package com.expense_tracker.expense_tracker.entity;

import com.expense_tracker.expense_tracker.enums.UserRoleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userroleid", nullable = false)
    private Long userRoleId;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "roleid")
    private Role role;

    @CreationTimestamp
    @Column(name = "createdby" ,nullable = false)
    private LocalDateTime createdBy;

    @UpdateTimestamp
    @Column(name = "updatedby" ,nullable = false)
    private LocalDateTime updatedBy;


    @Column(name = "status")
    private UserRoleStatus status;

}
