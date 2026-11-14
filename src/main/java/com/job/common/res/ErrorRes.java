package com.job.common.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorRes {

    private final String code;
    private final String message;
    @Builder.Default
    private final List<String> details = List.of();
}
