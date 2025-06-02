package com.technicalchallenge.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String loginId;
    private boolean active;
    private int version;
    private LocalDateTime lastModifiedTimestamp;
}
