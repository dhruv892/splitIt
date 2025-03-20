package com.dhruv892.SplitIt.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long createdBy;
    private String name;
    private Set<String> members;
}
