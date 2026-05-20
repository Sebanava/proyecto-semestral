package com.arriendo.reportes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arriendo.reportes.Reportes;

@Repository
public interface ReportesRepository extends JpaRepository<Reportes, Long>{


}
