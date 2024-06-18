package de.fhws.fiw.fds.demo.server.api.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

@JsonRootName("university")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "university")
public class Uni extends AbstractModel {

    
    private String departmentName;
    private int studentsOutgoing;
    private int universityRank;
    private String universityName;
    private String country;
    private int studentsIncoming;
    private String departmentWebsite;
    private String contactPerson;
    private LocalDate nextSpringSemesterStart;
    private LocalDate nextAutumnSemesterStart;
    private double annualTuitionFees;

    @SuttonLink(
            value = "universities/${id}",
            rel = "self"
    )
    private transient Link selfLink;

    @SuttonLink(
            value = "universities/${id}/modules",
            rel = "getModulesOfUniversity"
    )
    private transient Link modules;

    public Uni() {
        // make JPA happy
    }

    public Uni(int universityRank, double annualTuitionFees, LocalDate nextAutumnSemesterStart, LocalDate nextSpringSemesterStart, int studentsIncoming, int studentsOutgoing, String contactPerson, String departmentWebsite, String departmentName, String country, String universityName)
    {
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

    public String getUniversityName()
    {
        return universityName;
    }

    public void setUniversityName(String universityName)
    {
        this.universityName = universityName;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentWebsite()
    {
        return departmentWebsite;
    }

    public void setDepartmentWebsite(String departmentWebsite)
    {
        this.departmentWebsite = departmentWebsite;
    }

    public String getContactPerson()
    {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson)
    {
        this.contactPerson = contactPerson;
    }

    public int getStudentsOutgoing()
    {
        return studentsOutgoing;
    }

    public void setStudentsOutgoing(int studentsOutgoing)
    {
        this.studentsOutgoing = studentsOutgoing;
    }

    public int getStudentsIncoming()
    {
        return studentsIncoming;
    }

    public void setStudentsIncoming(int studentsIncoming)
    {
        this.studentsIncoming = studentsIncoming;
    }

    public LocalDate getNextSpringSemesterStart()
    {
        return nextSpringSemesterStart;
    }

    public void setNextSpringSemesterStart(LocalDate nextSpringSemesterStart)
    {
        this.nextSpringSemesterStart = nextSpringSemesterStart;
    }

    public LocalDate getNextAutumnSemesterStart()
    {
        return nextAutumnSemesterStart;
    }

    public void setNextAutumnSemesterStart(LocalDate nextAutumnSemesterStart)
    {
        this.nextAutumnSemesterStart = nextAutumnSemesterStart;
    }

    public double getAnnualTuitionFees()
    {
        return annualTuitionFees;
    }

    public void setAnnualTuitionFees(double annualTuitionFees)
    {
        this.annualTuitionFees = annualTuitionFees;
    }

    public int getUniversityRank()
    {
        return universityRank;
    }

    public void setUniversityRank(int universityRank)
    {
        this.universityRank = universityRank;
    }

    public Link getSelfLink()
    {
        return selfLink;
    }

    public void setSelfLink(Link selfLink)
    {
        this.selfLink = selfLink;
    }

    public Link getModules()
    {
        return modules;
    }

    public void setModules(Link modules)
    {
        this.modules = modules;
    }

    public static void validateUniversity(Uni university) throws SuttonWebAppException
    {
        if (university.getStudentsIncoming() < 0 ) {
            throw new SuttonWebAppException(Status.BAD_REQUEST, "There can't be less than 0 students incoming");
        }

        if (university.getStudentsOutgoing() < 0 ) {
            throw new SuttonWebAppException(Status.BAD_REQUEST, "There can't be less than 0 students outgoing");
        }

        if(university.getUniversityRank() < 0) {
            throw new SuttonWebAppException(Status.BAD_REQUEST, "Ranking can't be negative");
        }
    }

    @Override
    public String toString()
    {
        return "University{" +
                "universityName='" + universityName + '\'' +
                ", country='" + country + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentWebsite='" + departmentWebsite + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", studentsOutgoing=" + studentsOutgoing +
                ", studentsIncoming=" + studentsIncoming +
                ", nextSpringSemesterStart=" + nextSpringSemesterStart +
                ", nextAutumnSemesterStart=" + nextAutumnSemesterStart +
                ", annualTuitionFees=" + annualTuitionFees +
                ", universityRank=" + universityRank +
                '}';
    }
}
