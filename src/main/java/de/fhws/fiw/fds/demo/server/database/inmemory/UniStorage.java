package de.fhws.fiw.fds.demo.server.database.inmemory;

import java.time.LocalDate;
import java.util.function.Predicate;

import de.fhws.fiw.fds.demo.server.UniversityDao;
import de.fhws.fiw.fds.demo.server.api.models.Uni;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class UniStorage extends AbstractInMemoryStorage<Uni> implements UniversityDao {

    @Override
    public CollectionModelResult<Uni> readByUniversityNameCountry(String universityName, String country, SearchParameter searchParameter) {
        return InMemoryPaging.page(this.readAllByPredicate(
                byUniversityNameCountry(universityName, country),
                searchParameter
        ), searchParameter.getOffset(), searchParameter.getSize());
    }

    private Predicate<Uni> byUniversityNameCountry(String universityName, String country) {
        return p -> (universityName.isEmpty() || p.getUniversityName().equals(universityName)) && (country.isEmpty() || p.getCountry().equals(country));
    }

    public void resetDatabase() {
        this.storage.clear();
    }

    @Override
    public void initializeDatabase() {
        for (int i = 1; i <= 26; i++) {
            Uni university = getUniversity("%c University", (char) ('A' + i - 1), i, "Canada");
            this.create(university);
        }

        for (int i = 1; i <= 9; i++) {
            Uni university = getUniversity("%d University", i, i, "Germany");
            this.create(university);
        }
    }

    private static Uni getUniversity(String format, int i, int i1, String Germany) {
        String universityName = String.format(format, i);
        Uni university = new Uni();
        university.setUniversityName(universityName);
        university.setCountry(Germany);
        university.setDepartmentName("Computer Science");
        university.setDepartmentWebsite("http://www.def.edu/cs");
        university.setContactPerson("Dr. Jane Smith");
        university.setStudentsOutgoing(120);
        university.setStudentsIncoming(60);

        // Convert the string to LocalDate
        LocalDate springSemesterStart = LocalDate.parse("2024-01-15");
        LocalDate autumnSemesterStart = LocalDate.parse("2024-09-20");

        university.setNextSpringSemesterStart(springSemesterStart);
        university.setNextAutumnSemesterStart(autumnSemesterStart);

        university.setAnnualTuitionFees(40000.0);
        university.setUniversityRank(i1);
        return university;
    }
}
