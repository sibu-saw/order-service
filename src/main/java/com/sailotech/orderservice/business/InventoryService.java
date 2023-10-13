package com.sailotech.orderservice.business;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sailotech.orderservice.dto.InventoryDto;

@FeignClient("api-gateway")
public interface InventoryService {

	@GetMapping("api/inventory/{skuCode}") 
	public InventoryDto getInventoryBySkuCode(@PathVariable String skuCode);
}
