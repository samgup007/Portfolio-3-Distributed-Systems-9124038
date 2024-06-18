package de.fhws.fiw.fds.sutton.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.fhws.fiw.fds.demo.client.models.ClientModule;
import de.fhws.fiw.fds.demo.client.models.ClientUni;
import de.fhws.fiw.fds.demo.client.rest.Rest;


public class PortfolioTest
{
    private Rest client;


    private static @NotNull ClientModule getModuleExample() {
        var module = new ClientModule();
        module.setModuleName("TestModule");
        module.setCreditPoints(5);
        module.setSemesterOffered(2);
        module.setInstructorName("Test Instructor");
        module.setDescription("Test Module Description");
        return module;
    }

    private static @NotNull ClientUni getUniversityExample() {
        var university = new ClientUni();
        university.setUniversityName("TestUni");
        university.setCountry("TestLand");
        university.setDepartmentName("Test Science");
        university.setDepartmentWebsite("http://testuni.testland/cs");
        university.setContactPerson("Test Doe");
        university.setStudentsOutgoing(100);
        university.setStudentsIncoming(150);
        university.setNextSpringSemesterStart(LocalDate.of(2025, 3, 1));
        university.setNextAutumnSemesterStart(LocalDate.of(2025, 9, 1));
        university.setAnnualTuitionFees(1500.0);
        university.setUniversityRank(96);
        return university;
    }

    @BeforeEach
    public void setUp() throws IOException {
        this.client = new Rest();
        this.client.resetDatabase();
    }

    // CREATE UNIVERSITY
    @Test void create_uni_test() throws IOException {
        // GET ALL
        client.start();
        client.getAllUniversities();

        // CREATE
        var university = getUniversityExample();
        assertTrue(client.isCreateUniversityAllowed());
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
    }

    // CREATE UNIVERSITY WRONG INPUT 1
    @Test void create_uni_falseinput_incoming_test() throws IOException {
        // GET ALL
        client.start();
        client.getAllUniversities();

        // CREATE
        var university = getUniversityExample();
        university.setStudentsIncoming(-1);
        assertTrue(client.isCreateUniversityAllowed());
        client.createUniversity(university);
        assertEquals(400, client.getLastStatusCode());
    }

    // CREATE UNIVERSITY WRONG INPUT 2
    @Test void create_uni_falseinput_outgoing_test() throws IOException {
        // GET ALL
        client.start();
        client.getAllUniversities();

        // CREATE
        var university = getUniversityExample();
        university.setStudentsOutgoing(-1);
        assertTrue(client.isCreateUniversityAllowed());
        client.createUniversity(university);
        assertEquals(400, client.getLastStatusCode());
    }

    // GET DISPATCHER
    @Test public void dispatcher_availability() throws IOException {
        client.start();
        assertEquals(200, client.getLastStatusCode());
    }

    // GET ALL UNIVERSITIES
    @Test public void get_all_universities_dispatcher() throws IOException {
        client.start();
        assertTrue(client.isGetAllUniversitiesAllowed());
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
    }

    // GET SINGLE UNIVERSITY
    @Test void create_and_get_university_dispatcher() throws IOException {
        // GET ALL
        client.start();
        client.getAllUniversities();

        // CREATE UNIVERSITY
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        // TEST SINGLE RESULT
        assertTrue( client.isGetSingleUniversityAllowed() );
        client.getSingleUniversity();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(96, client.universityData().getFirst().getUniversityRank());
    }

    // GET ALL UNIVERSITY SIZE CORRECT
    @Test void create_5_universities_and_get_all() throws IOException {

        // CREATE 5 UNIVERSITIES
        for( int i=0; i<5; i++ ) {
            client.start();

            var university = getUniversityExample();

            client.createUniversity(university);
            assertEquals(201, client.getLastStatusCode());
        }

        client.start();
        assertTrue( client.isGetAllUniversitiesAllowed() );
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(5, client.universityData().size());
        
    }

