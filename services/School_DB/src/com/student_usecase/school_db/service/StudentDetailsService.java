/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.school_db.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.student_usecase.school_db.Results;
import com.student_usecase.school_db.StudentAcademics;
import com.student_usecase.school_db.StudentDetails;

/**
 * Service object for domain model class {@link StudentDetails}.
 */
public interface StudentDetailsService {

    /**
     * Creates a new StudentDetails. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on StudentDetails if any.
     *
     * @param studentDetails Details of the StudentDetails to be created; value cannot be null.
     * @return The newly created StudentDetails.
     */
	StudentDetails create(StudentDetails studentDetails);


	/**
	 * Returns StudentDetails by given id if exists.
	 *
	 * @param studentdetailsId The id of the StudentDetails to get; value cannot be null.
	 * @return StudentDetails associated with the given studentdetailsId.
     * @throws EntityNotFoundException If no StudentDetails is found.
	 */
	StudentDetails getById(Integer studentdetailsId) throws EntityNotFoundException;

    /**
	 * Find and return the StudentDetails by given id if exists, returns null otherwise.
	 *
	 * @param studentdetailsId The id of the StudentDetails to get; value cannot be null.
	 * @return StudentDetails associated with the given studentdetailsId.
	 */
	StudentDetails findById(Integer studentdetailsId);


	/**
	 * Updates the details of an existing StudentDetails. It replaces all fields of the existing StudentDetails with the given studentDetails.
	 *
     * This method overrides the input field values using Server side or database managed properties defined on StudentDetails if any.
     *
	 * @param studentDetails The details of the StudentDetails to be updated; value cannot be null.
	 * @return The updated StudentDetails.
	 * @throws EntityNotFoundException if no StudentDetails is found with given input.
	 */
	StudentDetails update(StudentDetails studentDetails) throws EntityNotFoundException;

    /**
	 * Deletes an existing StudentDetails with the given id.
	 *
	 * @param studentdetailsId The id of the StudentDetails to be deleted; value cannot be null.
	 * @return The deleted StudentDetails.
	 * @throws EntityNotFoundException if no StudentDetails found with the given id.
	 */
	StudentDetails delete(Integer studentdetailsId) throws EntityNotFoundException;

	/**
	 * Find all StudentDetails matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
	 *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
	 *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching StudentDetails.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
	 */
    @Deprecated
	Page<StudentDetails> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
	 * Find all StudentDetails matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
	 *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching StudentDetails.
     *
     * @see Pageable
     * @see Page
	 */
    Page<StudentDetails> findAll(String query, Pageable pageable);

    /**
	 * Exports all StudentDetails matching the given input query to the given exportType format.
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
	 * Retrieve the count of the StudentDetails in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
	 * @return The count of the StudentDetails.
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

    /*
     * Returns the associated resultses for given StudentDetails id.
     *
     * @param studentId value of studentId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated Results instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<Results> findAssociatedResultses(Integer studentId, Pageable pageable);

    /*
     * Returns the associated studentAcademicses for given StudentDetails id.
     *
     * @param studentId value of studentId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated StudentAcademics instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<StudentAcademics> findAssociatedStudentAcademicses(Integer studentId, Pageable pageable);

}

