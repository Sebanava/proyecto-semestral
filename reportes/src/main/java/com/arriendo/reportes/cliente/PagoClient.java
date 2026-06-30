package com.arriendo.reportes.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.arriendo.reportes.DTO.PagoDTO;

@FeignClient(name = "PAGO")
public interface PagoClient {

    @GetMapping("/pago")
    List<PagoDTO>ObtenerTodo();
}
