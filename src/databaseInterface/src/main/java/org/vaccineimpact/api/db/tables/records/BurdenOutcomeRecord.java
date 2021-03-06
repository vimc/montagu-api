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
import org.vaccineimpact.api.db.tables.BurdenOutcome;


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
public class BurdenOutcomeRecord extends UpdatableRecordImpl<BurdenOutcomeRecord> implements Record4<Short, String, String, Boolean> {

    private static final long serialVersionUID = -2098365512;

    /**
     * Setter for <code>public.burden_outcome.id</code>.
     */
    public void setId(Short value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.burden_outcome.id</code>.
     */
    public Short getId() {
        return (Short) get(0);
    }

    /**
     * Setter for <code>public.burden_outcome.code</code>.
     */
    public void setCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.burden_outcome.code</code>.
     */
    public String getCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.burden_outcome.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.burden_outcome.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.burden_outcome.proportion</code>.
     */
    public void setProportion(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.burden_outcome.proportion</code>.
     */
    public Boolean getProportion() {
        return (Boolean) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Short> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Short, String, String, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Short, String, String, Boolean> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field1() {
        return BurdenOutcome.BURDEN_OUTCOME.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return BurdenOutcome.BURDEN_OUTCOME.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return BurdenOutcome.BURDEN_OUTCOME.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return BurdenOutcome.BURDEN_OUTCOME.PROPORTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component4() {
        return getProportion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCode();
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
    public Boolean value4() {
        return getProportion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenOutcomeRecord value1(Short value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenOutcomeRecord value2(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenOutcomeRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenOutcomeRecord value4(Boolean value) {
        setProportion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenOutcomeRecord values(Short value1, String value2, String value3, Boolean value4) {
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
     * Create a detached BurdenOutcomeRecord
     */
    public BurdenOutcomeRecord() {
        super(BurdenOutcome.BURDEN_OUTCOME);
    }

    /**
     * Create a detached, initialised BurdenOutcomeRecord
     */
    public BurdenOutcomeRecord(Short id, String code, String name, Boolean proportion) {
        super(BurdenOutcome.BURDEN_OUTCOME);

        set(0, id);
        set(1, code);
        set(2, name);
        set(3, proportion);
    }
}
