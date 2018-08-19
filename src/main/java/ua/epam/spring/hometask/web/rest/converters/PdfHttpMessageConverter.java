package ua.epam.spring.hometask.web.rest.converters;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;
import ua.epam.spring.hometask.domain.to.TicketList;
import ua.epam.spring.hometask.util.pdf.PdfUtil;

import java.io.ByteArrayInputStream;

public class PdfHttpMessageConverter extends AbstractHttpMessageConverter<TicketList> {

    public PdfHttpMessageConverter() {
        super(MediaType.valueOf("application/pdf"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return TicketList.class.isAssignableFrom(clazz);
    }

    @Override
    protected TicketList readInternal(Class<? extends TicketList> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Upload not supported");
    }

    @Override
    protected void writeInternal(TicketList ticketList, HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
        try {
            //the following line doesn't work, why???
            //outputMessage.getHeaders().set("Content-Disposition", "attachment; filename=tickets.pdf");

            ByteArrayInputStream pdf = PdfUtil.toBaos(ticketList);
            FileCopyUtils.copy(pdf, outputMessage.getBody());

        } catch (Exception e) {
            throw new HttpMessageNotWritableException("Cannot write PDF", e);
        }
    }
}
