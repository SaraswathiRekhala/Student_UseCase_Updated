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

import com.student_usecase.studentportal_db.StandardDetails;
import com.student_usecase.studentportal_db.StandardDetailsId;


/**
 * ServiceImpl object for domain model class StandardDetails.
 *
 * @see StandardDetails
 */
@Service("StudentPortal_DB.StandardDetailsService")
public class StandardDetailsServiceImpl implements StandardDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardDetailsServiceImpl.class);


    @Autowired
    @Qualifier("StudentPortal_DB.StandardDetailsDao")
    private WMGenericDao<StandardDetails, StandardDetailsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<StandardDetails, StandardDetailsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
    @Override
	public StandardDetails create(StandardDetails standardDetails) {
        LOGGER.debug("Creating a new StandardDetails with information: {}", standardDetails);
        StandardDetails standardDetailsCreated = this.wmGenericDao.create(standardDetails);
        return standardDetailsCreated;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public StandardDetails getById(StandardDetailsId standarddetailsId) throws EntityNotFoundException {
        LOGGER.debug("Finding StandardDetails by id: {}", standarddetailsId);
        StandardDetails standardDetails = this.wmGenericDao.findById(standarddetailsId);
        if (standardDetails == null){
            LOGGER.debug("No StandardDetails found with id: {}", standarddetailsId);
            throw new EntityNotFoundException(String.valueOf(standarddetailsId));
        }
        return standardDetails;
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public StandardDetails findById(StandardDetailsId standarddetailsId) {
        LOGGER.debug("Finding StandardDetails by id: {}", standarddetailsId);
        return this.wmGenericDao.findById(standarddetailsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "StudentPortal_DBTransactionManager")
	@Override
	public StandardDetails update(StandardDetails standardDetails) throws EntityNotFoundException {
        LOGGER.debug("Updating StandardDetails with information: {}", standardDetails);
        this.wmGenericDao.update(standardDetails);

        StandardDetailsId standarddetailsId = new StandardDetailsId();
        standarddetailsId.setStandardId(standardDetails.getStandardId());
        standarddetailsId.setStandardName(standardDetails.getStandardName());

        return this.wmGenericDao.findById(standarddetailsId);
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
	@Override
	public StandardDetails delete(StandardDetailsId standarddetailsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting StandardDetails with id: {}", standarddetailsId);
        StandardDetails deleted = this.wmGenericDao.findById(standarddetailsId);
        if (deleted == null) {
            LOGGER.debug("No StandardDetails found with id: {}", standarddetailsId);
            throw new EntityNotFoundException(String.valueOf(standarddetailsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public Page<StandardDetails> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all StandardDetails");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Page<StandardDetails> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all StandardDetails");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service StudentPortal_DB for table StandardDetails to {} format", exportType);
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

