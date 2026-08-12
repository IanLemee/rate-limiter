package com.tech.ian.tokenbucket.service.ex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS, reason = "User send too many requests wait")
public class RateLimitExceededException extends RuntimeException {
}
