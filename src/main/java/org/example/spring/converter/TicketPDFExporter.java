package org.example.spring.converter;



import com.lowagie.text.*;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import org.example.spring.model.Ticket;


import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class TicketPDFExporter implements Serializable {
    private static final long serialVersionUID = 1L;



    public TicketPDFExporter() {
    }



    private void writeTableHeader(PdfPTable table) {
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

    private void writeTableData(PdfPTable table, List<Ticket> listTicket) {
        for (Ticket ticket : listTicket) {
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(String.valueOf(ticket.getEventId()));
            table.addCell(String.valueOf(ticket.getUserId()));
            table.addCell(String.valueOf(ticket.getCategory()));
            table.addCell(String.valueOf(ticket.getPlace()));
        }
    }
    public void export(HttpServletResponse response, List<Ticket> listTicket) throws DocumentException, IOException {
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
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, listTicket);

        document.add(table);

        document.close();

    }
}
