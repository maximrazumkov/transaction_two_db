package ru.maxim.transaction.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxim.transaction.repositories.ClsChangesRepo;
import ru.maxim.transaction.repositories.RoomRepo;
import ru.maxim.transaction.services.TransactService;

@Service
public class SimpleTransactService implements TransactService {

    private final RoomRepo roomRepo;
    private final ClsChangesRepo clsChangesRepo;

    public SimpleTransactService(RoomRepo roomRepo, ClsChangesRepo clsChangesRepo) {
        this.roomRepo = roomRepo;
        this.clsChangesRepo = clsChangesRepo;
    }

    @Override
    @Transactional(value = "chainedTransactionManager")
    public void testTransact() {
        roomRepo.save("The First");
        roomRepo.save("The Second");
        clsChangesRepo.save("8", "The First");
        clsChangesRepo.save("9", "The Second");
        roomRepo.save("The Third");
    }
}
