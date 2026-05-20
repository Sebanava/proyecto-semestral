package com.arriendo.reportes.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.arriendo.reportes.DTO.RentalDTO;

@FeignClient(name = "rental", url = "http://localhost:8083" )

public interface RentalClient {

        @GetMapping("/rental")
        List<RentalDTO>ObtenerTodo();


}
