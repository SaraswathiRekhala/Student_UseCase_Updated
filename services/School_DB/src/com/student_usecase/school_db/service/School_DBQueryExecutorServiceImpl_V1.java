/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/

package com.student_usecase.school_db.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.query.WMQueryExecutor;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.file.model.Downloadable;

import com.student_usecase.school_db.models.query.*;

@Service
public class School_DBQueryExecutorServiceImpl_V1 implements School_DBQueryExecutorService_V1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(School_DBQueryExecutorServiceImpl.class);

    @Autowired
    @Qualifier("School_DBWMQueryExecutor")
    private WMQueryExecutor queryExecutor;

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<Object> executeSV_GetSTudentResultsForStandard(Pageable pageable, String academicYear, Integer standardid, Integer testid) {
        Map params = new HashMap(3);

        params.put("academicYear", academicYear);
        params.put("standardid", standardid);
        params.put("testid", testid);

        return queryExecutor.executeNamedQuery("SV_GetSTudentResultsForStandard", params, Object.class, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<Object> executeSV_CountOfStudents(Pageable pageable, Integer standard, String year) {
        Map params = new HashMap(2);

        params.put("standard", standard);
        params.put("year", year);

        return queryExecutor.executeNamedQuery("SV_CountOfStudents", params, Object.class, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<Object> executeSV_Top3StudentsFromAllStandards(Pageable pageable, Integer testid, String academicyear) {
        Map params = new HashMap(2);

        params.put("TESTID", testid);
        params.put("ACADEMICYEAR", academicyear);

        return queryExecutor.executeNamedQuery("SV_Top3StudentsFromAllStandards", params, Object.class, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<Object> executeSV_AcademicSubjectsByStandard(Pageable pageable, String year, Integer standard) {
        Map params = new HashMap(2);

        params.put("year", year);
        params.put("standard", standard);

        return queryExecutor.executeNamedQuery("SV_AcademicSubjectsByStandard", params, Object.class, pageable);
    }

    @Transactional(readOnly = true, value = "School_DBTransactionManager")
    @Override
    public Page<Object> executeSV_CountOfStudentPerGrade(Pageable pageable, String academicYear, Integer standardid) {
        Map params = new HashMap(2);

        params.put("academicYear", academicYear);
        params.put("standardid", standardid);

        return queryExecutor.executeNamedQuery("SV_CountOfStudentPerGrade", params, Object.class, pageable);
    }

}


