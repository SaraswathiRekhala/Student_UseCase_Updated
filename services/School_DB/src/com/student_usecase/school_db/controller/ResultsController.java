/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.school_db.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.student_usecase.school_db.Results;
import com.student_usecase.school_db.ResultsId;
import com.student_usecase.school_db.service.ResultsService;


/**
 * Controller object for domain model class Results.
 * @see Results
 */
@RestController("School_DB.ResultsController")
@Api(value = "ResultsController", description = "Exposes APIs to work with Results resource.")
@RequestMapping("/School_DB/Results")
public class ResultsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultsController.class);

    @Autowired
	@Qualifier("School_DB.ResultsService")
	private ResultsService resultsService;

	@ApiOperation(value = "Creates a new Results instance.")
@RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
public Results createResults(@RequestBody Results results) {
		LOGGER.debug("Create Results with information: {}" , results);

		results = resultsService.create(results);
		LOGGER.debug("Created Results with information: {}" , results);

	    return results;
	}

@ApiOperation(value = "Returns the Results instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Results getResults(@RequestParam("testConductedId") Integer testConductedId,@RequestParam("studentId") Integer studentId,@RequestParam("academicYear") String academicYear,@RequestParam("standardId") Integer standardId) throws EntityNotFoundException {

        ResultsId resultsId = new ResultsId();
        resultsId.setTestConductedId(testConductedId);
        resultsId.setStudentId(studentId);
        resultsId.setAcademicYear(academicYear);
        resultsId.setStandardId(standardId);

        LOGGER.debug("Getting Results with id: {}" , resultsId);
        Results results = resultsService.getById(resultsId);
        LOGGER.debug("Results details with id: {}" , results);

        return results;
    }



    @ApiOperation(value = "Updates the Results instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Results editResults(@RequestParam("testConductedId") Integer testConductedId,@RequestParam("studentId") Integer studentId,@RequestParam("academicYear") String academicYear,@RequestParam("standardId") Integer standardId, @RequestBody Results results) throws EntityNotFoundException {

        results.setTestConductedId(testConductedId);
        results.setStudentId(studentId);
        results.setAcademicYear(academicYear);
        results.setStandardId(standardId);

        LOGGER.debug("Results details with id is updated with: {}" , results);

        return resultsService.update(results);
    }


    @ApiOperation(value = "Deletes the Results instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteResults(@RequestParam("testConductedId") Integer testConductedId,@RequestParam("studentId") Integer studentId,@RequestParam("academicYear") String academicYear,@RequestParam("standardId") Integer standardId) throws EntityNotFoundException {

        ResultsId resultsId = new ResultsId();
        resultsId.setTestConductedId(testConductedId);
        resultsId.setStudentId(studentId);
        resultsId.setAcademicYear(academicYear);
        resultsId.setStandardId(standardId);

        LOGGER.debug("Deleting Results with id: {}" , resultsId);
        Results results = resultsService.delete(resultsId);

        return results != null;
    }


    /**
     * @deprecated Use {@link #findResults(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Results instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Results> searchResultsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Results list");
        return resultsService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Results instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Results> findResults(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Results list");
        return resultsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Results instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Results> filterResults(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Results list");
        return resultsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportResults(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return resultsService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of Results instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countResults( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Results");
		return resultsService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getResultsAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return resultsService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service ResultsService instance
	 */
	protected void setResultsService(ResultsService service) {
		this.resultsService = service;
	}

}

