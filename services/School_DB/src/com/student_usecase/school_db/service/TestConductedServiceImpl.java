/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.school_db.service;

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

import com.student_usecase.school_db.Results;
import com.student_usecase.school_db.TestConducted;
import com.student_usecase.school_db.TestConductedId;


/**
 * ServiceImpl object for domain model class TestConducted.
 *
 * @see TestConducted
 */
@Service("School_DB.TestConductedService")
public class TestConductedServiceImpl implements TestConductedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConductedServiceImpl.class);

    @Autowired
	@Qualifier("School_DB.ResultsService")
	private ResultsService resultsService;

    @Autowired
    @Qualifier("School_DB.TestConductedDao")
    private WMGenericDao<TestConducted, TestConductedId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<TestConducted, TestConductedId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "School_DBTransactionManager")
    @Override
	public TestConducted create(TestConducted testConducted) {
        LOGGER.debug("Creating a new TestConducted with information: {}", testConducted);
        TestConducted testConductedCreated = this.wmGenericDao.create(testConducted);
        if(testConductedCreated.getResultses() != null) {
            for(Results resultse : testConductedCreated.getResultses()) {
                resultse.setTestConducted(testConductedCreated);
                LOGGER.debug("Creating a new child Results with information: {}", resultse);
                resultsService.create(resultse);
            }
        }
        return testConductedCreated;
    }

	@Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public TestConducted getById(TestConductedId testconductedId) throws EntityNotFoundException {
        LOGGER.debug("Finding TestConducted by id: {}", testconductedId);
        TestConducted testConducted = this.wmGenericDao.findById(testconductedId);
        if (testConducted == null){
            LOGGER.debug("No TestConducted found with id: {}", testconductedId);
            throw new EntityNotFoundException(String.valueOf(testconductedId));
        }
        return testConducted;
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public TestConducted findById(TestConductedId testconductedId) {
        LOGGER.debug("Finding TestConducted by id: {}", testconductedId);
        return this.wmGenericDao.findById(testconductedId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "School_DBTransactionManager")
	@Override
	public TestConducted update(TestConducted testConducted) throws EntityNotFoundException {
        LOGGER.debug("Updating TestConducted with information: {}", testConducted);
        this.wmGenericDao.update(testConducted);

        TestConductedId testconductedId = new TestConductedId();
        testconductedId.setAcademicYear(testConducted.getAcademicYear());
        testconductedId.setStandardId(testConducted.getStandardId());
        testconductedId.setTestConductedId(testConducted.getTestConductedId());

        return this.wmGenericDao.findById(testconductedId);
    }

    @Transactional(value = "School_DBTransactionManager")
	@Override
	public TestConducted delete(TestConductedId testconductedId) throws EntityNotFoundException {
        LOGGER.debug("Deleting TestConducted with id: {}", testconductedId);
        TestConducted deleted = this.wmGenericDao.findById(testconductedId);
        if (deleted == null) {
            LOGGER.debug("No TestConducted found with id: {}", testconductedId);
            throw new EntityNotFoundException(String.valueOf(testconductedId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public Page<TestConducted> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TestConducteds");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<TestConducted> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TestConducteds");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service School_DB for table TestConducted to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<Results> findAssociatedResultses(String academicYear, Integer standardId, Integer testConductedId, Pageable pageable) {
        LOGGER.debug("Fetching all associated resultses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("testConducted.academicYear = '" + academicYear + "'");
        queryBuilder.append(" and ");
        queryBuilder.append("testConducted.standardId = '" + standardId + "'");
        queryBuilder.append(" and ");
        queryBuilder.append("testConducted.testConductedId = '" + testConductedId + "'");

        return resultsService.findAll(queryBuilder.toString(), pageable);
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

