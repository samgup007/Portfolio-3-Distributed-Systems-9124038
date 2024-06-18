package de.fhws.fiw.fds.demo.server.database.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "modules")
public class ModuleDB extends AbstractDBModel {

    @Column(name = "moduleName")
    private String moduleName;

    @Column(name = "semesterOffered")
    private int semesterOffered; // 1 for spring, 2 for autumn

    @Column(name = "creditPoints")
    private int creditPoints; // Must be > 0

    @Column(name = "instructorName")
    private String instructorName;

    @Column(name = "description")
    private String description;

    public ModuleDB() {
        // make JPA happy
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getSemesterOffered() {
        return semesterOffered;
    }

    public void setSemesterOffered(int semesterOffered) {
        this.semesterOffered = semesterOffered;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
