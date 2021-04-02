
package com.uos.suribank.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    //filter String
    String filter;

    public void setFilters(){
        String[] parse = filter.split(",");
        HashMap<String, List<Long>> parseMap =  new HashMap<>();
        parseMap.put("TYPE", new ArrayList<Long>());
        parseMap.put("SUBJECT", new ArrayList<Long>());
        parseMap.put("PROFESSOR", new ArrayList<Long>());

        for(String s : parse){
            String[] token = s.split(":");
            parseMap.get(token[0]).add(Long.parseLong(token[1]));
        }
        filters = new FilterDTO(parseMap.get("TYPE"),parseMap.get("SUBJECT"),parseMap.get("PROFESSOR"));
    }

    //Filter
    FilterDTO filters;
}