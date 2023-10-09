package com.sailotech.orderservice.persistence.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.sailotech.orderservice.persistence.entity.Order;

public interface OrderRepository extends ListCrudRepository<Order, Integer> {

}
