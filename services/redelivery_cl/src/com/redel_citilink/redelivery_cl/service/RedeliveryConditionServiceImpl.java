/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelivery_cl.service;

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

import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.redel_citilink.redelivery_cl.RedeliveryCondition;


/**
 * ServiceImpl object for domain model class RedeliveryCondition.
 *
 * @see RedeliveryCondition
 */
@Service("redelivery_cl.RedeliveryConditionService")
@Validated
public class RedeliveryConditionServiceImpl implements RedeliveryConditionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedeliveryConditionServiceImpl.class);


    @Autowired
    @Qualifier("redelivery_cl.RedeliveryConditionDao")
    private WMGenericDao<RedeliveryCondition, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<RedeliveryCondition, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "redelivery_clTransactionManager")
    @Override
    public RedeliveryCondition create(RedeliveryCondition redeliveryCondition) {
        LOGGER.debug("Creating a new RedeliveryCondition with information: {}", redeliveryCondition);

        RedeliveryCondition redeliveryConditionCreated = this.wmGenericDao.create(redeliveryCondition);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(redeliveryConditionCreated);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public RedeliveryCondition getById(Integer redeliveryconditionId) {
        LOGGER.debug("Finding RedeliveryCondition by id: {}", redeliveryconditionId);
        return this.wmGenericDao.findById(redeliveryconditionId);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public RedeliveryCondition findById(Integer redeliveryconditionId) {
        LOGGER.debug("Finding RedeliveryCondition by id: {}", redeliveryconditionId);
        try {
            return this.wmGenericDao.findById(redeliveryconditionId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No RedeliveryCondition found with id: {}", redeliveryconditionId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public List<RedeliveryCondition> findByMultipleIds(List<Integer> redeliveryconditionIds, boolean orderedReturn) {
        LOGGER.debug("Finding RedeliveryConditions by ids: {}", redeliveryconditionIds);

        return this.wmGenericDao.findByMultipleIds(redeliveryconditionIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "redelivery_clTransactionManager")
    @Override
    public RedeliveryCondition update(RedeliveryCondition redeliveryCondition) {
        LOGGER.debug("Updating RedeliveryCondition with information: {}", redeliveryCondition);

        this.wmGenericDao.update(redeliveryCondition);
        this.wmGenericDao.refresh(redeliveryCondition);

        return redeliveryCondition;
    }

    @Transactional(value = "redelivery_clTransactionManager")
    @Override
    public RedeliveryCondition delete(Integer redeliveryconditionId) {
        LOGGER.debug("Deleting RedeliveryCondition with id: {}", redeliveryconditionId);
        RedeliveryCondition deleted = this.wmGenericDao.findById(redeliveryconditionId);
        if (deleted == null) {
            LOGGER.debug("No RedeliveryCondition found with id: {}", redeliveryconditionId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), RedeliveryCondition.class.getSimpleName(), redeliveryconditionId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "redelivery_clTransactionManager")
    @Override
    public void delete(RedeliveryCondition redeliveryCondition) {
        LOGGER.debug("Deleting RedeliveryCondition with {}", redeliveryCondition);
        this.wmGenericDao.delete(redeliveryCondition);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public Page<RedeliveryCondition> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all RedeliveryConditions");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager")
    @Override
    public Page<RedeliveryCondition> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all RedeliveryConditions");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service redelivery_cl for table RedeliveryCondition to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "redelivery_clTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service redelivery_cl for table RedeliveryCondition to {} format", options.getExportType());
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



}