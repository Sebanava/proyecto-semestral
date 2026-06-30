package com.arriendo.reportes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendo.reportes.Reportes;
import com.arriendo.reportes.DTO.ClienteDTO;
import com.arriendo.reportes.DTO.PagoDTO;
import com.arriendo.reportes.DTO.RentalDTO;
import com.arriendo.reportes.cliente.ClienteClient;
import com.arriendo.reportes.cliente.PagoClient;
import com.arriendo.reportes.cliente.RentalClient;
import com.arriendo.reportes.exception.ServicioNoDisponibleException;
import com.arriendo.reportes.model.ReportesModel;
import com.arriendo.reportes.repository.ReportesRepository;

@Service
public class ReportesService {

    @Autowired
    private ReportesRepository repository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private PagoClient pagoClient;

    @Autowired
    private RentalClient rentalClient;

    public List<Reportes> ObtenerTodo(){
        return repository.findAll();
    }

    public Reportes ObtenerPorId(Long id){
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    public Reportes guardar(ReportesModel model){
        Reportes reportes = new Reportes();
        reportes.setEstado(model.getEstado());
        reportes.setTipo(model.getTipo());
        reportes.setFecha(model.getFecha());
        return repository.save(reportes);
    }

    public Reportes actualizar(long id, ReportesModel model){
        Reportes reportes = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        reportes.setEstado(model.getEstado());
        reportes.setTipo(model.getTipo());
        reportes.setFecha(model.getFecha());
        return repository.save(reportes);
    }

    public void eliminar(long id){
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        repository.deleteById(id);
    }

    public String resumen(){
        List<ClienteDTO> clientes;
        try {
            clientes = clienteClient.ObtenerTodo();
        } catch (feign.FeignException e) {
            throw new ServicioNoDisponibleException(
                "El servicio de Clientes no está disponible. Intente más tarde.");
        }

        List<RentalDTO> rentals;
        try {
            rentals = rentalClient.ObtenerTodo();
        } catch (feign.FeignException e) {
            throw new ServicioNoDisponibleException(
                "El servicio de Arriendos no está disponible. Intente más tarde.");
        }

        List<PagoDTO> pagos;
        try {
            pagos = pagoClient.ObtenerTodo();
        } catch (feign.FeignException e) {
            throw new ServicioNoDisponibleException(
                "El servicio de Pagos no está disponible. Intente más tarde.");
        }

        long totalClientes = clientes.size();
        long totalArriendos = rentals.size();
        long arriendosActivos = rentals.stream()
            .filter(r -> r.getEstado().equals("activo"))
            .count();
        long pagosAprobados = pagos.stream()
            .filter(p -> p.getResultado().equals("aprobado"))
            .count();

        return "Total clientes: " + totalClientes +
               " | Total arriendos: " + totalArriendos +
               " | Arriendos activos: " + arriendosActivos +
               " | Pagos aprobados: " + pagosAprobados;
    }
}
