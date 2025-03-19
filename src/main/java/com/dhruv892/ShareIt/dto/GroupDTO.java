package com.dhruv892.ShareIt.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long createdBy;
    private String name;
    private Set<String> members;
}
