package com.ws.crm.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(UserProjectKey.class)
@Table(name = "users_project")
public class UserProject {

    @Id
    private Integer user_id;

    @Id
    private Integer project_id;

}
