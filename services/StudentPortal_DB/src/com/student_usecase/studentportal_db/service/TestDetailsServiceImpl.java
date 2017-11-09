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

import com.student_usecase.studentportal_db.TestDetails;
import com.student_usecase.studentportal_db.TestDetailsId;


/**
 * ServiceImpl object for domain model class TestDetails.
 *
 * @see TestDetails
 */
@Service("StudentPortal_DB.TestDetailsService")
public class TestDetailsServiceImpl implements TestDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDetailsServiceImpl.class);


    @Autowired
    @Qualifier("StudentPortal_DB.TestDetailsDao")
    private WMGenericDao<TestDetails, TestDetailsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<TestDetails, TestDetailsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
    @Override
	public TestDetails create(TestDetails testDetails) {
        LOGGER.debug("Creating a new TestDetails with information: {}", testDetails);
        TestDetails testDetailsCreated = this.wmGenericDao.create(testDetails);
        return testDetailsCreated;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public TestDetails getById(TestDetailsId testdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Finding TestDetails by id: {}", testdetailsId);
        TestDetails testDetails = this.wmGenericDao.findById(testdetailsId);
        if (testDetails == null){
            LOGGER.debug("No TestDetails found with id: {}", testdetailsId);
            throw new EntityNotFoundException(String.valueOf(testdetailsId));
        }
        return testDetails;
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public TestDetails findById(TestDetailsId testdetailsId) {
        LOGGER.debug("Finding TestDetails by id: {}", testdetailsId);
        return this.wmGenericDao.findById(testdetailsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "StudentPortal_DBTransactionManager")
	@Override
	public TestDetails update(TestDetails testDetails) throws EntityNotFoundException {
        LOGGER.debug("Updating TestDetails with information: {}", testDetails);
        this.wmGenericDao.update(testDetails);

        TestDetailsId testdetailsId = new TestDetailsId();
        testdetailsId.setTestId(testDetails.getTestId());
        testdetailsId.setTestName(testDetails.getTestName());

        return this.wmGenericDao.findById(testdetailsId);
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
	@Override
	public TestDetails delete(TestDetailsId testdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting TestDetails with id: {}", testdetailsId);
        TestDetails deleted = this.wmGenericDao.findById(testdetailsId);
        if (deleted == null) {
            LOGGER.debug("No TestDetails found with id: {}", testdetailsId);
            throw new EntityNotFoundException(String.valueOf(testdetailsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public Page<TestDetails> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TestDetails");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Page<TestDetails> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TestDetails");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service StudentPortal_DB for table TestDetails to {} format", exportType);
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

