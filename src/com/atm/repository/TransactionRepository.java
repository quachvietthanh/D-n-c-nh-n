package com.atm.repository;

import com.atm.entity.Transaction;

public class TransactionRepository extends BaseRepositoryImpl<Transaction> {

    public TransactionRepository() {
        super("data/transactions.dat", Transaction.class);
    }
}
