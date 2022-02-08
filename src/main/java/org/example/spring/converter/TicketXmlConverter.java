package org.example.spring.converter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.model.Entity.TicketEntity;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

/**
 * Read xml file.
 *
 * @author Igor Khodyko
 */
public class TicketXmlConverter implements ItemReader {
    List<TicketEntity> list = null;
    private Boolean batchJobState = false;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    @Value("${ticket.preload.xml.path}")
    private String filePath;
    private final static Logger logger = LogManager.getLogger(TicketXmlConverter.class.getName());

    public TicketXmlConverter() {
        logger.log(DEBUG, "created");
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    /**
     * Reads xml-file and converts it to list. Then returns one object in one call of method
     * till list become empty.
     * @return
     * @throws IOException
     */
    @Override
    public Object read() throws IOException {
        logger.log(DEBUG, "started.");
        if (!batchJobState) {
            try (FileInputStream is = new FileInputStream(filePath)) {
                list = ((TicketEntities) this.unmarshaller.unmarshal(new StreamSource(is))).getTicketEntitiesList();
                batchJobState = true;
                logger.log(DEBUG, "list of ticketEntities succesfully created");
            }
        }
        if (!list.isEmpty()) {
            return list.remove(0);
        }
        return null;
    }
}
