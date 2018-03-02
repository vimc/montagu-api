/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.TableRecordImpl;
import org.vaccineimpact.api.db.tables.SelectBurdenDataCol;


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
public class SelectBurdenDataColRecord extends TableRecordImpl<SelectBurdenDataColRecord> implements Record3<String, Integer, BigDecimal> {

    private static final long serialVersionUID = -230670178;

    /**
     * Setter for <code>public.select_burden_data_col.country</code>.
     */
    public void setCountry(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.select_burden_data_col.country</code>.
     */
    public String getCountry() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.select_burden_data_col.year</code>.
     */
    public void setYear(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.select_burden_data_col.year</code>.
     */
    public Integer getYear() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.select_burden_data_col.value</code>.
     */
    public void setValue(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.select_burden_data_col.value</code>.
     */
    public BigDecimal getValue() {
        return (BigDecimal) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, Integer, BigDecimal> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, Integer, BigDecimal> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.YEAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getYear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component3() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getYear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenDataColRecord value1(String value) {
        setCountry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenDataColRecord value2(Integer value) {
        setYear(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenDataColRecord value3(BigDecimal value) {
        setValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenDataColRecord values(String value1, Integer value2, BigDecimal value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SelectBurdenDataColRecord
     */
    public SelectBurdenDataColRecord() {
        super(SelectBurdenDataCol.SELECT_BURDEN_DATA_COL);
    }

    /**
     * Create a detached, initialised SelectBurdenDataColRecord
     */
    public SelectBurdenDataColRecord(String country, Integer year, BigDecimal value) {
        super(SelectBurdenDataCol.SELECT_BURDEN_DATA_COL);

        set(0, country);
        set(1, year);
        set(2, value);
    }
}
