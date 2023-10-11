package com.parking82.api.services;

import com.parking82.api.respository.PagamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class FinanceiroServices {

    private PagamentoRepository pagamentoRepository;

    public FinanceiroServices(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Double somaPagamentoDoDia(String data) {
        return pagamentoRepository.sumDayPayment(data);
    }

    public Double somaPorDatas(String dataInicial, String dataFinal) {
        return pagamentoRepository.sumAllDate(dataInicial, dataFinal);
    }

    public Double sumAll() {
        return pagamentoRepository.sumAll();
    }

}
