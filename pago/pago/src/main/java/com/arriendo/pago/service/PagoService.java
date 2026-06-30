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

        if (model.getNumero_tarjeta().length() != 16) {
            throw new RuntimeException("Forma de pago inválida: número de tarjeta debe tener 16 dígitos");
        }

        if (model.getCvv().length() != 3) {
            throw new RuntimeException("Forma de pago inválida: CVV debe tener 3 dígitos");
        }

        RentalDTO rental = rentalClient.obtenerRental(model.getId_rental());

        if ("pagado".equals(rental.getEstado())) {
            throw new RuntimeException("Este arriendo ya fue pagado");
        }

        pago registro = new pago();
        registro.setId_rental(model.getId_rental());
        registro.setResultado("aprobado");
        registro.setFecha(LocalDate.now());
        repository.save(registro);

        rentalClient.pagarRental(model.getId_rental());

        return "Pago aprobado por $" + rental.getMonto();
    }
    public List<pago> obtenerTodo(){
        return repository.findAll();
    }

    public pago obtenerPorId(Long id){
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public pago actualizar(Long id, pago pagoActualizado){
        pago pagoExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pagoExistente.setResultado(pagoActualizado.getResultado());
        pagoExistente.setFecha(pagoActualizado.getFecha());
        return repository.save(pagoExistente);
    }

    public void eliminar(Long id){
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        repository.deleteById(id);
    }
}