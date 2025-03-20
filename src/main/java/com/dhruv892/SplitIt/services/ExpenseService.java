package com.dhruv892.SplitIt.services;

import com.dhruv892.SplitIt.dto.ExpenseDTO;
import com.dhruv892.SplitIt.dto.ExpenseResponseDTO;
import com.dhruv892.SplitIt.entities.*;
import com.dhruv892.SplitIt.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final UserBalanceRepository userBalanceRepository;
    private final GroupBalanceRepository groupBalanceRepository;
    private final GroupRepository groupRepository;

//    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, BalanceRepository balanceRepository) {
//        this.expenseRepository = expenseRepository;
//        this.userRepository = userRepository;
//        this.balanceRepository = balanceRepository;
//    }

    @Transactional
    public ExpenseResponseDTO splitService(Set<UserEntity> members, Double amount, UserEntity paidBy, Long groupId) {

        System.out.println("-----------inside splitService-------------");
        int groupSize = members.size();
        Double shareAmount = amount / groupSize;
        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO();

        expenseResponseDTO.getUserGets().put(paidBy.getEmail(), amount - shareAmount);

        for (UserEntity member : members) {

//--------------------------------------update user balance-----------------------------------
            if(!member.equals(paidBy)){
                UserEntity user1 = (paidBy.getId() < member.getId()) ? paidBy : member;
                UserEntity user2 = (paidBy.getId() > member.getId()) ? paidBy : member;


                userBalanceRepository.findByUser1AndUser2(user1, user2)
                        .ifPresentOrElse(balance -> {
                                    balance.setBalance(balance.getBalance() + (paidBy.equals(user1) ? shareAmount : -shareAmount));
                                    userBalanceRepository.save(balance);
                                },
                                () -> {
                                    UserBalanceEntity newBalance = new UserBalanceEntity();
                                    newBalance.setUser1(user1);
                                    newBalance.setUser2(user2);
                                    newBalance.setBalance(paidBy.equals(user1) ? shareAmount : -shareAmount);
                                    userBalanceRepository.save(newBalance);
                                }
                        );
                expenseResponseDTO.getUserGets().put(member.getEmail(), -shareAmount);
            }

//--------------------------------------update group balance-----------------------------------
            if(groupId != null){
                GroupEntity group = groupRepository.findById(groupId)
                        .orElseThrow(() -> new RuntimeException("Group not found"));
                groupBalanceRepository.findByUserAndGroup(member, group)
                        .ifPresentOrElse(balance -> {
                                    double newBalance = balance.getBalance() + (member.equals(paidBy) ? amount - shareAmount : -shareAmount);
                                    balance.setBalance(newBalance);
                                    groupBalanceRepository.save(balance);
                                },
                                () -> {
                                    GroupBalanceEntity newBalance = new GroupBalanceEntity();
                                    newBalance.setUser(member);
                                    newBalance.setGroup(group);
                                    newBalance.setBalance(member.equals(paidBy) ? amount - shareAmount : -shareAmount);
                                    groupBalanceRepository.save(newBalance);
                                });
            }

        }

        System.out.println("--------------------split Service end--------------");
        System.out.println(expenseResponseDTO);

        return expenseResponseDTO;
    }

    @Transactional
    public ExpenseResponseDTO addExpense(ExpenseDTO inputExpense) {
        System.out.println("------------------inside addExpense service--------------------");
        //---------------------mapping to ExpenseEntity-----------------------------------------
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setAmount(inputExpense.getAmount());
        expenseEntity.setDescription(inputExpense.getDescription());
        expenseEntity.setGroupId(inputExpense.getGroupId());

        UserEntity paidBy = userRepository.findById(inputExpense.getPaidBy())
                .orElseThrow(() -> new RuntimeException("User not found: " + inputExpense.getPaidBy()));

        expenseEntity.setPaidBy(paidBy);

//        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);

        Set<UserEntity> members = new HashSet<>();


        if(inputExpense.getMembers() != null) {
            System.out.println("------------------inside if--------------------");
            members = inputExpense.getMembers().stream()
                    .map(email -> userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("User not found: " + email)))
                    .collect(Collectors.toSet());
        }
        members.add(paidBy);
        System.out.println("------------------members--------------------");
        System.out.println(members.toString());
        expenseEntity.setMembers(members);


        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);



        System.out.println("-------------------saved Expense--------------------");

        return splitService(savedExpense.getMembers(), savedExpense.getAmount(), savedExpense.getPaidBy(), savedExpense.getGroupId());
    }
}
