package org.example.spring.converter;


import com.lowagie.text.*;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.spring.model.Ticket;

import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;

/**
 * Converts Ticket data into PDF-file.
 *
 * @author Igor Khodyko
 */
public class TicketPDFExporter implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static Logger logger = LogManager.getLogger(TicketPDFExporter.class.getName());


    public TicketPDFExporter() {
        logger.log(DEBUG, "created");
    }

    /**
     * Build pdf table.
     *
     * @param table
     */
    private void writeTableHeader(PdfPTable table) {
        logger.log(DEBUG, "started.");
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Ticket ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Event Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("User ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Category", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Place", font));
        table.addCell(cell);
    }

    /**
     * Add data from list of ticket to pdf-table.
     *
     * @param table
     * @param listTicket
     */
    private void writeTableData(PdfPTable table, List<Ticket> listTicket) {
        logger.log(DEBUG, "started.");
        for (Ticket ticket : listTicket) {
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(String.valueOf(ticket.getEventId()));
            table.addCell(String.valueOf(ticket.getUserId()));
            table.addCell(String.valueOf(ticket.getCategory()));
            table.addCell(String.valueOf(ticket.getPlace()));
        }
    }

    /**
     * Export pdf file to client.
     *
     * @param response
     * @param listTicket
     * @throws DocumentException
     * @throws IOException
     */
    public void export(HttpServletResponse response, List<Ticket> listTicket) throws DocumentException, IOException {
        logger.log(DEBUG, "started.");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("List of Tickets", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table, listTicket);
        document.add(table);
        document.close();
    }
}
