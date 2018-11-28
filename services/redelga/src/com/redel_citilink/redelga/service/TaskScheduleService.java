/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelga.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.redel_citilink.redelga.TaskSchedule;

/**
 * Service object for domain model class {@link TaskSchedule}.
 */
public interface TaskScheduleService {

    /**
     * Creates a new TaskSchedule. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TaskSchedule if any.
     *
     * @param taskSchedule Details of the TaskSchedule to be created; value cannot be null.
     * @return The newly created TaskSchedule.
     */
    TaskSchedule create(@Valid TaskSchedule taskSchedule);


	/**
     * Returns TaskSchedule by given id if exists.
     *
     * @param taskscheduleId The id of the TaskSchedule to get; value cannot be null.
     * @return TaskSchedule associated with the given taskscheduleId.
	 * @throws EntityNotFoundException If no TaskSchedule is found.
     */
    TaskSchedule getById(Integer taskscheduleId);

    /**
     * Find and return the TaskSchedule by given id if exists, returns null otherwise.
     *
     * @param taskscheduleId The id of the TaskSchedule to get; value cannot be null.
     * @return TaskSchedule associated with the given taskscheduleId.
     */
    TaskSchedule findById(Integer taskscheduleId);

	/**
     * Find and return the list of TaskSchedules by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param taskscheduleIds The id's of the TaskSchedule to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TaskSchedules associated with the given taskscheduleIds.
     */
    List<TaskSchedule> findByMultipleIds(List<Integer> taskscheduleIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TaskSchedule. It replaces all fields of the existing TaskSchedule with the given taskSchedule.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TaskSchedule if any.
     *
     * @param taskSchedule The details of the TaskSchedule to be updated; value cannot be null.
     * @return The updated TaskSchedule.
     * @throws EntityNotFoundException if no TaskSchedule is found with given input.
     */
    TaskSchedule update(@Valid TaskSchedule taskSchedule);

    /**
     * Deletes an existing TaskSchedule with the given id.
     *
     * @param taskscheduleId The id of the TaskSchedule to be deleted; value cannot be null.
     * @return The deleted TaskSchedule.
     * @throws EntityNotFoundException if no TaskSchedule found with the given id.
     */
    TaskSchedule delete(Integer taskscheduleId);

    /**
     * Deletes an existing TaskSchedule with the given object.
     *
     * @param taskSchedule The instance of the TaskSchedule to be deleted; value cannot be null.
     */
    void delete(TaskSchedule taskSchedule);

    /**
     * Find all TaskSchedules matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TaskSchedules.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TaskSchedule> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TaskSchedules matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TaskSchedules.
     *
     * @see Pageable
     * @see Page
     */
    Page<TaskSchedule> findAll(String query, Pageable pageable);

    /**
     * Exports all TaskSchedules matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all TaskSchedules matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the TaskSchedules in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TaskSchedule.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}