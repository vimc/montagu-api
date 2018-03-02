/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.ModelRunParameterSet;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ModelRunParameterSetRecord extends UpdatableRecordImpl<ModelRunParameterSetRecord> implements Record4<Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 2003858017;

    /**
     * Setter for <code>public.model_run_parameter_set.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.model_run_parameter_set.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.model_run_parameter_set.responsibility_set</code>.
     */
    public void setResponsibilitySet(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.model_run_parameter_set.responsibility_set</code>.
     */
    public Integer getResponsibilitySet() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.model_run_parameter_set.upload_info</code>.
     */
    public void setUploadInfo(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.model_run_parameter_set.upload_info</code>.
     */
    public Integer getUploadInfo() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.model_run_parameter_set.model_version</code>.
     */
    public void setModelVersion(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.model_run_parameter_set.model_version</code>.
     */
    public Integer getModelVersion() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ModelRunParameterSet.MODEL_RUN_PARAMETER_SET.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ModelRunParameterSet.MODEL_RUN_PARAMETER_SET.RESPONSIBILITY_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ModelRunParameterSet.MODEL_RUN_PARAMETER_SET.UPLOAD_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return ModelRunParameterSet.MODEL_RUN_PARAMETER_SET.MODEL_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getResponsibilitySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getUploadInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getModelVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getResponsibilitySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUploadInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getModelVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterSetRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterSetRecord value2(Integer value) {
        setResponsibilitySet(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterSetRecord value3(Integer value) {
        setUploadInfo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterSetRecord value4(Integer value) {
        setModelVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterSetRecord values(Integer value1, Integer value2, Integer value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ModelRunParameterSetRecord
     */
    public ModelRunParameterSetRecord() {
        super(ModelRunParameterSet.MODEL_RUN_PARAMETER_SET);
    }

    /**
     * Create a detached, initialised ModelRunParameterSetRecord
     */
    public ModelRunParameterSetRecord(Integer id, Integer responsibilitySet, Integer uploadInfo, Integer modelVersion) {
        super(ModelRunParameterSet.MODEL_RUN_PARAMETER_SET);

        set(0, id);
        set(1, responsibilitySet);
        set(2, uploadInfo);
        set(3, modelVersion);
    }
}
