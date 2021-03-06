/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.BurdenEstimateCountryExpectation;


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
public class BurdenEstimateCountryExpectationRecord extends UpdatableRecordImpl<BurdenEstimateCountryExpectationRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = 1494732978;

    /**
     * Setter for <code>public.burden_estimate_country_expectation.burden_estimate_expectation</code>.
     */
    public void setBurdenEstimateExpectation(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.burden_estimate_country_expectation.burden_estimate_expectation</code>.
     */
    public Integer getBurdenEstimateExpectation() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.burden_estimate_country_expectation.country</code>.
     */
    public void setCountry(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.burden_estimate_country_expectation.country</code>.
     */
    public String getCountry() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return BurdenEstimateCountryExpectation.BURDEN_ESTIMATE_COUNTRY_EXPECTATION.BURDEN_ESTIMATE_EXPECTATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return BurdenEstimateCountryExpectation.BURDEN_ESTIMATE_COUNTRY_EXPECTATION.COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getBurdenEstimateExpectation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getBurdenEstimateExpectation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateCountryExpectationRecord value1(Integer value) {
        setBurdenEstimateExpectation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateCountryExpectationRecord value2(String value) {
        setCountry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateCountryExpectationRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BurdenEstimateCountryExpectationRecord
     */
    public BurdenEstimateCountryExpectationRecord() {
        super(BurdenEstimateCountryExpectation.BURDEN_ESTIMATE_COUNTRY_EXPECTATION);
    }

    /**
     * Create a detached, initialised BurdenEstimateCountryExpectationRecord
     */
    public BurdenEstimateCountryExpectationRecord(Integer burdenEstimateExpectation, String country) {
        super(BurdenEstimateCountryExpectation.BURDEN_ESTIMATE_COUNTRY_EXPECTATION);

        set(0, burdenEstimateExpectation);
        set(1, country);
    }
}
