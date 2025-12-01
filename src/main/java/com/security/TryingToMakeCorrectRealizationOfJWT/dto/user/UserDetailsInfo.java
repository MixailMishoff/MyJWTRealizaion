package com.security.TryingToMakeCorrectRealizationOfJWT.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsInfo {
    private UserDetailsInfoHeader userDetailsInfoHeader;
    private UserDetailsInfoPayload userDetailsInfoPayload;
    private String token;
}
