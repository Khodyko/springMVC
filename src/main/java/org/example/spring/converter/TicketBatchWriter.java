package org.example.spring.converter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.dao.daoImpl.TicketDaoImpl;
import org.example.spring.model.Entity.TicketEntity;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.TransactionAwareProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;

/**
 * Used for writing data into DataBase using Transaction(TicketBatchWriter bean).
 *
 * @author Igor Khodyko
 */
public class TicketBatchWriter implements ItemWriter<TicketEntity> {
    List<TicketEntity> output = TransactionAwareProxyFactory.createTransactionalList();
    private TicketDaoImpl ticketDaoImpl;
    private final static Logger logger = LogManager.getLogger(TicketBatchWriter.class.getName());

    public TicketBatchWriter() {
        logger.log(DEBUG, "created");
    }

    @Autowired
    public TicketBatchWriter(TicketDaoImpl ticketDaoImpl) {
        this.ticketDaoImpl = ticketDaoImpl;
        logger.log(DEBUG, "created");
    }

    /**
     * Replace Ticket Data in DataBase by data from list.
     *
     * @param list list of Tickets for replacing
     */
    @Override
    public void write(List<? extends TicketEntity> list) {
        logger.log(DEBUG, "started.");
        ticketDaoImpl.replaceTickets((List<TicketEntity>) list);
        output.addAll(list);
    }
}
