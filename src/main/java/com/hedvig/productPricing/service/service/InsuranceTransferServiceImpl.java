package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.query.*;
import com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.MemberServiceClient;
import com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto.CancellationEmailSentToInsurerRequest;
import com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.dto.notificationService.InsuranceActivationDateUpdatedRequest;
import com.hedvig.productPricing.service.web.dto.InsuranceCompaniesSE;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Service
public class InsuranceTransferServiceImpl implements InsuranceTransferService {

  private final Logger log = LoggerFactory.getLogger(InsuranceTransferServiceImpl.class);
  private final JavaMailSender mailSender;
  private final String cancellationEmail;
  private final ClassPathResource signatureImage;
  private final ContractRepository contractRepository;
  private final UserRepository userRepository;
  private final MemberServiceClient memberServiceClient;
  private final InsuranceLookup insuranceLookup;
  private final ProductRepository productRepository;
  private final String[] emailBCC;

  public InsuranceTransferServiceImpl(
      JavaMailSender mailSender,
      ContractRepository contractRepository,
      UserRepository userRepository,
      MemberServiceClient memberServiceClient,
      InsuranceLookup insuranceLookup,
      ProductRepository productRepository,
      @Value("${hedvig.insuranceTransferService.bcc}") String[] emailBCC)
      throws IOException {
    this.mailSender = mailSender;
    this.contractRepository = contractRepository;
    this.userRepository = userRepository;
    this.memberServiceClient = memberServiceClient;
    this.insuranceLookup = insuranceLookup;
    this.productRepository = productRepository;
    this.emailBCC = emailBCC;
    cancellationEmail = LoadEmail("cancel_existing_insurance.html");
    signatureImage = new ClassPathResource("mail/wordmark_mail.png");
  }

  private String LoadEmail(final String s) throws IOException {
    return IOUtils.toString(new ClassPathResource("mail/" + s).getInputStream(), "UTF-8");
  }

  @Override
  public void startTransferProcess(
      String memberId, Boolean insuredAtOtherCompany, InsuranceCompaniesSE currentInsurer) {
    if (currentInsurer.equals(InsuranceCompaniesSE.OTHER)) {
      notifyUnknownInsurer(memberId);
    } else {
      Optional<UserEntity> byId = userRepository.findById(memberId);
      Optional<ContractEntity> contract = contractRepository.findByMemberId(memberId);
      if (contract.isPresent() && byId.isPresent()) {
        UserEntity ue = byId.get();
        val emailAddress = insuranceLookup.extractEmailAddress(currentInsurer);
        // sendCancellationEmail(emailAddress, contract.get().contract, ue.address.getStreet(),
        // ue.firstName, memberId);
        // updateProductRepository(memberId);
        memberServiceClient.cancellationEmailSentToInsurer(
            memberId, new CancellationEmailSentToInsurerRequest(ue.currentInsurer));
      } else {
        log.error("Contract for member " + memberId + "not found");
      }
    }
  }

  private void updateProductRepository(String memberId) {
    Optional<ProductEntity> byMemberId =
        productRepository
            .findByMemberId(memberId)
            .stream()
            .filter(x -> x.state == ProductStates.SIGNED)
            .findFirst();
    byMemberId.ifPresent(
        productEntity -> {
          productEntity.cancellationEmailSentAt = Instant.now();
          productRepository.save(productEntity);
        });
  }

  private void notifyUnknownInsurer(String memberId) {
    try {
      val message = mailSender.createMimeMessage();
      val helper = new MimeMessageHelper(message, true, "UTF-8");

      String text = "Member (%s) har inte valt något försäkringsbolag";
      helper.setSubject(String.format(text, memberId));
      helper.setFrom("\"Medlemsadmin Hedvig\" <medlemsadmin@hedvig.com>");
      helper.setTo(emailBCC);

      helper.setText("Ring medlem!");

      mailSender.send(message);
    } catch (MailException | MessagingException e) {
      log.error("Could not send insurance cancellation letter!", e);
    }
  }

  @Override
  public void activationDateUpdated(
      String memberId, String currentInsurer, Instant activationDate) {
    memberServiceClient.activationDateUpdated(
        memberId, new InsuranceActivationDateUpdatedRequest(currentInsurer, activationDate));
  }

  private void sendCancellationEmail(
      String emailAddress, byte[] contract, String street, String firstName, String memberId) {
    try {
      val message = mailSender.createMimeMessage();
      val helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setSubject("Uppsägning av försäkring");
      helper.setFrom("\"Medlemsadmin Hedvig\" <medlemsadmin@hedvig.com>");
      helper.setTo(emailAddress);
      helper.setBcc(emailBCC);

      val templateMail =
          cancellationEmail.replace("{NAME}", firstName).replace("{ADDRESS}", street);
      helper.setText(templateMail, true);
      helper.addInline("image.png", signatureImage, "image/png");
      helper.addAttachment(
          "insurance-mandate.pdf", new ByteArrayResource(contract), "application/pdf");

      mailSender.send(message);
    } catch (MailException | MessagingException e) {
      log.error("Could not send email!", e);
    }
  }
}
