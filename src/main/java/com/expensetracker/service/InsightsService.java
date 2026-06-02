package com.expensetracker.service;

import com.expensetracker.dto.DashboardSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Periodically logs a spending insight on a background timer.
 * Demonstrates @Scheduled (a recurring task) alongside the rest of the app.
 */
@Component
public class InsightsService {
    private static final Logger log = LoggerFactory.getLogger(InsightsService.class);

    private final DashboardService dashboardService;

    public InsightsService(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void logInsight() {
        try {
            DashboardSummary s = dashboardService.summary();
            log.info("[insight] {} spend so far: {} across {} transactions; top category: {}",
                    s.getMonth(), s.getSpentThisMonth(), s.getTransactionCount(), s.getTopCategory());
        } catch (Exception e) {
            log.warn("Insight task failed: {}", e.getMessage());
        }
    }
}
