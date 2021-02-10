
package com.uos.suribank.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO {
    private Integer page = 0;
    private Integer size = 20;

    //sort
    String sort;
    //ASC or DESC
    String order;

    //filter
    String type;
    String value;
}