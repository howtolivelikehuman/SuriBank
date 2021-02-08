package com.uos.suribank.pagination;

import lombok.Builder;
import lombok.Data;
import lombok.AccessLevel;

@Builder(access = AccessLevel.PRIVATE)
@Data
public class Pair<T, U> {
    private T first;
    private U second;

    public static <T, U> Pair of(T first, U second) {
        return Pair.builder()
                .first(first)
                .second(second)
                .build();
    }
}
