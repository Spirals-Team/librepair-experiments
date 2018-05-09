package net.posesor.payments;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.posesor.SessionToken;
import net.posesor.runtime.LocationBuilder;
import net.posesor.subjects.SubjectOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.function.Function;

/**
 * HTTP endpoint for payment-related operations.
 */
@RestController
@ControllerAdvice
@RequestMapping(value = "/api/payments")
@Slf4j
@Scope("session")
public final class PaymentsEndpoint {

    private final SubjectOperations subjectOperations;

    private final PaymentOperations operations;

    private final Function<String, URI> locationSupplier;

    /**
     * Creates a new instance of {@link PaymentsEndpoint}.
     *
     * @param operations service to allow generate subjects in the fly.
     * @param sessionToken provides info about logged user
     */
    @Autowired
    public PaymentsEndpoint(MongoOperations operations, SessionToken sessionToken) {
        this(new LocationBuilder<>(PaymentsEndpoint.class),
                new PaymentOperations(operations, sessionToken.getUserName()),
                new SubjectOperations(operations, sessionToken.getUserName()));
    }

    /**
     * Creates a new instance of {@link PaymentsEndpoint}.
     * @param subjectOperations service to allow generate subjects in the fly.
     */
    public PaymentsEndpoint(Function<String, URI> locationSupplier, PaymentOperations operations, SubjectOperations subjectOperations) {
        this.locationSupplier = locationSupplier;
        this.operations = operations;
        this.subjectOperations = subjectOperations;
    }

    /**
     * Allows insert to DB new document.
     *
     * @param payment model DTO to insert in MongoDb
     * @return location
     */
    @PostMapping
    @SuppressWarnings("unused")
    public ResponseEntity post(@RequestBody @Valid PaymentDto payment) {
        val model = Mapper.map(payment);

        val subject =  subjectOperations.getOrAdd(payment.getSubjectName());
        model.setSubjectId(subject.getSubjectId());

        operations.insert(model);

        val location = locationSupplier.apply(model.getPaymentId());

        return ResponseEntity.created(location).build();
    }

    /***
     * Allows to create valid document template and return the client to work on them.
     *
     * From client perspective, it is a bit risky to keep factory of templates and
     * discover at runtime that factory is not 100% compatible with
     * server validation. So create documents on server-side is the smarted way.
     * Disadvantage of that approach is on more request to server.
     * @return template for payment.
     */
    @GetMapping("/template")
    @SuppressWarnings("unused")
    @ResponseBody
    public PaymentDto getTemplate() {
        val result = new PaymentDto();
        result.setEntries(new PaymentDto.PaymentEntry[]{
                new PaymentDto.PaymentEntry(),
                new PaymentDto.PaymentEntry(),
                new PaymentDto.PaymentEntry(),
        });
        return result;
    }

    /**
     * Returned all documents which have provided paymentId and created by logged user
     *
     * @param paymentId id of payment
     * @return {@link PaymentDto}
     */
    @GetMapping("/{paymentId}")
    @SuppressWarnings("unused")
    public ResponseEntity get(@PathVariable("paymentId") String paymentId) {
        val entry = operations.get(paymentId);

        if (entry == null) return ResponseEntity.notFound().build();

        val result = Mapper.map(entry);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{paymentId}")
    @SuppressWarnings("unused")
    public ResponseEntity put(@PathVariable("paymentId") String paymentId, @RequestBody PaymentDto payment) {

        val stored = operations.get(paymentId);
        if (stored == null) ResponseEntity.notFound().build();

        val newValue = Mapper.map(payment);
        newValue.setPaymentId(paymentId);

        operations.update(newValue);

        return ResponseEntity.noContent().build();
    }


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @SuppressWarnings("unused")
    public PaymentDto[] getPaymentPage() {
        val userPayments = operations.getAll();

        val uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}");

        return Mapper.map(userPayments)
                // normalization: don't allow return null instead of collections.
                .map(it -> {
                    it.setEntries(it.getEntries() == null
                            ? new PaymentDto.PaymentEntry[0]
                            : it.getEntries());
                    return it;
                })

                .map(it -> {
                    it.setPaymentId(uriBuilder.buildAndExpand(it.getPaymentId()).toUriString());
                    return it;
                })
                .toArray(PaymentDto[]::new);

    }

    /**
     * Allows removing payment from Db by paymentId
     *
      * @param paymentId Id of payments which should be removed
     */
    @DeleteMapping(path = "/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("paymentId") String paymentId) {
        operations.remove(paymentId);
    }

}
