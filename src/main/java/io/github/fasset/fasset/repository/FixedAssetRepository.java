/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.repository;

import io.github.fasset.fasset.model.FixedAsset;
import org.javamoney.moneta.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This Repository extends the Spring JPA Template and has runtime-implentation depending on the
 * nature of the {@code Entity}
 */
@Repository("fixedAssetRepository")
public interface FixedAssetRepository extends JpaRepository<FixedAsset, Integer> {

    @Query("SELECT " + "DISTINCT e.category " + "FROM FixedAsset e")
    List<String> getDistinctCategories();

    @Query("SELECT " + "DISTINCT e.solId " + "FROM FixedAsset e")
    List<String> getDistinctSolIds();

    /**
     * Return total purchase cost for a given category
     *
     * @param category of fixed asset
     * @return Purchase cost for the category
     */
    @Query("Select " + "SUM(e.purchaseCost) " + "FROM FixedAsset e " + "WHERE e.category = :category ")
    Money getTotalCategoryPurchaseCost(@Param("category") String category);

    @Query("SELECT " + "SUM(e.purchaseCost) " + "FROM FixedAsset e " + "WHERE e.solId = :solId ")
    Money getTotalSolPurchaseCost(@Param("solId") String solId);

    /**
     * Return total net book value for a given category
     *
     * @param category of fixed asset
     * @return Total net book value for a given category
     */
    @Query("Select " + "SUM(e.netBookValue) " + "FROM FixedAsset e " + "WHERE e.category = :category ")
    Money getTotalCategoryNetBookValue(@Param("category") String category);

    @Query("SELECT " + "SUM(e.netBookValue) " + "FROM FixedAsset e " + "WHERE e.solId = :solId ")
    Money getTotalSolNetBookValue(@Param("solId") String solId);

    /**
     * Return total no. of items for a given category
     *
     * @param category of fixed asset
     * @return No of fixed assets in a given category
     */
    @Query("Select " + "COUNT(e.category) " + "FROM FixedAsset e " + "WHERE e.category = :category ")
    int getTotalCategoryCount(@Param("category") String category);

    @Query("SELECT " + "COUNT(e.solId) " + "FROM FixedAsset e " + "WHERE e.solId = :solId ")
    int getTotalSolCount(@Param("solId") String solId);


}