    // GET ALL AND FILTER
    @Test void create_universities_filter_name_and_country() throws IOException {
        /* Create 35 universities "A-Z/1-9 University" to test ordering */
        client.fillDatabase();

        // CREATE UNIVERSITY KNOWN
        var university = getUniversityExample();

        // GET ALL AND CREATE
        client.start();
        client.getAllUniversities();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        // GET ALL AND FILTER BY KNOWN NAME
        client.start();
        client.getAllUniversities();
        assertTrue(client.isGetByNameAndCountryAllowed());
        client.getByNameAndCountry("TestUni","TestLand");
        assertEquals( 1, client.universityData().size() );
    }

    // Descending GET All
    @Test void create_universities_and_display_in_descending() throws IOException {
        /* Create 35 universities "A-Z/1-9 University" to test ordering */
        client.fillDatabase();

        // GET ALL AND ORDER
        client.start();
        client.getAllUniversities();
        assertTrue(client.isGetByNameAndCountryReversedAllowed());
        client.getByNameAndCountryReversed("","");
        assertEquals( "Z University", client.universityData().getFirst().getUniversityName() );

        // Ascending Ordering and GET All
        client.start();
        client.getAllUniversities();
        assertTrue(client.isGetAllUniversitiesAllowedReversed());
        client.getAllUniversitiesReversed();
        assertEquals( "Z University", client.universityData().getFirst().getUniversityName() );
    }

    // GET Second Position Single University
    @Test void create_two_universities_and_get_second() throws IOException {
        // CREATE EXAMPLES
        var university = getUniversityExample();
        var university2 = getUniversityExample();
        university2.setCountry("CountryTwo");

        // CREATE TWO and GET all
        client.start();
        client.getAllUniversities();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
        client.start();
        client.getAllUniversities();
        client.createUniversity(university2);
        assertEquals(201, client.getLastStatusCode());


        // START NEW
        client.start();
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(2, client.universityData().size());

        // TEST SECOND RESULT
        client.setUniversityCursor(1);
        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity(client.cursorUniversityData);
        assertEquals("CountryTwo", client.universityData().getFirst().getCountry());
    }

    // UPDATE UNIVERSITY
    @Test void create_university_and_update() throws IOException {

        // START AND CREATE UNIVERSITY
        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        // GET SINGLE
        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        // UPDATE
        var uniNew = client.universityData().getFirst();
        var newCountry = "ChangedCountry";
        uniNew.setCountry(newCountry);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(204, client.getLastStatusCode());

        // CHECK RESULT
        client.start();
        client.getAllUniversities();
        assertEquals(newCountry, client.universityData().getFirst().getCountry());

    }

    // UPDATE UNIVERSITY WRONG INPUT 1
    @Test void create_and_update_false_outgoing() throws IOException {

        // START AND CREATE UNIVERSITY
        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        // GET SINGLE
        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        // UPDATE
        var uniNew = client.universityData().getFirst();
        var newOutgoing = -1;
        uniNew.setStudentsOutgoing(newOutgoing);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(400, client.getLastStatusCode());

    }

    // UPDATE UNIVERSITY FALSE INPUT 2
    @Test void create_and_update_false_incoming() throws IOException {

        // START AND CREATE UNIVERSITY
        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        // GET SINGLE
        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        // UPDATE
        var uniNew = client.universityData().getFirst();
        var newIncoming = -1;
        uniNew.setStudentsIncoming(newIncoming);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(400, client.getLastStatusCode());
    }

    // UPDATE UNIVERSITY FALSE INPUT 3
    @Test void create_and_update_false_rank() throws IOException {

        // START AND CREATE UNIVERSITY
        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        // GET SINGLE
        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        // UPDATE
        var uniNew = client.universityData().getFirst();
        var newRank = -1;
        uniNew.setUniversityRank(newRank);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(400, client.getLastStatusCode());
    }

    // DELETE UNIVERSITY
    @Test void create_and_delete_one() throws IOException {

        // GET ALL
        client.start();
        client.getAllUniversities();

        // CREATE
        assertTrue(client.isCreateUniversityAllowed());
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        // GET SINGLE
        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();

        // DELETE
        assertTrue(client.isDeleteUniversityAllowed());
        client.deleteUniversity();
        assertEquals(204, client.getLastStatusCode());
        assertTrue(client.isGetAllUniversitiesAllowed());
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(0, client.universityData().size());

    }

