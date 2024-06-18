package de.fhws.fiw.fds.demo.client.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientModule extends AbstractClientModel {

    private String moduleName;
    private int semesterOffered;
    private int creditPoints; 
    private String instructorName;
    private String description;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private transient Link selfLinkOnSecond;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private transient Link selfLink;

    public ClientModule() {
    }

    public ClientModule(String moduleName, int semesterOffered, int creditPoints, String instructorName, String description) {
        this.moduleName = moduleName;
        this.semesterOffered = semesterOffered;
        this.creditPoints = creditPoints;
        this.instructorName = instructorName;
        this.description = description;
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

    public String getModuleName() {
        return moduleName;
    }


    public int getSemesterOffered() {
        return semesterOffered;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public void setSelfLinkOnSecond(Link selfLinkOnSecond) {
        this.selfLinkOnSecond = selfLinkOnSecond;
    }

    @JsonIgnore
    public Link getSelfLink() {
        return selfLink;
    }

    @JsonIgnore
    public Link getSelfLinkOnSecond() {
        return selfLinkOnSecond;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }
}
