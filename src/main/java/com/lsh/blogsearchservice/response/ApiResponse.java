package com.lsh.blogsearchservice.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private boolean result;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<?> successWithNoContent() {
        return new ApiResponse<>(true, null, null);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(false, null, message);
    }

    private ApiResponse(boolean result, T data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }
}