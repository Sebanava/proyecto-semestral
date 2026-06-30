package com.arriendo.reportes.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.arriendo.reportes.DTO.RentalDTO;

@FeignClient(name = "RENTAL")

public interface RentalClient {

        @GetMapping("/rental")
        List<RentalDTO>ObtenerTodo();


}
