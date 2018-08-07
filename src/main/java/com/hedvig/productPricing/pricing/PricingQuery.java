package com.hedvig.productPricing.pricing;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "query_type")
public class PricingQuery {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Integer id;

}
