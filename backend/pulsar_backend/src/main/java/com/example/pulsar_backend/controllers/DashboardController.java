package com.example.pulsar_backend.controllers;

import com.example.pulsar_backend.dto.ApiResponse;
import com.example.pulsar_backend.dto.SearchResultDTO;
import com.example.pulsar_backend.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {
    
    private final IDashboardService dashboardService;
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<SearchResultDTO>> search(@RequestParam String search) {
        log.info("GET /v1/api/dashboard/search - Searching for: {}", search);
        SearchResultDTO results = dashboardService.search(search);
        log.info("GET /v1/api/dashboard/search - Found {} users, {} groups, {} songs", 
                results.getUserInfo().size(), 
                results.getGroupInfo().size(), 
                results.getSongInfo().size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Search completed successfully", results));
    }
}
