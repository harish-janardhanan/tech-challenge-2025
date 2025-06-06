package com.technicalchallenge.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String loginId;
    private boolean active;
    private int version;
    private LocalDateTime lastModifiedTimestamp;
    private String userProfile;
}
