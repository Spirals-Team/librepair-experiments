package com.qit.server.dao;

import com.qit.server.models.answer.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantDao extends JpaRepository<Variant, Long> {
}
