/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.school_db.models.query;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wavemaker.runtime.data.annotations.ColumnAlias;

public class SvCountOfStudentPerGradeResponse implements Serializable {

    @JsonProperty("grade")
    @ColumnAlias("grade")
    private String grade;
    @JsonProperty("gradeId")
    @ColumnAlias("gradeId")
    private Integer gradeId;
    @JsonProperty("studentId")
    @ColumnAlias("studentId")
    private BigInteger studentId;

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getGradeId() {
        return this.gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public BigInteger getStudentId() {
        return this.studentId;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SvCountOfStudentPerGradeResponse)) return false;
        final SvCountOfStudentPerGradeResponse svCountOfStudentPerGradeResponse = (SvCountOfStudentPerGradeResponse) o;
        return Objects.equals(getGrade(), svCountOfStudentPerGradeResponse.getGrade()) &&
                Objects.equals(getGradeId(), svCountOfStudentPerGradeResponse.getGradeId()) &&
                Objects.equals(getStudentId(), svCountOfStudentPerGradeResponse.getStudentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGrade(),
                getGradeId(),
                getStudentId());
    }
}
