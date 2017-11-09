/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.studentportal_db.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import java.math.BigInteger;
import java.sql.Date;
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

import com.student_usecase.studentportal_db.StudentDetails;
import com.student_usecase.studentportal_db.StudentDetailsId;
import com.student_usecase.studentportal_db.service.StudentDetailsService;


/**
 * Controller object for domain model class StudentDetails.
 * @see StudentDetails
 */
@RestController("StudentPortal_DB.StudentDetailsController")
@Api(value = "StudentDetailsController", description = "Exposes APIs to work with StudentDetails resource.")
@RequestMapping("/StudentPortal_DB/StudentDetails")
public class StudentDetailsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDetailsController.class);

    @Autowired
	@Qualifier("StudentPortal_DB.StudentDetailsService")
	private StudentDetailsService studentDetailsService;

	@ApiOperation(value = "Creates a new StudentDetails instance.")
	@RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public StudentDetails createStudentDetails(@RequestBody StudentDetails studentDetails) {
		LOGGER.debug("Create StudentDetails with information: {}" , studentDetails);

		studentDetails = studentDetailsService.create(studentDetails);
		LOGGER.debug("Created StudentDetails with information: {}" , studentDetails);

	    return studentDetails;
	}

    @ApiOperation(value = "Returns the StudentDetails instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StudentDetails getStudentDetails(@RequestParam("address") String address,@RequestParam("contactNumber") BigInteger contactNumber,@RequestParam("dateofbirth") Date dateofbirth,@RequestParam("fatherName") String fatherName,@RequestParam("joiningDate") Date joiningDate,@RequestParam("studentId") Integer studentId,@RequestParam("studentName") String studentName) throws EntityNotFoundException {

        StudentDetailsId studentdetailsId = new StudentDetailsId();
        studentdetailsId.setAddress(address);
        studentdetailsId.setContactNumber(contactNumber);
        studentdetailsId.setDateofbirth(dateofbirth);
        studentdetailsId.setFatherName(fatherName);
        studentdetailsId.setJoiningDate(joiningDate);
        studentdetailsId.setStudentId(studentId);
        studentdetailsId.setStudentName(studentName);

        LOGGER.debug("Getting StudentDetails with id: {}" , studentdetailsId);
        StudentDetails studentDetails = studentDetailsService.getById(studentdetailsId);
        LOGGER.debug("StudentDetails details with id: {}" , studentDetails);

        return studentDetails;
    }



    @ApiOperation(value = "Updates the StudentDetails instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StudentDetails editStudentDetails(@RequestParam("address") String address,@RequestParam("contactNumber") BigInteger contactNumber,@RequestParam("dateofbirth") Date dateofbirth,@RequestParam("fatherName") String fatherName,@RequestParam("joiningDate") Date joiningDate,@RequestParam("studentId") Integer studentId,@RequestParam("studentName") String studentName, @RequestBody StudentDetails studentDetails) throws EntityNotFoundException {

        studentDetails.setAddress(address);
        studentDetails.setContactNumber(contactNumber);
        studentDetails.setDateofbirth(dateofbirth);
        studentDetails.setFatherName(fatherName);
        studentDetails.setJoiningDate(joiningDate);
        studentDetails.setStudentId(studentId);
        studentDetails.setStudentName(studentName);

        LOGGER.debug("StudentDetails details with id is updated with: {}" , studentDetails);

        return studentDetailsService.update(studentDetails);
    }


    @ApiOperation(value = "Deletes the StudentDetails instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteStudentDetails(@RequestParam("address") String address,@RequestParam("contactNumber") BigInteger contactNumber,@RequestParam("dateofbirth") Date dateofbirth,@RequestParam("fatherName") String fatherName,@RequestParam("joiningDate") Date joiningDate,@RequestParam("studentId") Integer studentId,@RequestParam("studentName") String studentName) throws EntityNotFoundException {

        StudentDetailsId studentdetailsId = new StudentDetailsId();
        studentdetailsId.setAddress(address);
        studentdetailsId.setContactNumber(contactNumber);
        studentdetailsId.setDateofbirth(dateofbirth);
        studentdetailsId.setFatherName(fatherName);
        studentdetailsId.setJoiningDate(joiningDate);
        studentdetailsId.setStudentId(studentId);
        studentdetailsId.setStudentName(studentName);

        LOGGER.debug("Deleting StudentDetails with id: {}" , studentdetailsId);
        StudentDetails studentDetails = studentDetailsService.delete(studentdetailsId);

        return studentDetails != null;
    }


    /**
     * @deprecated Use {@link #findStudentDetails(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of StudentDetails instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<StudentDetails> searchStudentDetailsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering StudentDetails list");
        return studentDetailsService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of StudentDetails instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<StudentDetails> findStudentDetails(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering StudentDetails list");
        return studentDetailsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of StudentDetails instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<StudentDetails> filterStudentDetails(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering StudentDetails list");
        return studentDetailsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportStudentDetails(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return studentDetailsService.export(exportType, query, pageable);
    }

	@ApiOperation(value = "Returns the total count of StudentDetails instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countStudentDetails( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting StudentDetails");
		return studentDetailsService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getStudentDetailsAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return studentDetailsService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service StudentDetailsService instance
	 */
	protected void setStudentDetailsService(StudentDetailsService service) {
		this.studentDetailsService = service;
	}

}

