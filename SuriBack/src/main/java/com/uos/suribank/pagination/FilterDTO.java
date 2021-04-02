package com.uos.suribank.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDTO {
    List<Long> type;
    List<Long> subject;
    List<Long> professor;
}
