package com.ws.crm.repositories.project;

import com.ws.crm.models.Project;
import com.ws.crm.repositories.ProjectsRepository;
import com.ws.crm.util.projects.UtilProject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProjectRepositoryTest {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Test
    public void findProjectByIdCustom() {

        projectsRepository.save(UtilProject.get());

        Optional<Project> projectFromRepository = projectsRepository.findProjectByIdCustom(UtilProject.get().getId());

        projectFromRepository.ifPresent(project -> assertEquals(UtilProject.get(), project));
    }
}