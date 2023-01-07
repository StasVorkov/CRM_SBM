package com.ws.crm.repositories;

import com.ws.crm.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectsRepository extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT p FROM Project p LEFT JOIN FETCH p.orders WHERE p.id =?1")
    Optional<Project> findProjectByIdCustom(int id);

    @Query(value = "SELECT p.project_id, p.name, p.address FROM PROJECTS AS p " +
            "JOIN users_project AS up " +
            "ON p.project_id = up.project_id AND up.user_id=?1 ORDER BY project_id",
            nativeQuery = true)
    List<Project> findProjectsByUser(int id);
}
