package de.fhws.fiw.fds.demo.server.database.utils;

import de.fhws.fiw.fds.demo.server.DaoFactory;

public class DatabaseInitialization {

    public static void initializeDBWithRelations() {
        DaoFactory.getInstance().getUniversityDao().initializeDatabase();
    }
}
