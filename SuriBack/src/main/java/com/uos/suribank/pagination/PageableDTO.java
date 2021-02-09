
package com.uos.suribank.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO {
    String type;
    String value;
    Integer page;
    Integer size;
    Integer totalElements;
    List<Pair<String, SortOption>> sorts;
    List<String> filters;
}