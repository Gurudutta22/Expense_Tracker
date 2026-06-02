package com.expensetracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Writes audit log entries on a background thread so the user's request returns immediately.
 * Demonstrates @Async (fire-and-forget concurrency) without blocking the main flow.
 */
@Service
public class AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    @Async
    public void recordAsync(String message) {
        log.info("[audit] {} (thread: {})", message, Thread.currentThread().getName());
    }
}
