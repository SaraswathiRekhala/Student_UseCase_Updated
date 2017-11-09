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

import com.student_usecase.school_db.AcademicSubjects;
import com.student_usecase.school_db.SubjectDetails;


/**
 * ServiceImpl object for domain model class SubjectDetails.
 *
 * @see SubjectDetails
 */
@Service("School_DB.SubjectDetailsService")
public class SubjectDetailsServiceImpl implements SubjectDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDetailsServiceImpl.class);

    @Autowired
	@Qualifier("School_DB.AcademicSubjectsService")
	private AcademicSubjectsService academicSubjectsService;

    @Autowired
    @Qualifier("School_DB.SubjectDetailsDao")
    private WMGenericDao<SubjectDetails, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<SubjectDetails, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "School_DBTransactionManager")
    @Override
	public SubjectDetails create(SubjectDetails subjectDetails) {
        LOGGER.debug("Creating a new SubjectDetails with information: {}", subjectDetails);
        SubjectDetails subjectDetailsCreated = this.wmGenericDao.create(subjectDetails);
        if(subjectDetailsCreated.getAcademicSubjectses() != null) {
            for(AcademicSubjects academicSubjectse : subjectDetailsCreated.getAcademicSubjectses()) {
                academicSubjectse.setSubjectDetails(subjectDetailsCreated);
                LOGGER.debug("Creating a new child AcademicSubjects with information: {}", academicSubjectse);
                academicSubjectsService.create(academicSubjectse);
            }
        }
        return subjectDetailsCreated;
    }

	@Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public SubjectDetails getById(Integer subjectdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Finding SubjectDetails by id: {}", subjectdetailsId);
        SubjectDetails subjectDetails = this.wmGenericDao.findById(subjectdetailsId);
        if (subjectDetails == null){
            LOGGER.debug("No SubjectDetails found with id: {}", subjectdetailsId);
            throw new EntityNotFoundException(String.valueOf(subjectdetailsId));
        }
        return subjectDetails;
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public SubjectDetails findById(Integer subjectdetailsId) {
        LOGGER.debug("Finding SubjectDetails by id: {}", subjectdetailsId);
        return this.wmGenericDao.findById(subjectdetailsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "School_DBTransactionManager")
	@Override
	public SubjectDetails update(SubjectDetails subjectDetails) throws EntityNotFoundException {
        LOGGER.debug("Updating SubjectDetails with information: {}", subjectDetails);
        this.wmGenericDao.update(subjectDetails);

        Integer subjectdetailsId = subjectDetails.getSubjectId();

        return this.wmGenericDao.findById(subjectdetailsId);
    }

    @Transactional(value = "School_DBTransactionManager")
	@Override
	public SubjectDetails delete(Integer subjectdetailsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting SubjectDetails with id: {}", subjectdetailsId);
        SubjectDetails deleted = this.wmGenericDao.findById(subjectdetailsId);
        if (deleted == null) {
            LOGGER.debug("No SubjectDetails found with id: {}", subjectdetailsId);
            throw new EntityNotFoundException(String.valueOf(subjectdetailsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "School_DBTransactionManager")
	@Override
	public Page<SubjectDetails> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all SubjectDetails");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<SubjectDetails> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all SubjectDetails");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service School_DB for table SubjectDetails to {} format", exportType);
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
    public Page<AcademicSubjects> findAssociatedAcademicSubjectses(Integer subjectId, Pageable pageable) {
        LOGGER.debug("Fetching all associated academicSubjectses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("subjectDetails.subjectId = '" + subjectId + "'");

        return academicSubjectsService.findAll(queryBuilder.toString(), pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service AcademicSubjectsService instance
	 */
	protected void setAcademicSubjectsService(AcademicSubjectsService service) {
        this.academicSubjectsService = service;
    }

}

