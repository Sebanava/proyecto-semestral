package com.arriendo.pago.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arriendo.pago.DTO.RentalDTO;

@FeignClient( name = "rental", url = "http://localhost:8083")
public interface RentalClient {


    @GetMapping("/rental/{id}")
    RentalDTO obtenerRental(@PathVariable long id);
                            
    


}
