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
import org.vaccineimpact.api.db.tables.ModelRunParameter;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ModelRunParameterRecord extends UpdatableRecordImpl<ModelRunParameterRecord> implements Record4<Integer, Integer, String, String> {

    private static final long serialVersionUID = -1619070036;

    /**
     * Setter for <code>public.model_run_parameter.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.model_run_parameter.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.model_run_parameter.model_run</code>.
     */
    public void setModelRun(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.model_run_parameter.model_run</code>.
     */
    public Integer getModelRun() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.model_run_parameter.key</code>.
     */
    public void setKey(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.model_run_parameter.key</code>.
     */
    public String getKey() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.model_run_parameter.value</code>.
     */
    public void setValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.model_run_parameter.value</code>.
     */
    public String getValue() {
        return (String) get(3);
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
    public Row4<Integer, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ModelRunParameter.MODEL_RUN_PARAMETER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ModelRunParameter.MODEL_RUN_PARAMETER.MODEL_RUN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ModelRunParameter.MODEL_RUN_PARAMETER.KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ModelRunParameter.MODEL_RUN_PARAMETER.VALUE;
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
        return getModelRun();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterRecord value2(Integer value) {
        setModelRun(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterRecord value3(String value) {
        setKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterRecord value4(String value) {
        setValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelRunParameterRecord values(Integer value1, Integer value2, String value3, String value4) {
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
     * Create a detached ModelRunParameterRecord
     */
    public ModelRunParameterRecord() {
        super(ModelRunParameter.MODEL_RUN_PARAMETER);
    }

    /**
     * Create a detached, initialised ModelRunParameterRecord
     */
    public ModelRunParameterRecord(Integer id, Integer modelRun, String key, String value) {
        super(ModelRunParameter.MODEL_RUN_PARAMETER);

        set(0, id);
        set(1, modelRun);
        set(2, key);
        set(3, value);
    }
}