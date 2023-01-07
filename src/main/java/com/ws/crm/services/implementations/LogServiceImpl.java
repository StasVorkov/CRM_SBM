package com.ws.crm.services.implementations;


import com.ws.crm.models.Log;
import com.ws.crm.models.Order;
import com.ws.crm.models.enums.Status;
import com.ws.crm.repositories.LogsRepository;
import com.ws.crm.services.LogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final LogsRepository logsRepository;

    @Autowired
    public LogServiceImpl(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @Override
    @Transactional
    public Log createFirstLogByOrder(Order order) {
        Log log = createFirstLog(order);
        logsRepository.save(log);
        return log;
    }

    private Log createFirstLog(Order order) {
        Log log = new Log();
        log.setOrder(order);
        log.setNewStatus(Status.NEW);
        log.setChange(LocalDateTime.now());
        return log;
    }

    @Override
    @Transactional
    public Log createNextLogByOrder(Order order) {
        Log log = createNextLog(order);
        logsRepository.save(log);
        return log;
    }

    private Log createNextLog(Order order) {
        Log log = new Log();
        log.setOrder(order);
        log.setNewStatus(order.getStatus());
        log.setChange(LocalDateTime.now());
        return log;
    }

    @Override
    public List<Log> getLogsByOrderId(int id) {
        return logsRepository.findLogsByOrderId(id);
    }
}
