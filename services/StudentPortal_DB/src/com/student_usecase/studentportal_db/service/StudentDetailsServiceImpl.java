/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.studentportal_db.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.student_usecase.studentportal_db.StudentDetails;
import com.student_usecase.studentportal_db.StudentDetailsId;


/**
 * ServiceImpl object for domain model class StudentDetails.
 *
 * @see StudentDetails
 */
@Service("StudentPortal_DB.StudentDetailsService")
@Validated
public class StudentDetailsServiceImpl implements StudentDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDetailsServiceImpl.class);


    @Autowired
    @Qualifier("StudentPortal_DB.StudentDetailsDao")
    private WMGenericDao<StudentDetails, StudentDetailsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<StudentDetails, StudentDetailsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
    @Override
	public StudentDetails create(StudentDetails studentDetails) {
        LOGGER.debug("Creating a new StudentDetails with information: {}", studentDetails);
        StudentDetails studentDetailsCreated = this.wmGenericDao.create(studentDetails);
        return studentDetailsCreated;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentDetails getById(StudentDetailsId studentdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Finding StudentDetails by id: {}", studentdetailsId);
        StudentDetails studentDetails = this.wmGenericDao.findById(studentdetailsId);
        if (studentDetails == null){
            LOGGER.debug("No StudentDetails found with id: {}", studentdetailsId);
            throw new EntityNotFoundException(String.valueOf(studentdetailsId));
        }
        return studentDetails;
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentDetails findById(StudentDetailsId studentdetailsId) {
        LOGGER.debug("Finding StudentDetails by id: {}", studentdetailsId);
        return this.wmGenericDao.findById(studentdetailsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentDetails update(StudentDetails studentDetails) throws EntityNotFoundException {
        LOGGER.debug("Updating StudentDetails with information: {}", studentDetails);
        this.wmGenericDao.update(studentDetails);

        StudentDetailsId studentdetailsId = new StudentDetailsId();
        studentdetailsId.setAddress(studentDetails.getAddress());
        studentdetailsId.setContactNumber(studentDetails.getContactNumber());
        studentdetailsId.setDateofbirth(studentDetails.getDateofbirth());
        studentdetailsId.setFatherName(studentDetails.getFatherName());
        studentdetailsId.setJoiningDate(studentDetails.getJoiningDate());
        studentdetailsId.setStudentId(studentDetails.getStudentId());
        studentdetailsId.setStudentName(studentDetails.getStudentName());

        return this.wmGenericDao.findById(studentdetailsId);
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentDetails delete(StudentDetailsId studentdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting StudentDetails with id: {}", studentdetailsId);
        StudentDetails deleted = this.wmGenericDao.findById(studentdetailsId);
        if (deleted == null) {
            LOGGER.debug("No StudentDetails found with id: {}", studentdetailsId);
            throw new EntityNotFoundException(String.valueOf(studentdetailsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public Page<StudentDetails> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all StudentDetails");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Page<StudentDetails> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all StudentDetails");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service StudentPortal_DB for table StudentDetails to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}

