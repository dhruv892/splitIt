package com.dhruv892.ShareIt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_balances", uniqueConstraints = @UniqueConstraint(columnNames = {"user1", "user2"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1", nullable = false)
    private UserEntity user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2", nullable = false)
    private UserEntity user2;

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double balance;
}
