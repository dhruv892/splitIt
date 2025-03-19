package com.dhruv892.ShareIt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group_balances", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "group_id"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupBalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    Double balance;
}
