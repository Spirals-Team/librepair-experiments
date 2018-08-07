package com.hedvig.productPricing.service.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hedvig.productPricing.service.query.PerilEntity;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.UserEntity;
import lombok.val;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InsuranceDTO {

  @JsonIgnore public UUID id;

  public ArrayList<CategoryDTO> categories = new ArrayList<>();
  public double currentTotalPrice;
  public Double newTotalPrice;
  public ProductStatus status;
  public String statusDescription = "";
  public InsuranceCompaniesSE currentInsurerName = null;
  public Boolean insuredAtOtherCompany;
  public String policyUrl;
  public String presaleInformationUrl;
  public String insuranceType;
  public LocalDateTime activeFrom;
  public Integer personsInHousehold;
  public String addressStreet = null;
  public String certificateUrl;

  public InsuranceDTO() {}

  public InsuranceDTO(ProductEntity p, List<PerilEntity> perils) {
    this(Clock.systemDefaultZone(), p, perils, null);
  }

  public InsuranceDTO(ProductEntity p, List<PerilEntity> perils, UserEntity ue) {
    this(Clock.systemDefaultZone(), p, perils, ue);
  }

  public InsuranceDTO(Clock clock, ProductEntity p, List<PerilEntity> perils, UserEntity ue) {
    this.id = p.id;
    this.currentTotalPrice = p.currentTotalPrice.doubleValue();
    this.newTotalPrice = p.newPrice != null ? p.newPrice.doubleValue() : null;

    this.categories = new ArrayList<>();
    this.insuredAtOtherCompany = p.insuredAtOtherCompany;
    if (p.insuredAtOtherCompany) {
      this.currentInsurerName =
          InsuranceCompaniesSE.create(
              p.currentInsurer != null ? p.currentInsurer : ue.currentInsurer);
    }

    this.insuranceType = p.houseType.toString();
    this.activeFrom = p.activeFrom;

    this.personsInHousehold =
        p.personsInHouseHold != null ? p.personsInHouseHold : ue.personsInHouseHold;

    if (p.address != null) {
      this.addressStreet = p.address.getStreet();
    } else if (ue.address != null) {
      this.addressStreet = ue.address.getStreet();
    }

    final ProductStatus.Status status =
        ProductStatus.createStatus(clock, p.state, p.activeFrom, p.activeTo);
    this.status = status.getStatus();
    this.statusDescription = status.getDescription();

    CategoryDTO cat1 = new CategoryDTO();
    cat1.iconUrl =
        "https://s3.eu-central-1.amazonaws.com/com-hedvig-web-content/du_och_din_familj%402x.png";

    CategoryDTO cat2 = new CategoryDTO();
    cat2.iconUrl = "https://s3.eu-central-1.amazonaws.com/com-hedvig-web-content/lagenhet%402x.png";

    CategoryDTO cat3 = new CategoryDTO();
    cat3.iconUrl = "https://s3.eu-central-1.amazonaws.com/com-hedvig-web-content/prylar%402x.png";

    if ((p.address != null) && (p.personsInHouseHold != null)) {
      val addressText = p.address.getStreet().length() < 22 ? p.address.getStreet() : null;

      switch (p.personsInHouseHold) {
        case 1:
          cat1.title = "Jag";
          cat1.description = "försäkras för";

          if (addressText == null) {
            cat2.title = "Min lägenhet";
          } else {
            cat2.title = addressText;
          }
          cat2.description = "försäkras för";

          cat3.title = "Mina prylar";
          cat3.description = "försäkras för";
          break;

        case 2:
          // TODO: Use names when present
          cat1.title = "Jag och min familj";
          cat1.description = "försäkras för";

          if (addressText == null) {
            cat2.title = "Vår lägenhet";
          } else {
            cat2.title = addressText;
          }
          cat2.description = "försäkras för";

          cat3.title = "Våra prylar";
          cat3.description = "försäkras för";
          break;

        default:
          cat1.title = "Jag och min familj";
          cat1.description = "försäkras för";

          if (addressText == null) {
            cat2.title = "Vår lägenhet";
          } else {
            cat2.title = addressText;
          }
          cat2.description = "försäkras för";

          cat3.title = "Våra prylar";
          cat3.description = "försäkras för";
          break;
      }
    } else if (ue != null && ue.address != null && ue.personsInHouseHold != null) {
      val addressText = ue.address.getStreet().length() < 22 ? ue.address.getStreet() : null;

      switch (ue.personsInHouseHold) {
        case 1:
          cat1.title = "Jag";
          cat1.description = "försäkras för";

          if (addressText == null) {
            cat2.title = "Min lägenhet";
          } else {
            cat2.title = addressText;
          }
          cat2.description = "försäkras för";

          cat3.title = "Mina prylar";
          cat3.description = "försäkras för";
          break;

        case 2:
          // TODO: Use names when present
          cat1.title = "Jag och min familj";
          cat1.description = "försäkras för";

          if (addressText == null) {
            cat2.title = "Vår lägenhet";
          } else {
            cat2.title = addressText;
          }
          cat2.description = "försäkras för";

          cat3.title = "Våra prylar";
          cat3.description = "försäkras för";
          break;

        default:
          cat1.title = "Jag och min familj";
          cat1.description = "försäkras för";

          if (addressText == null) {
            cat2.title = "Vår lägenhet";
          } else {
            cat2.title = addressText;
          }
          cat2.description = "försäkras för";

          cat3.title = "Våra prylar";
          cat3.description = "försäkras för";
          break;
      }
    } else {
      cat1.title = "Du och din familj";
      cat2.title = "Din lägenhet";
      cat3.title = "Dina prylar";
      cat1.setSummary();
      cat2.setSummary();
      cat3.setSummary();
    }

    for (PerilEntity peril : perils) {
      PerilDTO pd = new PerilDTO(peril);
      // Lookup the user specific state for the peril
      pd.state = p.perils.get(pd.id);
      switch (pd.category) {
        case "ME":
          cat1.perils.add(pd);
          break;
        case "HOUSE":
          cat2.perils.add(pd);
          break;
        case "STUFF":
          cat3.perils.add(pd);
          break;
      }
    }
    this.categories.add(cat1);
    this.categories.add(cat2);
    this.categories.add(cat3);
  }
}
