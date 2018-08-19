package ua.epam.spring.hometask.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.domain.form.PurchasingTicketsForm;
import ua.epam.spring.hometask.domain.to.PurchasedTicketDTO;
import ua.epam.spring.hometask.domain.to.PurchasingTicketDTO;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserAccountService;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.util.pdf.PdfUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Vladimir_Vysokomorny on 05-Dec-17.
 */
@Controller
public class BookingController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserAccountService accountService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            }

            @Override
            public String getAsText() throws IllegalArgumentException {
                return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDateTime) getValue());
            }
        });
    }


    @RequestMapping("/tickets")
    public ModelAndView getPurchasedTickets(@RequestParam("eventId") long eventId,
                                            @RequestParam("dateTime") LocalDateTime dateTime) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);

        Event event = eventService.getById(eventId);
        List<PurchasedTicketDTO> userTicketsForEvent = bookingService
                .getPurchasedTicketsForEvent(event, dateTime)
                .stream()
                .filter(t -> t.getUser().equals(user))
                .map(t -> new PurchasedTicketDTO(
                        t.getId(),
                        t.getEvent().getName(),
                        t.getDateTime(),
                        t.getSeat()))
                .collect(Collectors.toList());

        ModelAndView mav = new ModelAndView("tickets");
        mav.addObject("purchasedTickets", userTicketsForEvent);
        mav.addObject("eventId", eventId);
        mav.addObject("airDate", dateTime);

        return mav;
    }

    @RequestMapping("/tickets/pdf")
    public void getPurchasedTicketsInPdf(@RequestParam("eventId") long eventId,
                                         @RequestParam("dateTime") LocalDateTime dateTime,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);


        Event event = eventService.getById(eventId);
        Set<Ticket> tickets = bookingService
                .getPurchasedTicketsForEvent(event, dateTime)
                .stream()
                .filter(t -> t.getUser().equals(user))
                .collect(Collectors.toSet());


        String fileName = "tickets.pdf";
        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temporaryFilePath = tempDirectory.getAbsolutePath();
        final String fullPath = temporaryFilePath + "\\" + fileName;

        PdfUtil.writeTickets(tickets,fullPath);

        File file = new File(fullPath);
        if (!file.exists()) {
            throw new FileNotFoundException("file with path: " + fullPath + " was not found.");
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    @RequestMapping(value = "/tickets/showPrice", method = RequestMethod.POST)
    public ModelAndView getPurchasingTickets(@ModelAttribute("ticketsForm") PurchasingTicketsForm ticketsForm,
                                             @RequestParam long eventId,
                                             @RequestParam LocalDateTime airDate,
                                             BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException(result.toString());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);


        Event event = eventService.getById(eventId);

        List<PurchasingTicketDTO> tickets =
                ticketsForm.getSeats()
                        .stream()
                        .map(seat -> new PurchasingTicketDTO(event.getName(), airDate, seat)).collect(Collectors.toList());
        double price = bookingService.getTicketsPrice(event, airDate, user, new HashSet<>(ticketsForm.getSeats()));

        Collection<UserAccount> accounts = accountService.getAll(user.getId());

        ModelAndView mav = new ModelAndView("book");
        mav.addObject("eventId", eventId);
        mav.addObject("airDate", airDate);
        mav.addObject("purchasingTickets", tickets);
        mav.addObject("price", price);
        mav.addObject("accounts", accounts);

        return mav;
    }

    @RequestMapping(value = "/tickets/book", method = RequestMethod.POST)
    public String bookTickets(@ModelAttribute("ticketsForm") PurchasingTicketsForm ticketsForm,
                              @RequestParam("accountId") long accountId,
                              @RequestParam("price") long price,
                              @RequestParam long eventId,
                              @RequestParam LocalDateTime airDate,
                              BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException(result.toString());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);

        Event event = eventService.getById(eventId);
        Set<Ticket> tickets = ticketsForm.getSeats().stream()
                .map(seat -> new Ticket(user, event, airDate, seat))
                .collect(Collectors.toSet());


        UserAccount account = accountService.getById(accountId, user.getId());
        bookingService.bookTickets(tickets, user.getId(), account, price);

        return "redirect:/tickets?eventId=" + eventId + "&dateTime=" + airDate;
    }
}
