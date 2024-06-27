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

    @BeforeEach
    public void setUp() throws IOException {
        this.client = new Rest();
        this.client.resetDatabase();
    }

    @Test void create_uni_test() throws IOException {
        client.start();
        client.getAllUniversities();

        // CREATE
        var university = getUniversityExample();
        assertTrue(client.isCreateUniversityAllowed());
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
    }

    @Test void create_uni_falseinput_incoming_test() throws IOException {
        client.start();
        client.getAllUniversities();

        var university = getUniversityExample();
        university.setStudentsIncoming(-1);
        assertTrue(client.isCreateUniversityAllowed());
        client.createUniversity(university);
        assertEquals(400, client.getLastStatusCode());
    }

    @Test void create_uni_falseinput_outgoing_test() throws IOException {
        client.start();
        client.getAllUniversities();

        var university = getUniversityExample();
        university.setStudentsOutgoing(-1);
        assertTrue(client.isCreateUniversityAllowed());
        client.createUniversity(university);
        assertEquals(400, client.getLastStatusCode());
    }

    @Test public void dispatcher_availability() throws IOException {
        client.start();
        assertEquals(200, client.getLastStatusCode());
    }

    @Test public void get_all_universities_dispatcher() throws IOException {
        client.start();
        assertTrue(client.isGetAllUniversitiesAllowed());
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
    }

    @Test void create_and_get_university_dispatcher() throws IOException {
        client.start();
        client.getAllUniversities();

        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        assertTrue( client.isGetSingleUniversityAllowed() );
        client.getSingleUniversity();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(96, client.universityData().getFirst().getUniversityRank());
    }

       @Test void create_university_and__module_change_false_credits() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isCreateModuleAllowed());
        var nonExistingModule = getModuleExample();
        client.createModule(nonExistingModule);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();

        var updated = client.moduleData().getFirst();
        var newCredits = -1;
        updated.setCreditPoints(newCredits);
        assertTrue(client.isUpdateModuleAllowed());
        client.updateModule(updated);
        assertEquals(400, client.getLastStatusCode());
    }

     @Test void create_universities_filter_name_and_country() throws IOException {
        client.fillDatabase();

        var university = getUniversityExample();

        client.start();
        client.getAllUniversities();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        client.start();
        client.getAllUniversities();
        assertTrue(client.isGetByNameAndCountryAllowed());
        client.getByNameAndCountry("UniTest","Germany");
        assertEquals( 1, client.universityData().size() );
    }

    @Test void create_5_universities_and_get_all() throws IOException {

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

    @Test void create_uni_falseinput_outgoing_test() throws IOException {
        client.start();
        client.getAllUniversities();

        var university = getUniversityExample();
        university.setStudentsOutgoing(-1);
        assertTrue(client.isCreateUniversityAllowed());
        client.createUniversity(university);
        assertEquals(400, client.getLastStatusCode());
    }

    @Test public void dispatcher_availability() throws IOException {
        client.start();
        assertEquals(200, client.getLastStatusCode());
    }

    @Test void create_universities_filter_name_and_country() throws IOException {
        client.fillDatabase();

        var university = getUniversityExample();

        client.start();
        client.getAllUniversities();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        client.start();
        client.getAllUniversities();
        assertTrue(client.isGetByNameAndCountryAllowed());
        client.getByNameAndCountry("UniTest","Germany");
        assertEquals( 1, client.universityData().size() );
    }

    @Test void create_universities_and_display_in_descending() throws IOException {
        client.fillDatabase();

        client.start();
        client.getAllUniversities();
        assertTrue(client.isGetByNameAndCountryReversedAllowed());
        client.getByNameAndCountryReversed("","");
        assertEquals( "Z University", client.universityData().getFirst().getUniversityName() );

        client.start();
        client.getAllUniversities();
        assertTrue(client.isGetAllUniversitiesAllowedReversed());
        client.getAllUniversitiesReversed();
        assertEquals( "Z University", client.universityData().getFirst().getUniversityName() );
    }

    @Test void create_two_universities_and_get_second() throws IOException {
        var university = getUniversityExample();
        var university2 = getUniversityExample();
        university2.setCountry("CountryTwo");

        client.start();
        client.getAllUniversities();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
        client.start();
        client.getAllUniversities();
        client.createUniversity(university2);
        assertEquals(201, client.getLastStatusCode());


        client.start();
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(2, client.universityData().size());

        client.setUniversityCursor(1);
        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity(client.cursorUniversityData);
        assertEquals("CountryTwo", client.universityData().getFirst().getCountry());
    }

    @Test void create_university_and_update() throws IOException {

        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        var uniNew = client.universityData().getFirst();
        var newCountry = "ChangedCountry";
        uniNew.setCountry(newCountry);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(204, client.getLastStatusCode());

        client.start();
        client.getAllUniversities();
        assertEquals(newCountry, client.universityData().getFirst().getCountry());

    }

    @Test void create_and_update_false_outgoing() throws IOException {

        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        var uniNew = client.universityData().getFirst();
        var newOutgoing = -1;
        uniNew.setStudentsOutgoing(newOutgoing);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(400, client.getLastStatusCode());

    }

    @Test void create_and_update_false_incoming() throws IOException {

        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        var uniNew = client.universityData().getFirst();
        var newIncoming = -1;
        uniNew.setStudentsIncoming(newIncoming);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(400, client.getLastStatusCode());
    }

    @Test void create_and_update_false_rank() throws IOException {

        client.start();
        client.getAllUniversities();
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();
        assertEquals(1, client.universityData().size());

        var uniNew = client.universityData().getFirst();
        var newRank = -1;
        uniNew.setUniversityRank(newRank);

        assertTrue(client.isUpdateUniversityAllowed());
        client.updateUniversity(uniNew);
        assertEquals(400, client.getLastStatusCode());
    }

    @Test void create_and_delete_one() throws IOException {

        client.start();
        client.getAllUniversities();

        assertTrue(client.isCreateUniversityAllowed());
        var university = getUniversityExample();
        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleUniversityAllowed());
        client.getSingleUniversity();

        assertTrue(client.isDeleteUniversityAllowed());
        client.deleteUniversity();
        assertEquals(204, client.getLastStatusCode());
        assertTrue(client.isGetAllUniversitiesAllowed());
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(0, client.universityData().size());

    }

    @Test void create_and_check_paging() throws IOException {
        client.fillDatabase();
        client.fillDatabase();
        client.start();

        assertTrue(client.isGetAllUniversitiesAllowed());
        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isNextAvailable());
        client.getNextPageOfUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertTrue(client.isPrevAvailable());
        assertTrue(client.isNextAvailable());
        assertEquals(20, client.getNumberOfResults());
        assertEquals(70, client.getTotalNumberOfResults());

        client.getNextPageOfUniversities();
        client.getNextPageOfUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(10, client.getNumberOfResults());
        assertFalse(client.isNextAvailable());
    }


    private void startAndCreateUniversity() throws IOException {
        client.start();
        client.getAllUniversities();

        var university = getUniversityExample();

        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
        client.getSingleUniversity();
    }

    @Test void create_and_get_all_modules() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
    }

    @Test void create_university_and_module() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(1, client.universityData().size());
    }

    @Test void create_university_and_false_module_for_semester() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        module.setSemesterOffered(3);
        client.createModule(module);
        assertEquals(400, client.getLastStatusCode());
    }

    @Test void create_university_and_module_for_false_credits() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        module.setCreditPoints(-1);
        client.createModule(module);
        assertEquals(400, client.getLastStatusCode());
    }

    @Test void create_university_and_get_module() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(5, client.moduleData().getFirst().getCreditPoints());
    }

     @Test void create_university_and_module_change_description() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isCreateModuleAllowed());
        var nonExistingModule = getModuleExample();
        client.createModule(nonExistingModule);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();

        var updated = client.moduleData().getFirst();
        var newDescription = "new description";
        updated.setDescription(newDescription);
        assertTrue(client.isUpdateModuleAllowed());
        client.updateModule(updated);
        assertEquals(204, client.getLastStatusCode());

        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals("new description", client.moduleData().getFirst().getDescription());
    }

    @Test void create_university_and__module_change_false_credits() throws IOException {

        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());

        assertTrue(client.isCreateModuleAllowed());
        var nonExistingModule = getModuleExample();
        client.createModule(nonExistingModule);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();

        var updated = client.moduleData().getFirst();
        var newCredits = -1;
        updated.setCreditPoints(newCredits);
        assertTrue(client.isUpdateModuleAllowed());
        client.updateModule(updated);
        assertEquals(400, client.getLastStatusCode());
    }


    @Test void create_two_modules_and_delete_single() throws IOException {
        startAndCreateUniversity();

        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(0, client.moduleData().size());

        assertTrue(client.isCreateModuleAllowed());
        var module = getModuleExample();
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();
        assertTrue(client.isGetAllModulesAllowed());

        client.getAllModules();
        assertTrue(client.isCreateModuleAllowed());
        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleModuleAllowed());
        client.getSingleModule();

        assertTrue(client.isDeleteModuleAllowed());
        client.deleteModule();
        assertTrue(client.isGetAllModulesAllowed());
        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(1, client.universityData().size());


    }

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
        university.setUniversityName("TestUniversity");
        university.setCountry("Germany");
        university.setDepartmentName("Science");
        university.setDepartmentWebsite("http://testuniversity.germany/cs");
        university.setContactPerson("Sam");
        university.setStudentsOutgoing(100);
        university.setStudentsIncoming(150);
        university.setNextSpringSemesterStart(LocalDate.of(2025, 3, 1));
        university.setNextAutumnSemesterStart(LocalDate.of(2025, 9, 1));
        university.setAnnualTuitionFees(1500.0);
        university.setUniversityRank(96);
        return university;
    }
}
