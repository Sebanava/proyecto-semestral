package com.arriendo.pago.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.arriendo.pago.DTO.RentalDTO;

@FeignClient(name = "RENTAL")
public interface RentalClient {

    @GetMapping("/rental/{id}")
    RentalDTO obtenerRental(@PathVariable long id);

    @PutMapping("/rental/{id}/pagar")
    void pagarRental(@PathVariable long id);

}
