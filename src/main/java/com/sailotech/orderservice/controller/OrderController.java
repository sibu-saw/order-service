package com.sailotech.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailotech.orderservice.business.OrderService;
import com.sailotech.orderservice.dto.OrderDto;

@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("{orderId}")
	public ResponseEntity<OrderDto> getById(@PathVariable int orderId) {
		
		OrderDto orderDto = orderService.getOrderById(orderId);
		if(orderDto == null)
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity(orderDto, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public ResponseEntity<OrderDto> getById(@RequestBody OrderDto orderDto) {
		OrderDto updatedOrderDto = orderService.createOrder(orderDto);
		
		if(updatedOrderDto == null)
			return new ResponseEntity(null, HttpStatus.CONFLICT);
		
		return new ResponseEntity(updatedOrderDto, HttpStatus.CREATED);
	}
}
