package com.example.pulsar_backend.service;

import com.example.pulsar_backend.dto.SearchResultDTO;

public interface IDashboardService {
    SearchResultDTO search(String searchTerm);
}
