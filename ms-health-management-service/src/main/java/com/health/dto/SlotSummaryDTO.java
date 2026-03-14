package com.health.dto;

import java.time.LocalDate;

public class SlotSummaryDTO {
    private LocalDate slotDate;
    private long totalSlots;
    private long available;
    private long leave;

    // Constructors
    public SlotSummaryDTO(LocalDate slotDate, long totalSlots, long available, long leave) {
        this.slotDate = slotDate;
        this.totalSlots = totalSlots;
        this.available = available;
        this.leave = leave;
    }

    // Getters & Setters
    public LocalDate getSlotDate() { return slotDate; }
    public void setSlotDate(LocalDate slotDate) { this.slotDate = slotDate; }

    public long getTotalSlots() { return totalSlots; }
    public void setTotalSlots(long totalSlots) { this.totalSlots = totalSlots; }

    public long getAvailable() { return available; }
    public void setAvailable(long available) { this.available = available; }

    public long getLeave() { return leave; }
    public void setLeave(long leave) { this.leave = leave; }
}
