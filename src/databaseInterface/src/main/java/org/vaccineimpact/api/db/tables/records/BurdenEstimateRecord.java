/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.BurdenEstimate;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BurdenEstimateRecord extends UpdatableRecordImpl<BurdenEstimateRecord> implements Record8<Integer, Integer, String, Integer, Integer, Boolean, BigDecimal, Integer> {

    private static final long serialVersionUID = -1951771521;

    /**
     * Setter for <code>public.burden_estimate.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.burden_estimate.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.burden_estimate.burden_estimate_set</code>.
     */
    public void setBurdenEstimateSet(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.burden_estimate.burden_estimate_set</code>.
     */
    public Integer getBurdenEstimateSet() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.burden_estimate.country</code>.
     */
    public void setCountry(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.burden_estimate.country</code>.
     */
    public String getCountry() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.burden_estimate.year</code>.
     */
    public void setYear(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.burden_estimate.year</code>.
     */
    public Integer getYear() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.burden_estimate.burden_outcome</code>.
     */
    public void setBurdenOutcome(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.burden_estimate.burden_outcome</code>.
     */
    public Integer getBurdenOutcome() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.burden_estimate.stochastic</code>.
     */
    public void setStochastic(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.burden_estimate.stochastic</code>.
     */
    public Boolean getStochastic() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>public.burden_estimate.value</code>.
     */
    public void setValue(BigDecimal value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.burden_estimate.value</code>.
     */
    public BigDecimal getValue() {
        return (BigDecimal) get(6);
    }

    /**
     * Setter for <code>public.burden_estimate.age</code>.
     */
    public void setAge(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.burden_estimate.age</code>.
     */
    public Integer getAge() {
        return (Integer) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, Integer, Integer, Boolean, BigDecimal, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, Integer, Integer, Boolean, BigDecimal, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return BurdenEstimate.BURDEN_ESTIMATE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return BurdenEstimate.BURDEN_ESTIMATE.BURDEN_ESTIMATE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return BurdenEstimate.BURDEN_ESTIMATE.COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return BurdenEstimate.BURDEN_ESTIMATE.YEAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return BurdenEstimate.BURDEN_ESTIMATE.BURDEN_OUTCOME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return BurdenEstimate.BURDEN_ESTIMATE.STOCHASTIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field7() {
        return BurdenEstimate.BURDEN_ESTIMATE.VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return BurdenEstimate.BURDEN_ESTIMATE.AGE;
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
        return getBurdenEstimateSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getYear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getBurdenOutcome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component6() {
        return getStochastic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component7() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getAge();
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
        return getBurdenEstimateSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getYear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getBurdenOutcome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getStochastic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value7() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getAge();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value2(Integer value) {
        setBurdenEstimateSet(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value3(String value) {
        setCountry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value4(Integer value) {
        setYear(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value5(Integer value) {
        setBurdenOutcome(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value6(Boolean value) {
        setStochastic(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value7(BigDecimal value) {
        setValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord value8(Integer value) {
        setAge(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateRecord values(Integer value1, Integer value2, String value3, Integer value4, Integer value5, Boolean value6, BigDecimal value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BurdenEstimateRecord
     */
    public BurdenEstimateRecord() {
        super(BurdenEstimate.BURDEN_ESTIMATE);
    }

    /**
     * Create a detached, initialised BurdenEstimateRecord
     */
    public BurdenEstimateRecord(Integer id, Integer burdenEstimateSet, String country, Integer year, Integer burdenOutcome, Boolean stochastic, BigDecimal value, Integer age) {
        super(BurdenEstimate.BURDEN_ESTIMATE);

        set(0, id);
        set(1, burdenEstimateSet);
        set(2, country);
        set(3, year);
        set(4, burdenOutcome);
        set(5, stochastic);
        set(6, value);
        set(7, age);
    }
}
