package de.fhws.fiw.fds.demo.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SecondarySelfLink;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

@JsonRootName("module")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "module")
public class Module extends AbstractModel {

    private String moduleName;
    private int semesterOffered; 
    private int creditPoints; 
    private String instructorName;
    private String description;

    @SecondarySelfLink(
            primaryPathElement = "universities",
            secondaryPathElement = "modules"
    )
    private transient Link selfLinkOnSecond;

    @SelfLink(pathElement = "modules")
    private transient Link selfLink;

    public Module() {
    }

    public Module(String moduleName, int semesterOffered, int creditPoints,
    String instructorName, String description) {
        this.moduleName = moduleName;
        this.semesterOffered = semesterOffered;
        this.creditPoints = creditPoints;
        this.instructorName = instructorName;
        this.description = description;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getDescription() {
        return description;
    }

    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    public Link getSelfLinkOnSecond() {
        return selfLinkOnSecond;
    }

    public void setSelfLinkOnSecond(Link selfLinkOnSecond) {
        this.selfLinkOnSecond = selfLinkOnSecond;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setSemesterOffered(int semesterOffered) {
        this.semesterOffered = semesterOffered;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getSemesterOffered() {
        return semesterOffered;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static void validateModule(Module module) throws SuttonWebAppException {
        if (module.getSemesterOffered() != 1 && module.getSemesterOffered() != 2) {
            throw new SuttonWebAppException(Status.BAD_REQUEST, "Invalid semesterOffered. It must be 1 for spring or 2 for autumn.");
        }

        if (module.getCreditPoints() <= 0) {
            throw new SuttonWebAppException(Status.BAD_REQUEST, "Invalid creditPoints. It must be greater than 0.");
        }
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", semesterOffered=" + semesterOffered +
                ", description='" + description + '\'' +
                ", creditPoints=" + creditPoints +
                ", instructorName='" + instructorName + '\'' +
                '}';
    }
}
