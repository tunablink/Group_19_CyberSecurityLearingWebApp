package com.example.blog_web.compete.domain;

/**
 * Idempotent week-close pipeline: only {@link #OPEN} weeks are picked up by the scheduler.
 */
public enum CompeteWeekCloseStatus {
    OPEN,
    CLOSING,
    CLOSED
}
