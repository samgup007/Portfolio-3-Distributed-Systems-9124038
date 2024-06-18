package de.fhws.fiw.fds.demo.server;

import de.fhws.fiw.fds.demo.server.database.inmemory.ModuleStorage;
import de.fhws.fiw.fds.demo.server.database.inmemory.UniStorage;
import de.fhws.fiw.fds.demo.server.database.inmemory.UniStorageModule;

public class DaoFactory {

    private static DaoFactory INSTANCE;

    private final UniversityDao universityDao;
    private final ModuleDao moduleDao;
    private final ModuleOfUniversityDao moduleOfPartnerUniversityDao;

    private DaoFactory() {
        this.moduleDao = new ModuleStorage();
        this.universityDao = new UniStorage();
        this.moduleOfPartnerUniversityDao = new UniStorageModule(this.moduleDao);
    }

    public static DaoFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DaoFactory();
        }
        return INSTANCE;
    }

    public UniversityDao getUniversityDao() {
        return this.universityDao;
    }

    public ModuleDao getModuleDao() {
        return this.moduleDao;
    }

    public ModuleOfUniversityDao getModuleOfPartnerUniversityDao() {
        return this.moduleOfPartnerUniversityDao;
    }
}
