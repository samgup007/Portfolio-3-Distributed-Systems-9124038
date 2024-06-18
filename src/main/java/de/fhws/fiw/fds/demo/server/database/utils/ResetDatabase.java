package de.fhws.fiw.fds.demo.server.database.utils;

import de.fhws.fiw.fds.demo.server.DaoFactory;

public class ResetDatabase {

    public static void resetAll() {
        DaoFactory.getInstance().getUniversityDao().resetDatabase();
    }

}
