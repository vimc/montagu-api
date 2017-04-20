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
import org.vaccineimpact.api.db.tables.ImpactEstimateSet;


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
public class ImpactEstimateSetRecord extends UpdatableRecordImpl<ImpactEstimateSetRecord> implements Record4<Integer, Integer, String, String> {

    private static final long serialVersionUID = 1366672795;

    /**
     * Setter for <code>public.impact_estimate_set.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.impact_estimate_set.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.impact_estimate_set.version</code>.
     */
    public void setVersion(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.impact_estimate_set.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.impact_estimate_set.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.impact_estimate_set.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.impact_estimate_set.script</code>.
     */
    public void setScript(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.impact_estimate_set.script</code>.
     */
    public String getScript() {
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
        return ImpactEstimateSet.IMPACT_ESTIMATE_SET.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ImpactEstimateSet.IMPACT_ESTIMATE_SET.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ImpactEstimateSet.IMPACT_ESTIMATE_SET.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ImpactEstimateSet.IMPACT_ESTIMATE_SET.SCRIPT;
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
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getScript();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactEstimateSetRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactEstimateSetRecord value2(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactEstimateSetRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactEstimateSetRecord value4(String value) {
        setScript(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactEstimateSetRecord values(Integer value1, Integer value2, String value3, String value4) {
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
     * Create a detached ImpactEstimateSetRecord
     */
    public ImpactEstimateSetRecord() {
        super(ImpactEstimateSet.IMPACT_ESTIMATE_SET);
    }

    /**
     * Create a detached, initialised ImpactEstimateSetRecord
     */
    public ImpactEstimateSetRecord(Integer id, Integer version, String name, String script) {
        super(ImpactEstimateSet.IMPACT_ESTIMATE_SET);

        set(0, id);
        set(1, version);
        set(2, name);
        set(3, script);
    }
}
