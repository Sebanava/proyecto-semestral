package com.arriendo.pago.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendo.pago.pago;
import com.arriendo.pago.DTO.RentalDTO;
import com.arriendo.pago.client.RentalClient;
import com.arriendo.pago.model.PagoModel;
import com.arriendo.pago.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private RentalClient rentalClient;

    public String procesarPago(PagoModel model) {

        // Validar número de tarjeta (16 dígitos)
        if (model.getNumero_tarjeta().length() != 16) {
            return "Forma de pago inválida: número de tarjeta incorrecto";
        }

        // Validar CVV (3 dígitos)
        if (model.getCvv().length() != 3) {
            return "Forma de pago inválida: CVV incorrecto";
        }

        // Consultar el arriendo en rental
        RentalDTO rental = rentalClient.obtenerRental(model.getId_rental());

        // Guardar el registro del pago
        pago registro = new pago();
        registro.setId_rental(model.getId_rental());
        registro.setResultado("aprobado");
        registro.setFecha(LocalDate.now());
        repository.save(registro);

        return "Pago aprobado por $" + rental.getMonto();
    }
        public List<pago> obtenerTodo(){
        return repository.findAll();
    }
}