package com.arriendo.reportes.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.arriendo.reportes.DTO.PagoDTO;

@FeignClient(name = "pago", url = "http://localhost:8084")
public interface PagoClient {

    @GetMapping("/pago")
    List<PagoDTO>ObtenerTodo();
}
