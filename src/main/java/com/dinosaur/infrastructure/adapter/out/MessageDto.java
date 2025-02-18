package com.dinosaur.infrastructure.adapter.out;

import com.dinosaur.domain.Status;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class MessageDto {
    private Long dinosaurId;
    private Status newStatus;
    private String timestamp;
}
