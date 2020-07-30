package com.hungtin.tako.takobackend.auth.http;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RefreshTokenGenReq {

  private String refreshToken;
}
