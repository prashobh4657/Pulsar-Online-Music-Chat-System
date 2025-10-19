package com.example.pulsar_backend.controllers;

import com.example.pulsar_backend.dto.ApiResponse;
import com.example.pulsar_backend.dto.FriendListResponseDTO;
import com.example.pulsar_backend.entity.FriendEntity;
import com.example.pulsar_backend.service.IFriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/friends")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final IFriendService friendService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<FriendEntity>> sendRequest(@RequestParam Long userId, @RequestParam Long friendId) {
        log.info("POST /v1/api/friends/request - User {} sending friend request to user {}", userId, friendId);
        FriendEntity friendRequest = friendService.sendRequest(userId, friendId);
        log.info("POST /v1/api/friends/request - Friend request sent successfully with ID: {}", friendRequest.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildSuccessResponse("Friend request sent successfully", friendRequest));
    }

    @PostMapping("/request/accept")
    public ResponseEntity<ApiResponse<FriendEntity>> acceptRequest(@RequestParam Long userId, @RequestParam Long friendId) {
        log.info("POST /v1/api/friends/request/accept - User {} accepting friend request from user {}", userId, friendId);
        FriendEntity acceptedRequest = friendService.acceptRequest(userId, friendId);
        log.info("POST /v1/api/friends/request/accept - Friend request accepted successfully");
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Friend request accepted", acceptedRequest));
    }

    @PostMapping("/request/reject")
    public ResponseEntity<ApiResponse<Void>> rejectRequest(@RequestParam Long userId, @RequestParam Long friendId) {
        log.info("POST /v1/api/friends/request/reject - User {} rejecting friend request from user {}", userId, friendId);
        friendService.rejectRequest(userId, friendId);
        log.info("POST /v1/api/friends/request/reject - Friend request rejected successfully");
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Friend request rejected", null));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<FriendEntity>>> listFriends(@RequestParam Long userId) {
        log.info("GET /v1/api/friends/list - Fetching friends list for user {}", userId);
        List<FriendEntity> friends = friendService.listFriends(userId);
        log.info("GET /v1/api/friends/list - Successfully retrieved {} friends for user {}", friends.size(), userId);
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Friends retrieved successfully", friends));
    }

    @GetMapping("/list/details")
    public ResponseEntity<ApiResponse<List<FriendListResponseDTO>>> getFriendsList(@RequestParam Long userId) {
        log.info("GET /v1/api/friends/list/details - Fetching friends list with details for user {}", userId);
        List<FriendListResponseDTO> friendsList = friendService.getFriendsList(userId);
        log.info("GET /v1/api/friends/list/details - Successfully retrieved {} friends with details", friendsList.size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Friends list retrieved successfully", friendsList));
    }

    @GetMapping("/requests/pending")
    public ResponseEntity<ApiResponse<List<FriendEntity>>> listPendingRequests(@RequestParam Long userId) {
        log.info("GET /v1/api/friends/requests/pending - Fetching pending requests for user {}", userId);
        List<FriendEntity> pendingRequests = friendService.listPendingRequests(userId);
        log.info("GET /v1/api/friends/requests/pending - Successfully retrieved {} pending requests", pendingRequests.size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Pending requests retrieved successfully", pendingRequests));
    }

}
