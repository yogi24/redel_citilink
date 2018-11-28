/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelga.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
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
import com.wavemaker.runtime.data.util.DaoUtils;
import com.wavemaker.runtime.file.model.Downloadable;

import com.redel_citilink.redelga.CampTask;
import com.redel_citilink.redelga.TaskSchedule;


/**
 * ServiceImpl object for domain model class CampTask.
 *
 * @see CampTask
 */
@Service("redelga.CampTaskService")
@Validated
public class CampTaskServiceImpl implements CampTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampTaskServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("redelga.TaskScheduleService")
    private TaskScheduleService taskScheduleService;

    @Autowired
    @Qualifier("redelga.CampTaskDao")
    private WMGenericDao<CampTask, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<CampTask, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "redelgaTransactionManager")
    @Override
    public CampTask create(CampTask campTask) {
        LOGGER.debug("Creating a new CampTask with information: {}", campTask);

        CampTask campTaskCreated = this.wmGenericDao.create(campTask);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(campTaskCreated);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public CampTask getById(Integer camptaskId) {
        LOGGER.debug("Finding CampTask by id: {}", camptaskId);
        return this.wmGenericDao.findById(camptaskId);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public CampTask findById(Integer camptaskId) {
        LOGGER.debug("Finding CampTask by id: {}", camptaskId);
        try {
            return this.wmGenericDao.findById(camptaskId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No CampTask found with id: {}", camptaskId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public List<CampTask> findByMultipleIds(List<Integer> camptaskIds, boolean orderedReturn) {
        LOGGER.debug("Finding CampTasks by ids: {}", camptaskIds);

        return this.wmGenericDao.findByMultipleIds(camptaskIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "redelgaTransactionManager")
    @Override
    public CampTask update(CampTask campTask) {
        LOGGER.debug("Updating CampTask with information: {}", campTask);

        List<TaskSchedule> taskSchedules = campTask.getTaskSchedules();
        if(taskSchedules != null && Hibernate.isInitialized(taskSchedules)) {
            taskSchedules.forEach(_taskSchedule -> _taskSchedule.setCampTask(campTask));
        }

        this.wmGenericDao.update(campTask);
        this.wmGenericDao.refresh(campTask);

        // Deleting children which are not present in the list.
        if(taskSchedules != null && Hibernate.isInitialized(taskSchedules) && !taskSchedules.isEmpty()) {
            List<TaskSchedule> _remainingChildren = wmGenericDao.execute(
                session -> DaoUtils.findAllRemainingChildren(session, TaskSchedule.class,
                        new DaoUtils.ChildrenFilter<>("campTask", campTask, taskSchedules)));
            LOGGER.debug("Found {} detached children, deleting", _remainingChildren.size());
            _remainingChildren.forEach(_taskSchedule -> taskScheduleService.delete(_taskSchedule));
            campTask.setTaskSchedules(taskSchedules);
        }

        return campTask;
    }

    @Transactional(value = "redelgaTransactionManager")
    @Override
    public CampTask delete(Integer camptaskId) {
        LOGGER.debug("Deleting CampTask with id: {}", camptaskId);
        CampTask deleted = this.wmGenericDao.findById(camptaskId);
        if (deleted == null) {
            LOGGER.debug("No CampTask found with id: {}", camptaskId);
            throw new EntityNotFoundException(String.valueOf(camptaskId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "redelgaTransactionManager")
    @Override
    public void delete(CampTask campTask) {
        LOGGER.debug("Deleting CampTask with {}", campTask);
        this.wmGenericDao.delete(campTask);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public Page<CampTask> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all CampTasks");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public Page<CampTask> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all CampTasks");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service redelga for table CampTask to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "redelgaTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service redelga for table CampTask to {} format", options.getExportType());
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

    @Transactional(readOnly = true, value = "redelgaTransactionManager")
    @Override
    public Page<TaskSchedule> findAssociatedTaskSchedules(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated taskSchedules");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("campTask.id = '" + id + "'");

        return taskScheduleService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TaskScheduleService instance
     */
    protected void setTaskScheduleService(TaskScheduleService service) {
        this.taskScheduleService = service;
    }

}