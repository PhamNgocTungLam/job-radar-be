package com.job.common.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRes<T> {

    private final T data;
    private final ErrorRes error;

    private BaseRes(T data, ErrorRes error) {
        this.data = data;
        this.error = error;
    }

    public static <T> BaseRes<T> ok(T data) {
        return new BaseRes<>(data, null);
    }

    public static BaseRes<Void> ok() {
        return new BaseRes<>(null, null);
    }

    public static BaseRes<Void> fail(ErrorRes error) {
        return new BaseRes<>(null, error);
    }
}
