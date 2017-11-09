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

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.student_usecase.studentportal_db.StudentAcademics;
import com.student_usecase.studentportal_db.StudentAcademicsId;


/**
 * ServiceImpl object for domain model class StudentAcademics.
 *
 * @see StudentAcademics
 */
@Service("StudentPortal_DB.StudentAcademicsService")
public class StudentAcademicsServiceImpl implements StudentAcademicsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentAcademicsServiceImpl.class);


    @Autowired
    @Qualifier("StudentPortal_DB.StudentAcademicsDao")
    private WMGenericDao<StudentAcademics, StudentAcademicsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<StudentAcademics, StudentAcademicsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
    @Override
	public StudentAcademics create(StudentAcademics studentAcademics) {
        LOGGER.debug("Creating a new StudentAcademics with information: {}", studentAcademics);
        StudentAcademics studentAcademicsCreated = this.wmGenericDao.create(studentAcademics);
        return studentAcademicsCreated;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentAcademics getById(StudentAcademicsId studentacademicsId) throws EntityNotFoundException {
        LOGGER.debug("Finding StudentAcademics by id: {}", studentacademicsId);
        StudentAcademics studentAcademics = this.wmGenericDao.findById(studentacademicsId);
        if (studentAcademics == null){
            LOGGER.debug("No StudentAcademics found with id: {}", studentacademicsId);
            throw new EntityNotFoundException(String.valueOf(studentacademicsId));
        }
        return studentAcademics;
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentAcademics findById(StudentAcademicsId studentacademicsId) {
        LOGGER.debug("Finding StudentAcademics by id: {}", studentacademicsId);
        return this.wmGenericDao.findById(studentacademicsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentAcademics update(StudentAcademics studentAcademics) throws EntityNotFoundException {
        LOGGER.debug("Updating StudentAcademics with information: {}", studentAcademics);
        this.wmGenericDao.update(studentAcademics);

        StudentAcademicsId studentacademicsId = new StudentAcademicsId();
        studentacademicsId.setAcademicYear(studentAcademics.getAcademicYear());
        studentacademicsId.setRollnumber(studentAcademics.getRollnumber());
        studentacademicsId.setStandard(studentAcademics.getStandard());
        studentacademicsId.setStudentId(studentAcademics.getStudentId());

        return this.wmGenericDao.findById(studentacademicsId);
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
	@Override
	public StudentAcademics delete(StudentAcademicsId studentacademicsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting StudentAcademics with id: {}", studentacademicsId);
        StudentAcademics deleted = this.wmGenericDao.findById(studentacademicsId);
        if (deleted == null) {
            LOGGER.debug("No StudentAcademics found with id: {}", studentacademicsId);
            throw new EntityNotFoundException(String.valueOf(studentacademicsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public Page<StudentAcademics> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all StudentAcademics");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Page<StudentAcademics> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all StudentAcademics");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service StudentPortal_DB for table StudentAcademics to {} format", exportType);
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

