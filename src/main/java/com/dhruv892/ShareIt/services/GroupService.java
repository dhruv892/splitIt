package com.dhruv892.ShareIt.services;

import com.dhruv892.ShareIt.dto.GroupDTO;
import com.dhruv892.ShareIt.dto.ResponseGroupDTO;

import java.util.List;



public interface GroupService {

	ResponseGroupDTO createNewGroup(GroupDTO inputGroup);

}
