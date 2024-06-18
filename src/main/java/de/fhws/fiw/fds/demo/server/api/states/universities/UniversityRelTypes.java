package de.fhws.fiw.fds.demo.server.api.states.universities;

public interface UniversityRelTypes {
    
    // Relationship types for university operations
    String CREATE_UNIVERSITY = "createUniversity";
    String GET_ALL_UNIVERSITIES = "getAllUniversities";
    String GET_SINGLE_UNIVERSITY = "getSingleUniversity";
    String FILTER_BY_NAME_AND_COUNTRY = "filterByNameAndCountry";
    String FILTER_BY_NAME_AND_COUNTRY_REVERSED = "filterByNameAndCountryReversed";
    String UPDATE_SINGLE_UNIVERSITY = "updateUniversity";
    String DELETE_SINGLE_UNIVERSITY = "deleteUniversity";
    String GET_ALL_UNIVERSITIES_REVERSED = "getAllUniversitiesReversed";
    
}
