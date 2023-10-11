package com.techscript.spot82.controller;

import com.techscript.spot82.services.FinanceiroServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financeiro")
public class FinancierController {

    private FinanceiroServices financeiroServices;

    public FinancierController(FinanceiroServices financeiroServices) {
        this.financeiroServices = financeiroServices;
    }

    @GetMapping("/dia")
    public Double today(@RequestParam(name = "data") String data) {
        return financeiroServices.somaPagamentoDoDia(data);
    }

    @GetMapping("/personalizado")
    public Double sumAllDate(@RequestParam(name = "start") String startDate, @RequestParam(name = "end") String endDate) {
        return financeiroServices.somaPorDatas(startDate, endDate);
    }

    @GetMapping("/todo-periodo")
    public Double sumAll() {
        return financeiroServices.sumAll();
    }
}
