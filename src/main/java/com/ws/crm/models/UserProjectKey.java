package com.ws.crm.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserProjectKey implements Serializable {
    private Integer user_id;
    private Integer project_id;
}
