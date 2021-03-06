/*Copyright (c) 2016-2017 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.student_usecase.studentportal_db;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

public class StudentDetailsId implements Serializable {

    private String address;
    private BigInteger contactNumber;
    private Date dateofbirth;
    private String fatherName;
    private Date joiningDate;
    private Integer studentId;
    private String studentName;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(BigInteger contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getDateofbirth() {
        return this.dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Date getJoiningDate() {
        return this.joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentDetails)) return false;
        final StudentDetails studentDetails = (StudentDetails) o;
        return Objects.equals(getAddress(), studentDetails.getAddress()) &&
                Objects.equals(getContactNumber(), studentDetails.getContactNumber()) &&
                Objects.equals(getDateofbirth(), studentDetails.getDateofbirth()) &&
                Objects.equals(getFatherName(), studentDetails.getFatherName()) &&
                Objects.equals(getJoiningDate(), studentDetails.getJoiningDate()) &&
                Objects.equals(getStudentId(), studentDetails.getStudentId()) &&
                Objects.equals(getStudentName(), studentDetails.getStudentName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(),
                getContactNumber(),
                getDateofbirth(),
                getFatherName(),
                getJoiningDate(),
                getStudentId(),
                getStudentName());
    }
}
