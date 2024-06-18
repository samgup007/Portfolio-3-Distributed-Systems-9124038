package de.fhws.fiw.fds.demo.server.database.models;

import java.time.LocalDate;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "university")
public class UniversityDB extends AbstractDBModel {

    @Column(name = "universityRank")
    private int universityRank;

    @Column(name = "universityName")
    private String universityName;

    @Column(name = "country")
    private String country;

    @Column(name = "departmentName")
    private String departmentName;

    @Column(name = "departmentWebsite")
    private String departmentWebsite;

    @Column(name = "contactPerson")
    private String contactPerson;

    @Column(name = "studentsOutgoing")
    private int studentsOutgoing;

    @Column(name = "studentsIncoming")
    private int studentsIncoming;

    @Column(name = "nextSpringSemesterStart")
    private LocalDate nextSpringSemesterStart;

    @Column(name = "nextAutumnSemesterStart")
    private LocalDate nextAutumnSemesterStart;

    @Column(name = "annualTuitionFees")
    private double annualTuitionFees;

    public UniversityDB() {
    }

    public int getUniversityRank() {
        return universityRank;
    }

    public void setUniversityRank(int universityRank) {
        this.universityRank = universityRank;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentWebsite() {
        return departmentWebsite;
    }

    public void setDepartmentWebsite(String departmentWebsite) {
        this.departmentWebsite = departmentWebsite;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getStudentsOutgoing() {
        return studentsOutgoing;
    }

    public void setStudentsOutgoing(int studentsOutgoing) {
        this.studentsOutgoing = studentsOutgoing;
    }

    public int getStudentsIncoming() {
        return studentsIncoming;
    }

    public void setStudentsIncoming(int studentsIncoming) {
        this.studentsIncoming = studentsIncoming;
    }

    public LocalDate getNextSpringSemesterStart() {
        return nextSpringSemesterStart;
    }

    public void setNextSpringSemesterStart(LocalDate nextSpringSemesterStart) {
        this.nextSpringSemesterStart = nextSpringSemesterStart;
    }

    public LocalDate getNextAutumnSemesterStart() {
        return nextAutumnSemesterStart;
    }

    public void setNextAutumnSemesterStart(LocalDate nextAutumnSemesterStart) {
        this.nextAutumnSemesterStart = nextAutumnSemesterStart;
    }

    public double getAnnualTuitionFees() {
        return annualTuitionFees;
    }

    public void setAnnualTuitionFees(double annualTuitionFees) {
        this.annualTuitionFees = annualTuitionFees;
    }
}
