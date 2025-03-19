package com.dhruv892.ShareIt.services;


import com.dhruv892.ShareIt.dto.GroupDTO;
import com.dhruv892.ShareIt.dto.ResponseGroupDTO;
import com.dhruv892.ShareIt.entities.GroupEntity;
import com.dhruv892.ShareIt.entities.UserEntity;
import com.dhruv892.ShareIt.exceptions.ResourceNotFoundException;
import com.dhruv892.ShareIt.repository.GroupRepository;
import com.dhruv892.ShareIt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
//    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ResponseGroupDTO createNewGroup(GroupDTO inputGroup){
        System.out.println("-----------------------reached Service------------------------");
        System.out.println("-----------------------input group------------------------");
        System.out.println(inputGroup.toString());
        UserEntity createdBy = userRepository.findById(inputGroup.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Set<UserEntity> members = new HashSet<>();
        if (inputGroup.getMembers() != null && !inputGroup.getMembers().isEmpty()) {
            members = inputGroup.getMembers().stream()
                    .map(email -> userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("User not found: " + email)))
                    .collect(Collectors.toSet());
        }
        members.add(createdBy);
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCreatedBy(createdBy);
        groupEntity.setName(inputGroup.getName());
        groupEntity.setMembers(members);
        GroupEntity savedGroupEntity = groupRepository.save(groupEntity);

        //GroupEntity groupEntity = modelMapper.map(inputGroup, GroupEntity.class);
        ResponseGroupDTO responseGroupDTO = new ResponseGroupDTO();
        responseGroupDTO.setName(savedGroupEntity.getName());
        responseGroupDTO.setMembers(savedGroupEntity.getMembers().stream()
                .map(UserEntity::getEmail)
                .collect(Collectors.toSet())
        );
        return responseGroupDTO;
    }

}

