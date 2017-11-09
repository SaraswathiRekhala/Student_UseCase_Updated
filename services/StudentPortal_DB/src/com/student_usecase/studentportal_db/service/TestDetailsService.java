/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.studentportal_db.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.student_usecase.studentportal_db.TestDetails;
import com.student_usecase.studentportal_db.TestDetailsId;

/**
 * Service object for domain model class {@link TestDetails}.
 */
public interface TestDetailsService {

    /**
     * Creates a new TestDetails. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TestDetails if any.
     *
     * @param testDetails Details of the TestDetails to be created; value cannot be null.
     * @return The newly created TestDetails.
     */
	TestDetails create(TestDetails testDetails);


	/**
	 * Returns TestDetails by given id if exists.
	 *
	 * @param testdetailsId The id of the TestDetails to get; value cannot be null.
	 * @return TestDetails associated with the given testdetailsId.
     * @throws EntityNotFoundException If no TestDetails is found.
	 */
	TestDetails getById(TestDetailsId testdetailsId) throws EntityNotFoundException;

    /**
	 * Find and return the TestDetails by given id if exists, returns null otherwise.
	 *
	 * @param testdetailsId The id of the TestDetails to get; value cannot be null.
	 * @return TestDetails associated with the given testdetailsId.
	 */
	TestDetails findById(TestDetailsId testdetailsId);


	/**
	 * Updates the details of an existing TestDetails. It replaces all fields of the existing TestDetails with the given testDetails.
	 *
     * This method overrides the input field values using Server side or database managed properties defined on TestDetails if any.
     *
	 * @param testDetails The details of the TestDetails to be updated; value cannot be null.
	 * @return The updated TestDetails.
	 * @throws EntityNotFoundException if no TestDetails is found with given input.
	 */
	TestDetails update(TestDetails testDetails) throws EntityNotFoundException;

    /**
	 * Deletes an existing TestDetails with the given id.
	 *
	 * @param testdetailsId The id of the TestDetails to be deleted; value cannot be null.
	 * @return The deleted TestDetails.
	 * @throws EntityNotFoundException if no TestDetails found with the given id.
	 */
	TestDetails delete(TestDetailsId testdetailsId) throws EntityNotFoundException;

	/**
	 * Find all TestDetails matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
	 *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
	 *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TestDetails.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
	 */
    @Deprecated
	Page<TestDetails> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
	 * Find all TestDetails matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TestDetails.
     *
     * @see Pageable
     * @see Page
	 */
    Page<TestDetails> findAll(String query, Pageable pageable);

    /**
	 * Exports all TestDetails matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
	 */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

	/**
	 * Retrieve the count of the TestDetails in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
	 * @return The count of the TestDetails.
	 */
	long count(String query);

	/**
	 * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
	 * @return Paginated data with included fields.

     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
	Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}

