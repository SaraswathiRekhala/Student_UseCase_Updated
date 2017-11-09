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

import com.student_usecase.school_db.AcademicTestSubjects;
import com.student_usecase.school_db.AcademicTestSubjectsId;
import com.student_usecase.school_db.TestConducted;


/**
 * ServiceImpl object for domain model class AcademicTestSubjects.
 *
 * @see AcademicTestSubjects
 */
@Service("School_DB.AcademicTestSubjectsService")
public class AcademicTestSubjectsServiceImpl implements AcademicTestSubjectsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcademicTestSubjectsServiceImpl.class);

    @Autowired
	@Qualifier("School_DB.TestConductedService")
	private TestConductedService testConductedService;

    @Autowired
    @Qualifier("School_DB.AcademicTestSubjectsDao")
    private WMGenericDao<AcademicTestSubjects, AcademicTestSubjectsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<AcademicTestSubjects, AcademicTestSubjectsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "School_DBTransactionManager")
    @Override
	public AcademicTestSubjects create(AcademicTestSubjects academicTestSubjects) {
        LOGGER.debug("Creating a new AcademicTestSubjects with information: {}", academicTestSubjects);
        AcademicTestSubjects academicTestSubjectsCreated = this.wmGenericDao.create(academicTestSubjects);
        if(academicTestSubjectsCreated.getTestConducteds() != null) {
            for(TestConducted testConducted : academicTestSubjectsCreated.getTestConducteds()) {
                testConducted.setAcademicTestSubjects(academicTestSubjectsCreated);
                LOGGER.debug("Creating a new child TestConducted with information: {}", testConducted);
                testConductedService.create(testConducted);
            }
        }
        return academicTestSubjectsCreated;
    }

	@Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public AcademicTestSubjects getById(AcademicTestSubjectsId academictestsubjectsId) throws EntityNotFoundException {
        LOGGER.debug("Finding AcademicTestSubjects by id: {}", academictestsubjectsId);
        AcademicTestSubjects academicTestSubjects = this.wmGenericDao.findById(academictestsubjectsId);
        if (academicTestSubjects == null){
            LOGGER.debug("No AcademicTestSubjects found with id: {}", academictestsubjectsId);
            throw new EntityNotFoundException(String.valueOf(academictestsubjectsId));
        }
        return academicTestSubjects;
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public AcademicTestSubjects findById(AcademicTestSubjectsId academictestsubjectsId) {
        LOGGER.debug("Finding AcademicTestSubjects by id: {}", academictestsubjectsId);
        return this.wmGenericDao.findById(academictestsubjectsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "School_DBTransactionManager")
	@Override
	public AcademicTestSubjects update(AcademicTestSubjects academicTestSubjects) throws EntityNotFoundException {
        LOGGER.debug("Updating AcademicTestSubjects with information: {}", academicTestSubjects);
        this.wmGenericDao.update(academicTestSubjects);

        AcademicTestSubjectsId academictestsubjectsId = new AcademicTestSubjectsId();
        academictestsubjectsId.setAcademicYear(academicTestSubjects.getAcademicYear());
        academictestsubjectsId.setStandardId(academicTestSubjects.getStandardId());
        academictestsubjectsId.setSubjectId(academicTestSubjects.getSubjectId());
        academictestsubjectsId.setTestId(academicTestSubjects.getTestId());

        return this.wmGenericDao.findById(academictestsubjectsId);
    }

    @Transactional(value = "School_DBTransactionManager")
	@Override
	public AcademicTestSubjects delete(AcademicTestSubjectsId academictestsubjectsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting AcademicTestSubjects with id: {}", academictestsubjectsId);
        AcademicTestSubjects deleted = this.wmGenericDao.findById(academictestsubjectsId);
        if (deleted == null) {
            LOGGER.debug("No AcademicTestSubjects found with id: {}", academictestsubjectsId);
            throw new EntityNotFoundException(String.valueOf(academictestsubjectsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public Page<AcademicTestSubjects> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all AcademicTestSubjects");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<AcademicTestSubjects> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all AcademicTestSubjects");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service School_DB for table AcademicTestSubjects to {} format", exportType);
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
    public Page<TestConducted> findAssociatedTestConducteds(String academicYear, Integer standardId, Integer subjectId, Integer testId, Pageable pageable) {
        LOGGER.debug("Fetching all associated testConducteds");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("academicTestSubjects.academicYear = '" + academicYear + "'");
        queryBuilder.append(" and ");
        queryBuilder.append("academicTestSubjects.standardId = '" + standardId + "'");
        queryBuilder.append(" and ");
        queryBuilder.append("academicTestSubjects.subjectId = '" + subjectId + "'");
        queryBuilder.append(" and ");
        queryBuilder.append("academicTestSubjects.testId = '" + testId + "'");

        return testConductedService.findAll(queryBuilder.toString(), pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TestConductedService instance
	 */
	protected void setTestConductedService(TestConductedService service) {
        this.testConductedService = service;
    }

}

