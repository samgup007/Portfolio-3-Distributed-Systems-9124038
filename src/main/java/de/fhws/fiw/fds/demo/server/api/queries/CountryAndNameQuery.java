package de.fhws.fiw.fds.demo.server.api.queries;

import java.util.List;

import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Uni;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;


public class CountryAndNameQuery<R> extends AbstractQuery<R, Uni>
{

    private final String universityName;
    private final String country;
    private final String order;

    public CountryAndNameQuery(String universityName, String country, String order, int offset, int size) {
        this.universityName = universityName;
        this.country = country;
        this.order = order;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
    }

    protected CollectionModelResult<Uni> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        searchParameter.setOrderByAttribute(this.order);

        CollectionModelResult<Uni> result = DaoFactory.getInstance().getUniversityDao().readAll();

        List<Uni> filteredResults = result.getResult().stream()
                .filter(u -> (this.universityName.isEmpty() || u.getUniversityName().equalsIgnoreCase(this.universityName)))
                .filter(u -> (this.country.isEmpty() || u.getCountry().equalsIgnoreCase(this.country)))
                .toList();

        List<Uni> sortedResults = filteredResults.stream()
                .sorted((u1, u2) -> {
                    if (this.order.equalsIgnoreCase("asc")) {
                        return u1.getUniversityName().compareTo(u2.getUniversityName());
                    } else if (this.order.equalsIgnoreCase("dsc")) {
                        return u2.getUniversityName().compareTo(u1.getUniversityName());
                    } else {
                        return 0;
                    }
                })
                .toList();

        List<Uni> paginatedResults = sortedResults.stream()
                .skip(searchParameter.getOffset())
                .limit(searchParameter.getSize())
                .toList();

        result.getResult().clear();
        result.getResult().addAll(paginatedResults);
        result.setTotalNumberOfResult(sortedResults.size());

        return result;
    }



}
