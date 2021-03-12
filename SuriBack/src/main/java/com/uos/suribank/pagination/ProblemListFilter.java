package com.uos.suribank.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemListFilter {
    String type;
    String[] professor;
    Long[] subject;
}
