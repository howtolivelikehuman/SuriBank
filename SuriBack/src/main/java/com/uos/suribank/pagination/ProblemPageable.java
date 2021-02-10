package com.uos.suribank.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ProblemPageable {

    public static Pageable makePageable(PageableDTO page){
        Pageable pageable;
        
        //init DefaultValue
        setProbDefaultValue(page);
        if(page.getSort()== null){
            pageable = PageRequest.of(page.getPage(), page.getSize());
        }
        else{
            Sort sort = getSortOrders(page.getSort(), page.getOrder());
            pageable = PageRequest.of(page.getPage(), page.getSize(), sort);
        }
        return pageable;
    }

    public static void setProbDefaultValue(PageableDTO page){
        if(page.getPage() == null){
            page.setPage(0);
        }
        if(page.getSize() == null){
            page.setSize(20);
        }
        if(page.getSort().length() < 1){
            page.setSort("id");
        }
    }

    public static Sort getSortOrders(String sort, String order){
        try{
            if(order.equals("DESC")){
                return Sort.by(Sort.Direction.DESC, sort);
            }else{
                return Sort.by(Sort.Direction.ASC, sort);
            }
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sort error");
        }
    }
}
