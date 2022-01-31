package org.example.spring.converter;


import org.example.spring.dao.daoImpl.TicketDaoImpl;
import org.example.spring.model.Entity.TicketEntity;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.TransactionAwareProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TicketBatchWriter implements ItemWriter<TicketEntity> {
    List<TicketEntity> output = TransactionAwareProxyFactory.createTransactionalList();
    private TicketDaoImpl ticketDaoImpl;

    @Autowired
    public TicketBatchWriter(TicketDaoImpl ticketDaoImpl) {
        this.ticketDaoImpl = ticketDaoImpl;
    }

    @Override
    public void write(List<? extends TicketEntity> list) throws Exception {
        System.out.println("writer: list = " + list);
        ticketDaoImpl.replaceTickets((List<TicketEntity>) list);
        output.addAll(list);
    }


}
