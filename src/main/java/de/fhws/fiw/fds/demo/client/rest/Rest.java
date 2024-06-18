package de.fhws.fiw.fds.demo.client.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.fhws.fiw.fds.demo.client.models.ClientModule;
import de.fhws.fiw.fds.demo.client.models.ClientUni;
import de.fhws.fiw.fds.demo.client.web.ClientModuleWeb;
import de.fhws.fiw.fds.demo.client.web.ClientUniWeb;
import de.fhws.fiw.fds.sutton.client.rest2.AbstractRestClient;

public class Rest extends AbstractRestClient {
    private static final String CHANGEABLE = "test/api";
    private static final String BASE_URL = "http://localhost:8080/" + CHANGEABLE;



    // Modules 
    private static final String CREATE_MODULE = "createModule";
    private static final String GET_ALL_MODULES = "getAllModules";
    private static final String UPDATE_SINGLE_MODULE = "updateModule";
    private static final String DELETE_SINGLE_MODULE = "deleteModule";
    private static final String GET_SINGLE_MODULE = "getModule";

    private List<ClientUni> currentUniversityData;
    public int cursorUniversityData = 0;
    private List<ClientModule> currentModuleData;
    private int cursorModuleData = 0;

    final private ClientUniWeb universityWebClient;
    final private ClientModuleWeb moduleWebClient;

    public Rest() {
        super();
        this.universityWebClient = new ClientUniWeb();
        this.moduleWebClient = new ClientModuleWeb();
        this.currentModuleData = Collections.EMPTY_LIST;
        this.currentUniversityData = Collections.EMPTY_LIST;
    }

    public boolean isGetAllModulesAllowed() {
        return isLinkAvailable(GET_ALL_MODULES) || !this.currentUniversityData.isEmpty();
    }

