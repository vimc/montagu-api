/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.Touchstone;


/**
 * This is the top-level categorization. It refers to an Operational Forecast 
 * from GAVI, a WUENIC July update, or some other data set against which impact 
 * estimates are going to be done 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TouchstoneRecord extends UpdatableRecordImpl<TouchstoneRecord> implements Record6<String, String, Integer, String, Integer, Integer> {

    private static final long serialVersionUID = -678504995;

    /**
     * Setter for <code>public.touchstone.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.touchstone.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.touchstone.touchstone_name</code>.
     */
    public void setTouchstoneName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.touchstone.touchstone_name</code>.
     */
    public String getTouchstoneName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.touchstone.version</code>.
     */
    public void setVersion(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.touchstone.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.touchstone.status</code>.
     */
    public void setStatus(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.touchstone.status</code>.
     */
    public String getStatus() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.touchstone.year_start</code>.
     */
    public void setYearStart(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.touchstone.year_start</code>.
     */
    public Integer getYearStart() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.touchstone.year_end</code>.
     */
    public void setYearEnd(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.touchstone.year_end</code>.
     */
    public Integer getYearEnd() {
        return (Integer) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Integer, String, Integer, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Integer, String, Integer, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Touchstone.TOUCHSTONE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Touchstone.TOUCHSTONE.TOUCHSTONE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return Touchstone.TOUCHSTONE.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Touchstone.TOUCHSTONE.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Touchstone.TOUCHSTONE.YEAR_START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Touchstone.TOUCHSTONE.YEAR_END;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTouchstoneName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getYearStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getYearEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneRecord value2(String value) {
        setTouchstoneName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneRecord value3(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneRecord value4(String value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneRecord value5(Integer value) {
        setYearStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneRecord value6(Integer value) {
        setYearEnd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneRecord values(String value1, String value2, Integer value3, String value4, Integer value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TouchstoneRecord
     */
    public TouchstoneRecord() {
        super(Touchstone.TOUCHSTONE);
    }

    /**
     * Create a detached, initialised TouchstoneRecord
     */
    public TouchstoneRecord(String id, String touchstoneName, Integer version, String status, Integer yearStart, Integer yearEnd) {
        super(Touchstone.TOUCHSTONE);

        set(0, id);
        set(1, touchstoneName);
        set(2, version);
        set(3, status);
        set(4, yearStart);
        set(5, yearEnd);
    }
}
