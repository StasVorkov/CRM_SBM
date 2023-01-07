package com.ws.crm.util.projects;
import com.ws.crm.models.Project;

import java.util.ArrayList;

public class UtilProject {
    private static final String NAME = "stubProject";
    private static final String ADDRESS = "stubAddress";
    private static volatile Project instance;
    private static final int id = 1;

    private UtilProject() {
    }

    public static Project get() {
        if (instance == null) {
            synchronized (UtilProject.class) {
                if (instance == null) {
                    instance = createStub();
                }
            }
        }return instance;
    }
    private static Project createStub() {
        Project stubProject = new Project();
        stubProject.setId(id);
        stubProject.setName(NAME);
        stubProject.setAddress(ADDRESS);
        stubProject.setUsers(new ArrayList<>());
        stubProject.setOrders(new ArrayList<>());
        return stubProject;
    }

}
