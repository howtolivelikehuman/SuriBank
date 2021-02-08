package com.uos.suribank.pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;

public class DomainSpec<T extends Enum<T>, U> {
    private final Class<T> clazz;
    @Getter @Setter
    private int defaultPage = 0;
    @Getter @Setter
    private int defaultSize = 20;
    private SortStrategy<T> sortStrategy;


    public DomainSpec(Class<T> clazz, SortStrategy<T> sortStrategy) {
        this.clazz = clazz;
        this.sortStrategy = sortStrategy;
    }

    public DomainSpec(Class<T> clazz, SortStrategy<T> sortStrategy, int defaultPage, int defaultSize) {
        this.clazz = clazz;
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.sortStrategy = sortStrategy;
    }

    public void changeSortStrategy(SortStrategy<T> sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public Pageable getPageable(PageableDTO dto) {
        Integer page = dto.getPage();
        Integer size = dto.getSize();
        Pageable pageable;

        if (dto.getSorts().size() < 1) {
            pageable = PageRequest.of(page == null ? defaultPage : page, size == null ? defaultSize : size);
        } else {
            List<Sort.Order> sorts = getSortOrders(dto.getSorts());
            pageable = PageRequest.of(page == null ? defaultPage : page, size == null ? defaultSize : size, Sort.by(sorts));
        }

        return pageable;
    }

    private List<Sort.Order> getSortOrders(List<Pair<String, SortOption>> sorts) {
        Assert.notNull(this.sortStrategy,"There is no sort strategy");

        List<Sort.Order> orders = new ArrayList<>();
        for (var o : sorts) {
            T column;
            try {
                column = Enum.valueOf(this.clazz, o.getFirst().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Sort option error");
            }

            final Sort.Order order = sortStrategy.getSortOrder(column, o.getSecond());
            Assert.notNull(order, "sort option error");

            orders.add(order);
        }
        return orders;
    }
}