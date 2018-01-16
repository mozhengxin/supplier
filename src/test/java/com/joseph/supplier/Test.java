package com.joseph.supplier;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Test {

    public static void main(String[] args) {
        Vo vo = new Vo();
        PriorityQueue<String> queue = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });


    }

    public Vo getQuery(Vo vo) {
        Vo query = new Vo();
        if (!Objects.isNull(vo.getName())) {
            query.setName(vo.getName());
            return query;
        }
        if (!Objects.isNull(vo.getOrderId())) {
            query.setOrderId(vo.getOrderId());
            return query;
        }
        return null;
    }
}
