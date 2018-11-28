/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelga.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.redel_citilink.redelga.TaskSchedule;


/**
 * ServiceImpl object for domain model class TaskSchedule.
 *
 * @see TaskSchedule
 */
@Service("redelga.TaskScheduleService")
@Validated
public class TaskScheduleServiceImpl implements TaskScheduleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskScheduleServiceImpl.class);


    @Autowired
    @Qualifier("redelga.TaskScheduleDao")
    private WMGenericDao<TaskSchedule, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<TaskSchedule, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "redelgaTransactionManager")
    @Override
    public TaskSchedule create(TaskSchedule taskSchedule) {
        LOGGER.debug("Creating a new TaskSchedule with information: {}", taskSchedule);

        TaskSchedule taskScheduleCreated = this.wmGenericDao.create(taskSchedule);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(taskScheduleCreated);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public TaskSchedule getById(Integer taskscheduleId) {
        LOGGER.debug("Finding TaskSchedule by id: {}", taskscheduleId);
        return this.wmGenericDao.findById(taskscheduleId);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public TaskSchedule findById(Integer taskscheduleId) {
        LOGGER.debug("Finding TaskSchedule by id: {}", taskscheduleId);
        try {
            return this.wmGenericDao.findById(taskscheduleId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TaskSchedule found with id: {}", taskscheduleId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public List<TaskSchedule> findByMultipleIds(List<Integer> taskscheduleIds, boolean orderedReturn) {
        LOGGER.debug("Finding TaskSchedules by ids: {}", taskscheduleIds);

        return this.wmGenericDao.findByMultipleIds(taskscheduleIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "redelgaTransactionManager")
    @Override
    public TaskSchedule update(TaskSchedule taskSchedule) {
        LOGGER.debug("Updating TaskSchedule with information: {}", taskSchedule);

        this.wmGenericDao.update(taskSchedule);
        this.wmGenericDao.refresh(taskSchedule);

        return taskSchedule;
    }

    @Transactional(value = "redelgaTransactionManager")
    @Override
    public TaskSchedule delete(Integer taskscheduleId) {
        LOGGER.debug("Deleting TaskSchedule with id: {}", taskscheduleId);
        TaskSchedule deleted = this.wmGenericDao.findById(taskscheduleId);
        if (deleted == null) {
            LOGGER.debug("No TaskSchedule found with id: {}", taskscheduleId);
            throw new EntityNotFoundException(String.valueOf(taskscheduleId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "redelgaTransactionManager")
    @Override
    public void delete(TaskSchedule taskSchedule) {
        LOGGER.debug("Deleting TaskSchedule with {}", taskSchedule);
        this.wmGenericDao.delete(taskSchedule);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public Page<TaskSchedule> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TaskSchedules");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public Page<TaskSchedule> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TaskSchedules");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service redelga for table TaskSchedule to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service redelga for table TaskSchedule to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}