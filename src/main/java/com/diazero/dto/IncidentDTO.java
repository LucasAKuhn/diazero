package com.diazero.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime closedAt;

}
