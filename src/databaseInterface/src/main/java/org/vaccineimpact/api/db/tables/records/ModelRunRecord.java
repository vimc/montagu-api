/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.ModelRun;


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
public class ModelRunRecord extends UpdatableRecordImpl<ModelRunRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = 1040452951;

    /**
     * Setter for <code>public.model_run.internal_id</code>.
     */
    public void setInternalId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.model_run.internal_id</code>.
     */
    public Integer getInternalId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.model_run.run_id</code>.
     */
    public void setRunId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.model_run.run_id</code>.
     */
    public String getRunId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.model_run.model_run_parameter_set</code>.
     */
    public void setModelRunParameterSet(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.model_run.model_run_parameter_set</code>.
     */
    public Integer getModelRunParameterSet() {
        return (Integer) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ModelRun.MODEL_RUN.INTERNAL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ModelRun.MODEL_RUN.RUN_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ModelRun.MODEL_RUN.MODEL_RUN_PARAMETER_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getInternalId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getRunId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getModelRunParameterSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getInternalId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getRunId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getModelRunParameterSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunRecord value1(Integer value) {
        setInternalId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunRecord value2(String value) {
        setRunId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunRecord value3(Integer value) {
        setModelRunParameterSet(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ModelRunRecord
     */
    public ModelRunRecord() {
        super(ModelRun.MODEL_RUN);
    }

    /**
     * Create a detached, initialised ModelRunRecord
     */
    public ModelRunRecord(Integer internalId, String runId, Integer modelRunParameterSet) {
        super(ModelRun.MODEL_RUN);

        set(0, internalId);
        set(1, runId);
        set(2, modelRunParameterSet);
    }
}
