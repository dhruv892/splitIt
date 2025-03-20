package com.dhruv892.SplitIt.services;

import com.dhruv892.SplitIt.dto.GroupDTO;
import com.dhruv892.SplitIt.dto.ResponseGroupDTO;

import java.util.List;



public interface GroupService {

	ResponseGroupDTO createNewGroup(GroupDTO inputGroup);

}
