package com.sailotech.orderservice.business;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sailotech.orderservice.dto.InventoryDto;
import com.sailotech.orderservice.dto.OrderDto;
import com.sailotech.orderservice.persistence.entity.Order;
import com.sailotech.orderservice.persistence.repository.OrderRepository;

@Service
public class OrderService {

	@Value("${inventory.service.url}")
	private String inventoryServiceUrl;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public OrderDto getOrderById(int orderId) {
		Optional<Order> orderEntity = orderRepository.findById(orderId);
		
		if(orderEntity.isPresent())
			return modelMapper.map(orderEntity.get(), OrderDto.class);
		
		return null;
	}


	public OrderDto createOrder(OrderDto orderDto) {
		
		/*
		InventoryDto inventoryDto = WebClient.create(inventoryServiceUrl+"/"+orderDto.getSkuCode())
		.get()
		.retrieve()
		.bodyToMono(InventoryDto.class)
		.block();
		*/
		
		InventoryDto inventoryDto = inventoryService.getInventoryBySkuCode(orderDto.getSkuCode());
		
		if(orderDto.getQuantity() >= inventoryDto.getAvailableQuantity())
			return null;
		
		Order orderEntity = modelMapper.map(orderDto, Order.class);
		orderEntity = orderRepository.save(orderEntity);
		
		return modelMapper.map(orderEntity, OrderDto.class);
	}
}
