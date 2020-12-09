package ru.maxim.transaction.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim.transaction.services.TransactService;

@RestController
public class TransactController {

    private final TransactService transactService;

    public TransactController(TransactService transactService) {
        this.transactService = transactService;
    }

    @GetMapping("/")
    public void transactTest() {
        transactService.testTransact();
    }
}
