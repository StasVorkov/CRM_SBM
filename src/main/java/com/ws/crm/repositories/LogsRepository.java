package com.ws.crm.repositories;

import com.ws.crm.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogsRepository extends JpaRepository<Log,Integer> {

    @Query(value = "SELECT l FROM Log l WHERE l.order.id =?1 ORDER BY l.id")
    List<Log> findLogsByOrderId(int id);

}
