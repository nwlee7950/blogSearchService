package com.lsh.blogsearchservice.dto.kakao;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meta {
    private Integer total_count;
    private Integer pageable_count;
    private Boolean is_end;
}