    // GET ALL AND CHECK FOR CORRECT PAGING
    @Test void create_and_check_paging() throws IOException {
        // CREATE 70 UNIVERSITIES
        client.fillDatabase();
        client.fillDatabase();
        client.start();

        // GET ALL universities
        assertTrue(client.isGetAllUniversitiesAllowed());
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());

        // Besuchen der zweiten Seite
        assertTrue(client.isNextAvailable());
        client.getNextPageOfUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertTrue(client.isPrevAvailable());
        assertTrue(client.isNextAvailable());
        assertEquals(20, client.getNumberOfResults());
        assertEquals(70, client.getTotalNumberOfResults());

        // Auf letzte Seite gehen
        client.getNextPageOfUniversities();
        client.getNextPageOfUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(10, client.getNumberOfResults());
        assertFalse(client.isNextAvailable());
    }

    // Modules

    private void startAndCreateUniversity() throws IOException {
        client.start();
        client.getAllUniversities();

        var university = getUniversityExample();

        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
        client.getSingleUniversity();
    }

    // GET ALL MODULES
    @Test void create_and_get_all_modules() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET MODULES
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
    }

    // CREATE MODULE
    @Test void create_university_and_module() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET ALL
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        // CREATE MODULE
        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());

        // TEST CREATION
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(1, client.universityData().size());
    }

    // CREATE MODULE FALSE INPUT 1
    @Test void create_university_and_false_module_for_semester() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET ALL
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        // CREATE MODULE
        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        module.setSemesterOffered(3);
        client.createModule(module);
        assertEquals(400, client.getLastStatusCode());
    }

    // CREATE MODULE FALSE INPUT 2
    @Test void create_university_and_module_for_false_credits() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET ALL
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        // CREATE MODULE
        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        module.setCreditPoints(-1);
        client.createModule(module);
        assertEquals(400, client.getLastStatusCode());
    }

    // GET SINGLE MODULE
    @Test void create_university_and_get_module() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET ALL
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        // CREATE MODULE
        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());

        // TEST SINGLE MODULE
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(5, client.moduleData().getFirst().getCreditPoints());
    }

     // UPDATE MODULE
     @Test void create_university_and_module_change_description() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET ALL
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        // CREATE MODULE
        assertTrue(client.isCreateModuleAllowed());
        var nonExistingModule = getModuleExample();
        client.createModule(nonExistingModule);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();

        // UPDATE MODULE
        var updated = client.moduleData().getFirst();
        var newDescription = "new description";
        updated.setDescription(newDescription);
        assertTrue(client.isUpdateModuleAllowed());
        client.updateModule(updated);
        assertEquals(204, client.getLastStatusCode());

        // TEST RESULT OF CHANGE
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals("new description", client.moduleData().getFirst().getDescription());
    }

    // UPDATE MODULE FALSE INPUT 1
    @Test void create_university_and__module_change_false_credits() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET ALL
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        // CREATE MODULE
        assertTrue(client.isCreateModuleAllowed());
        var nonExistingModule = getModuleExample();
        client.createModule(nonExistingModule);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();

        // UPDATE MODULE
        var updated = client.moduleData().getFirst();
        var newCredits = -1;
        updated.setCreditPoints(newCredits);
        assertTrue(client.isUpdateModuleAllowed());
        client.updateModule(updated);
        assertEquals(400, client.getLastStatusCode());
    }


    // DELETE MODULE
    @Test void create_two_modules_and_delete_single() throws IOException {

        // BEFORE EACH
        startAndCreateUniversity();

        // GET ALL
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(0, client.moduleData().size());


        // CREATE MODULE
        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertTrue(client.isGetAllModulesAllowed());

        // CREATE SECOND MODULE
        client.getAllModules();
        assertTrue(client.isCreateModuleAllowed());
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();

        // DELETE MODULE
        assertTrue(client.isDeleteModuleAllowed());
        client.deleteModule();
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(1, client.universityData().size());


    }
}