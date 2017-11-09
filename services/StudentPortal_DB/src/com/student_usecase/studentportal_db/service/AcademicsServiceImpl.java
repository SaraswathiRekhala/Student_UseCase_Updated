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

import com.student_usecase.studentportal_db.Academics;
import com.student_usecase.studentportal_db.AcademicsId;


/**
 * ServiceImpl object for domain model class Academics.
 *
 * @see Academics
 */
@Service("StudentPortal_DB.AcademicsService")
public class AcademicsServiceImpl implements AcademicsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcademicsServiceImpl.class);


    @Autowired
    @Qualifier("StudentPortal_DB.AcademicsDao")
    private WMGenericDao<Academics, AcademicsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Academics, AcademicsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
    @Override
	public Academics create(Academics academics) {
        LOGGER.debug("Creating a new Academics with information: {}", academics);
        Academics academicsCreated = this.wmGenericDao.create(academics);
        return academicsCreated;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public Academics getById(AcademicsId academicsId) throws EntityNotFoundException {
        LOGGER.debug("Finding Academics by id: {}", academicsId);
        Academics academics = this.wmGenericDao.findById(academicsId);
        if (academics == null){
            LOGGER.debug("No Academics found with id: {}", academicsId);
            throw new EntityNotFoundException(String.valueOf(academicsId));
        }
        return academics;
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public Academics findById(AcademicsId academicsId) {
        LOGGER.debug("Finding Academics by id: {}", academicsId);
        return this.wmGenericDao.findById(academicsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "StudentPortal_DBTransactionManager")
	@Override
	public Academics update(Academics academics) throws EntityNotFoundException {
        LOGGER.debug("Updating Academics with information: {}", academics);
        this.wmGenericDao.update(academics);

        AcademicsId academicsId = new AcademicsId();
        academicsId.setAcademicYear(academics.getAcademicYear());
        academicsId.setClassroom(academics.getClassroom());
        academicsId.setClassteacher(academics.getClassteacher());
        academicsId.setStandard(academics.getStandard());

        return this.wmGenericDao.findById(academicsId);
    }

    @Transactional(value = "StudentPortal_DBTransactionManager")
	@Override
	public Academics delete(AcademicsId academicsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Academics with id: {}", academicsId);
        Academics deleted = this.wmGenericDao.findById(academicsId);
        if (deleted == null) {
            LOGGER.debug("No Academics found with id: {}", academicsId);
            throw new EntityNotFoundException(String.valueOf(academicsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
	@Override
	public Page<Academics> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Academics");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Page<Academics> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Academics");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "StudentPortal_DBTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service StudentPortal_DB for table Academics to {} format", exportType);
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
