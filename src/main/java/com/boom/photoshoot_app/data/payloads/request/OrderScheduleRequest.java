package com.boom.photoshoot_app.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class OrderScheduleRequest {
    @NotBlank
    @NotNull
    private LocalDateTime dateTime;

    public OrderScheduleRequest() {
    }

    public OrderScheduleRequest(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
