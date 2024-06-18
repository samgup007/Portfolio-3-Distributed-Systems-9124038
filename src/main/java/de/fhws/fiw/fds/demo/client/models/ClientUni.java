package de.fhws.fiw.fds.demo.client.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientUni extends AbstractClientModel {

    private String universityName;
    private String country;
    private String departmentName;
    private String departmentWebsite;
    private String contactPerson;
    private int studentsOutgoing;
    private int studentsIncoming;
    private LocalDate nextSpringSemesterStart;
    private LocalDate nextAutumnSemesterStart;
    private double annualTuitionFees;
    private int universityRank;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link selfLink;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link modules;

    public ClientUni() {
    }

    public ClientUni(int universityRank, double annualTuitionFees, LocalDate nextAutumnSemesterStart,
    LocalDate nextSpringSemesterStart, int studentsIncoming, int studentsOutgoing,
    String contactPerson, String departmentWebsite, String departmentName, String country,
    String universityName) {
        this.universityRank = universityRank;
        this.annualTuitionFees = annualTuitionFees;
        this.nextAutumnSemesterStart = nextAutumnSemesterStart;
        this.nextSpringSemesterStart = nextSpringSemesterStart;
        this.studentsIncoming = studentsIncoming;
        this.studentsOutgoing = studentsOutgoing;
        this.contactPerson = contactPerson;
        this.departmentWebsite = departmentWebsite;
        this.departmentName = departmentName;
        this.country = country;
        this.universityName = universityName;
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

    public int getUniversityRank() {
        return universityRank;
    }

    public void setUniversityRank(int universityRank) {
        this.universityRank = universityRank;
    }

    @JsonIgnore
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonIgnore
    public Link getModules() {
        return modules;
    }

    public void setModules(Link modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "UniversityClientModel{" +
                "universityName='" + universityName + '\'' +
                ", departmentWebsite='" + departmentWebsite + '\'' +
                ", nextSpringSemesterStart=" + nextSpringSemesterStart +
                ", nextAutumnSemesterStart=" + nextAutumnSemesterStart +
                ", annualTuitionFees=" + annualTuitionFees +
                ", universityRank=" + universityRank +
                ", contactPerson='" + contactPerson + '\'' +
                ", studentsOutgoing=" + studentsOutgoing +
                ", studentsIncoming=" + studentsIncoming +
                ", selfLink=" + selfLink +
                ", modules=" + modules +
                ", country='" + country + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
