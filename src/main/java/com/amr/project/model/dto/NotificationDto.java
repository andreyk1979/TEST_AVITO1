package com.amr.project.model.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long chatId;
    private String fromUserName;
    private String toUserName;

    public NotificationDto() {
    }

    public NotificationDto(String fromUserName) {
        this.fromUserName = fromUserName;
    }
}