    public void getAllModules() throws IOException {
        if (isLinkAvailable(GET_ALL_MODULES)) {
            processResponse(this.moduleWebClient.getCollectionOfModules(getUrl(GET_ALL_MODULES)), (response) -> {
                this.currentModuleData = new LinkedList<>(response.getResponseData());
                this.cursorModuleData = 0;
            });
        } else if (!this.currentUniversityData.isEmpty()) {
            processResponse(this.moduleWebClient.getCollectionOfModules(
                    this.currentUniversityData.get(this.cursorUniversityData).getModules().getUrl()), (response) -> {
                this.currentModuleData = new LinkedList<>(response.getResponseData());
                this.cursorModuleData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    //University
    private static final String CREATE_UNIVERSITY = "createUniversity";
    private static final String GET_ALL_UNIVERSITIES = "getAllUniversities";
    private static final String FILTER_BY_NAME_AND_COUNTRY = "filterByNameAndCountry";
    private static final String FILTER_BY_NAME_AND_COUNTRY_REVERSED = "filterByNameAndCountryReversed";
    private static final String UPDATE_SINGLE_UNIVERSITY = "updateUniversity";
    private static final String DELETE_SINGLE_UNIVERSITY = "deleteUniversity";
    private static final String GET_ALL_UNIVERSITIES_REVERSED = "getAllUniversitiesReversed";

    // Paging
    public boolean isNextAvailable() {
        return isLinkAvailable("next");
    }

    public boolean isPrevAvailable() {
        return isLinkAvailable("prev");
    }
    

    // GET Single Module
    public boolean isGetSingleModuleAllowed() {
        return !this.currentModuleData.isEmpty() ||
                isLocationHeaderAvailable() ||
                isLinkAvailable(GET_SINGLE_MODULE);
    }

    public List<ClientModule> moduleData() {
        return this.currentModuleData;
    }

    public void getSingleModule() throws IOException {
        if (isLocationHeaderAvailable()) {
            getSingleModule(getLocationHeaderURL());
        } else if (isLinkAvailable(GET_SINGLE_MODULE)) {
            getSingleModule(getUrl(GET_SINGLE_MODULE));
        } else if (!this.currentModuleData.isEmpty()) {
            getSingleModule(this.cursorModuleData);
        } else {
            throw new IllegalStateException();
        }
    }

    public void setModuleCursor(int index) {
        if (0 <= index && index < this.currentModuleData.size()) {
            this.cursorModuleData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getSingleModule(int index) throws IOException {
        getSingleModule(this.currentModuleData.get(index).getSelfLink().getUrl());
    }

    private void getSingleModule(String url) throws IOException {
        processResponse(this.moduleWebClient.getSingleModule(url), (response) -> {
            this.currentModuleData = new LinkedList(response.getResponseData());
            this.cursorModuleData = 0;
        });
    }

    //Fill or Start
    public void resetDatabase() throws IOException {
        processResponse(this.universityWebClient.resetDatabaseOnServer(BASE_URL), (response) -> {
        });
    }

    public void fillDatabase() throws IOException {
        processResponse(this.universityWebClient.fillDatabase(BASE_URL), (response) -> {
        });
    }

    public void start() throws IOException {
        processResponse(this.universityWebClient.getDispatcher(BASE_URL), (response) -> {
        });
    }

    //Get All Universities
    public boolean isGetAllUniversitiesAllowed() {
        return isLinkAvailable(GET_ALL_UNIVERSITIES);
    }

    public void getAllUniversities() throws IOException {
        if (isGetAllUniversitiesAllowed()) {
            processResponse(this.universityWebClient.getCollectionOfUniversities(getUrl(GET_ALL_UNIVERSITIES)), (response) -> {
                this.currentUniversityData = new LinkedList<>(response.getResponseData());
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public void getNextPageOfUniversities() throws IOException {
        if (isNextAvailable()) {
            processResponse(this.universityWebClient.getCollectionOfUniversities(getUrl("next")), (response) -> {
                this.currentUniversityData = new LinkedList<>(response.getResponseData());
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException("Next page is not available");
        }
    }

    public boolean isGetAllUniversitiesAllowedReversed() throws IOException {
        return isLinkAvailable(GET_ALL_UNIVERSITIES_REVERSED);
    }

    public void getAllUniversitiesReversed() throws IOException {
        if (isGetAllUniversitiesAllowedReversed()) {
            processResponse(this.universityWebClient.getCollectionOfUniversities(getUrl(GET_ALL_UNIVERSITIES_REVERSED)), (response) -> {
                this.currentUniversityData = new LinkedList<>(response.getResponseData());
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetByNameAndCountryAllowed() {
        return isLinkAvailable(FILTER_BY_NAME_AND_COUNTRY);
    }

    public boolean isGetByNameAndCountryReversedAllowed() {
        return isLinkAvailable(FILTER_BY_NAME_AND_COUNTRY_REVERSED);
    }

    public void getByNameAndCountry(String name, String country) throws IOException {
        if (isGetByNameAndCountryAllowed()) {
            var url = getUrl(FILTER_BY_NAME_AND_COUNTRY);
            url = url.replace("{UNIVERSITYNAME}", name);
            url = url.replace("{COUNTRY}", country);
            processResponse(this.universityWebClient.getCollectionOfUniversities(url), (response) -> {
                this.currentUniversityData = new LinkedList<>(response.getResponseData());
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public void getByNameAndCountryReversed(String name, String country) throws IOException {
        if (isGetByNameAndCountryReversedAllowed()) {
            var url = getUrl(FILTER_BY_NAME_AND_COUNTRY_REVERSED);
            url = url.replace("{UNIVERSITYNAME}", name);
            url = url.replace("{COUNTRY}", country);
            processResponse(this.universityWebClient.getCollectionOfUniversities(url), (response) -> {
                this.currentUniversityData = new LinkedList<>(response.getResponseData());
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    //Get Single University//
    public boolean isGetSingleUniversityAllowed() {
        return !this.currentUniversityData.isEmpty() || isLocationHeaderAvailable();
    }

    public List<ClientUni> universityData() {
        return this.currentUniversityData;
    }

    public void setUniversityCursor(int index) {
        if (0 <= index && index < this.currentUniversityData.size()) {
            this.cursorUniversityData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getSingleUniversity() throws IOException {
        if (isLocationHeaderAvailable()) {
            getSingleUniversity(getLocationHeaderURL());
        } else if (!this.currentUniversityData.isEmpty()) {
            getSingleUniversity(this.cursorUniversityData);
        } else {
            throw new IllegalStateException();
        }
    }

    public void getSingleUniversity(int index) throws IOException {
        getSingleUniversity(this.currentUniversityData.get(index).getSelfLink().getUrl());
    }

    private void getSingleUniversity(String url) throws IOException {
        processResponse(this.universityWebClient.getSingleUniversity(url), (response) -> {
            this.currentUniversityData = new LinkedList(response.getResponseData());
            this.cursorUniversityData = 0;
        });
    }

    //Put Single University//
    public boolean isUpdateUniversityAllowed() {
        return isLinkAvailable(UPDATE_SINGLE_UNIVERSITY);
    }

    public void updateUniversity(ClientUni university) throws IOException {
        if (isUpdateUniversityAllowed()) {
            processResponse(this.universityWebClient.updateUniversity(getUrl(UPDATE_SINGLE_UNIVERSITY), university), (response) -> {
                this.currentUniversityData = Collections.EMPTY_LIST;
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }


    //Post Single University
    public boolean isCreateUniversityAllowed() {
        return isLinkAvailable(CREATE_UNIVERSITY);
    }

    public void createUniversity(ClientUni University) throws IOException {
        if (isCreateUniversityAllowed()) {
            processResponse(this.universityWebClient.postNewUniversity(getUrl(CREATE_UNIVERSITY), University), (response) -> {
                this.currentUniversityData = Collections.EMPTY_LIST;
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    

    //Delete Single University//
    public boolean isDeleteUniversityAllowed() {
        return isLinkAvailable(DELETE_SINGLE_UNIVERSITY);
    }

    public void deleteUniversity() throws IOException {
        if (isDeleteUniversityAllowed()) {
            processResponse(this.universityWebClient.deleteUniversity(getUrl(DELETE_SINGLE_UNIVERSITY)), (response) -> {
                this.currentUniversityData = Collections.EMPTY_LIST;
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    //Module Update
    public boolean isUpdateModuleAllowed() {
        return isLinkAvailable(UPDATE_SINGLE_MODULE);
    }

    public void updateModule(ClientModule module) throws IOException {
        if (isUpdateModuleAllowed()) {
            processResponse(this.moduleWebClient.putNewModule(getUrl(UPDATE_SINGLE_MODULE), module), (response) -> {
                this.currentModuleData = Collections.EMPTY_LIST;
                this.cursorModuleData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    //Create Module
    public boolean isCreateModuleAllowed() {
        return isLinkAvailable(CREATE_MODULE);
    }

    public void createModule(ClientModule module) throws IOException {
        if (isCreateModuleAllowed()) {
            processResponse(this.moduleWebClient.postNewModule(getUrl(CREATE_MODULE), module), (response) -> {
                this.currentModuleData = Collections.EMPTY_LIST;
                this.cursorModuleData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    //Delete Module
    public boolean isDeleteModuleAllowed() {
        return isLinkAvailable(DELETE_SINGLE_MODULE);
    }

    public void deleteModule() throws IOException {
        if (isDeleteModuleAllowed()) {
            processResponse(this.moduleWebClient.deleteModule(getUrl(DELETE_SINGLE_MODULE)), (response) -> {
                this.currentModuleData = Collections.EMPTY_LIST;
                this.cursorModuleData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }
}
