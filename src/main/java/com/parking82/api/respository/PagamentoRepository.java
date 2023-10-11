package com.parking82.api.respository;

import com.parking82.api.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT SUM(t.payment) FROM Payment t WHERE t.date = :data")
    Double sumDayPayment(String data);

    @Query("SELECT SUM(t.payment) FROM Payment t WHERE t.date BETWEEN ?1 AND ?2")
    Double sumAllDate(String startDate, String endDate);

    @Query("SELECT SUM(t.payment) FROM Payment t")
    Double sumAll();


}
