
package com.dhruv892.ShareIt.controllers;

import com.dhruv892.ShareIt.dto.GroupDTO;
import com.dhruv892.ShareIt.dto.ResponseGroupDTO;
import com.dhruv892.ShareIt.services.GroupService;
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
