/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelivery_cl.service;

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

import com.redel_citilink.redelivery_cl.MtcTask;
import com.redel_citilink.redelivery_cl.MtcTaskPlanned;


/**
 * ServiceImpl object for domain model class MtcTask.
 *
 * @see MtcTask
 */
@Service("redelivery_cl.MtcTaskService")
@Validated
public class MtcTaskServiceImpl implements MtcTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MtcTaskServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("redelivery_cl.MtcTaskPlannedService")
    private MtcTaskPlannedService mtcTaskPlannedService;

    @Autowired
    @Qualifier("redelivery_cl.MtcTaskDao")
    private WMGenericDao<MtcTask, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<MtcTask, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "redelivery_clTransactionManager")
    @Override
    public MtcTask create(MtcTask mtcTask) {
        LOGGER.debug("Creating a new MtcTask with information: {}", mtcTask);

        MtcTask mtcTaskCreated = this.wmGenericDao.create(mtcTask);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(mtcTaskCreated);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public MtcTask getById(Integer mtctaskId) {
        LOGGER.debug("Finding MtcTask by id: {}", mtctaskId);
        return this.wmGenericDao.findById(mtctaskId);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public MtcTask findById(Integer mtctaskId) {
        LOGGER.debug("Finding MtcTask by id: {}", mtctaskId);
        try {
            return this.wmGenericDao.findById(mtctaskId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No MtcTask found with id: {}", mtctaskId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public List<MtcTask> findByMultipleIds(List<Integer> mtctaskIds, boolean orderedReturn) {
        LOGGER.debug("Finding MtcTasks by ids: {}", mtctaskIds);

        return this.wmGenericDao.findByMultipleIds(mtctaskIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "redelivery_clTransactionManager")
    @Override
    public MtcTask update(MtcTask mtcTask) {
        LOGGER.debug("Updating MtcTask with information: {}", mtcTask);

        List<MtcTaskPlanned> mtcTaskPlanneds = mtcTask.getMtcTaskPlanneds();
        if(mtcTaskPlanneds != null && Hibernate.isInitialized(mtcTaskPlanneds)) {
            mtcTaskPlanneds.forEach(_mtcTaskPlanned -> _mtcTaskPlanned.setMtcTask(mtcTask));
        }

        this.wmGenericDao.update(mtcTask);
        this.wmGenericDao.refresh(mtcTask);

        // Deleting children which are not present in the list.
        if(mtcTaskPlanneds != null && Hibernate.isInitialized(mtcTaskPlanneds) && !mtcTaskPlanneds.isEmpty()) {
            List<MtcTaskPlanned> _remainingChildren = wmGenericDao.execute(
                session -> DaoUtils.findAllRemainingChildren(session, MtcTaskPlanned.class,
                        new DaoUtils.ChildrenFilter<>("mtcTask", mtcTask, mtcTaskPlanneds)));
            LOGGER.debug("Found {} detached children, deleting", _remainingChildren.size());
            _remainingChildren.forEach(_mtcTaskPlanned -> mtcTaskPlannedService.delete(_mtcTaskPlanned));
            mtcTask.setMtcTaskPlanneds(mtcTaskPlanneds);
        }

        return mtcTask;
    }

    @Transactional(value = "redelivery_clTransactionManager")
    @Override
    public MtcTask delete(Integer mtctaskId) {
        LOGGER.debug("Deleting MtcTask with id: {}", mtctaskId);
        MtcTask deleted = this.wmGenericDao.findById(mtctaskId);
        if (deleted == null) {
            LOGGER.debug("No MtcTask found with id: {}", mtctaskId);
            throw new EntityNotFoundException(String.valueOf(mtctaskId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "redelivery_clTransactionManager")
    @Override
    public void delete(MtcTask mtcTask) {
        LOGGER.debug("Deleting MtcTask with {}", mtcTask);
        this.wmGenericDao.delete(mtcTask);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public Page<MtcTask> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all MtcTasks");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public Page<MtcTask> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all MtcTasks");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service redelivery_cl for table MtcTask to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service redelivery_cl for table MtcTask to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public Page<MtcTaskPlanned> findAssociatedMtcTaskPlanneds(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated mtcTaskPlanneds");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("mtcTask.id = '" + id + "'");

        return mtcTaskPlannedService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service MtcTaskPlannedService instance
     */
    protected void setMtcTaskPlannedService(MtcTaskPlannedService service) {
        this.mtcTaskPlannedService = service;
    }

}