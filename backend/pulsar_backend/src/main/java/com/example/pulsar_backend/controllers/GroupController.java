package com.example.pulsar_backend.controllers;

import com.example.pulsar_backend.dto.ApiResponse;
import com.example.pulsar_backend.entity.GroupMasterEntity;
import com.example.pulsar_backend.entity.UserEntity;
import com.example.pulsar_backend.service.IGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/api/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {
    private final IGroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<GroupMasterEntity>> createGroup(
            @RequestParam String name,
            @RequestParam(required = false) String description) {
        log.info("POST /v1/api/group/create - Creating group: {}", name);
        GroupMasterEntity group = groupService.createGroup(name, description);
        log.info("POST /v1/api/group/create - Group created successfully with ID: {}", group.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildSuccessResponse("Group created successfully", group));
    }

    @PostMapping("/create/bulk")
    public ResponseEntity<ApiResponse<List<GroupMasterEntity>>> createGroupsBulk(@RequestBody List<GroupMasterEntity> groups) {
        log.info("POST /v1/api/group/create/bulk - Creating {} groups in bulk", groups.size());
        List<GroupMasterEntity> savedGroups = groupService.createGroupsBulk(groups);
        log.info("POST /v1/api/group/create/bulk - Successfully created {} groups", savedGroups.size());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildSuccessResponse("Groups created successfully", savedGroups));
    }

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse<String>> addUserToGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId) {
        log.info("POST /v1/api/group/addUser - Adding user {} to group {}", userId, groupId);
        String result = groupService.addUserToGroup(userId, groupId);
        log.info("POST /v1/api/group/addUser - {}", result);
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse(result, null));
    }

    @GetMapping("/{groupId}/users")
    public ResponseEntity<ApiResponse<Set<UserEntity>>> getGroupUsers(@PathVariable Long groupId) {
        log.info("GET /v1/api/group/{}/users - Fetching users for group", groupId);
        Set<UserEntity> users = groupService.getGroupUsers(groupId);
        log.info("GET /v1/api/group/{}/users - Found {} users", groupId, users.size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Users retrieved successfully", users));
    }

    @GetMapping("/user/{userId}/groups")
    public ResponseEntity<ApiResponse<Set<GroupMasterEntity>>> getUserGroups(@PathVariable Long userId) {
        log.info("GET /v1/api/group/user/{}/groups - Fetching groups for user", userId);
        Set<GroupMasterEntity> groups = groupService.getUserGroups(userId);
        log.info("GET /v1/api/group/user/{}/groups - Found {} groups", userId, groups.size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Groups retrieved successfully", groups));
    }
}
