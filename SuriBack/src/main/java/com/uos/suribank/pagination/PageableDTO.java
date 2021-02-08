
package com.uos.suribank.pagination;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageableDTO {
    private Integer page;
    private Integer size;
    private Integer totalElements;
    private List<Pair<String, SortOption>> sorts;
    private List<String> filters;
}