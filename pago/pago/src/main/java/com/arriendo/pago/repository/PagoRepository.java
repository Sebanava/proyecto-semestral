package com.arriendo.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arriendo.pago.*;

@Repository
public interface PagoRepository extends JpaRepository <pago, Long>{

}
