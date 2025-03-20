package com.dhruv892.SplitIt.dto;
import lombok.Data;

import java.util.Set;

@Data
public class ResponseGroupDTO {

    private String name;
    private Set<String> members;
}
