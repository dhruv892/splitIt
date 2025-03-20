
package com.dhruv892.SplitIt.controllers;

import com.dhruv892.SplitIt.dto.GroupDTO;
import com.dhruv892.SplitIt.dto.ResponseGroupDTO;
import com.dhruv892.SplitIt.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseGroupDTO createNewGroup(@RequestBody GroupDTO inputGroup) {
        System.out.println("-----------------------reached controller------------------------");
        return groupService.createNewGroup(inputGroup);
    }
    
}
