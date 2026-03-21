package com.health.dto.response;

import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotSummary {

    private Long id;
    private LocalDate slotDate;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;
    private String status; // AVAILABLE, BREAK, LUNCH, LEAVE, BOOKED


    public SlotSummary(Long id, LocalDate slotDate, LocalTime slotStartTime, LocalTime slotEndTime, String status) {
        this.id = id;
        this.slotDate = slotDate;
        this.slotStartTime = slotStartTime;
        this.slotEndTime = slotEndTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
    }

    public LocalTime getSlotStartTime() {
        return slotStartTime;
    }

    public void setSlotStartTime(LocalTime slotStartTime) {
        this.slotStartTime = slotStartTime;
    }

    public LocalTime getSlotEndTime() {
        return slotEndTime;
    }

    public void setSlotEndTime(LocalTime slotEndTime) {
        this.slotEndTime = slotEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
