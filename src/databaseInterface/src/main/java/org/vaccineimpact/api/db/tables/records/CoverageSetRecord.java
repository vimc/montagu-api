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
import org.vaccineimpact.api.db.tables.CoverageSet;


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
public class CoverageSetRecord extends UpdatableRecordImpl<CoverageSetRecord> implements Record6<Integer, String, String, String, String, String> {

    private static final long serialVersionUID = 1179574221;

    /**
     * Setter for <code>public.coverage_set.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.coverage_set.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.coverage_set.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.coverage_set.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.coverage_set.touchstone</code>.
     */
    public void setTouchstone(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.coverage_set.touchstone</code>.
     */
    public String getTouchstone() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.coverage_set.vaccine</code>.
     */
    public void setVaccine(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.coverage_set.vaccine</code>.
     */
    public String getVaccine() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.coverage_set.gavi_support_level</code>.
     */
    public void setGaviSupportLevel(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.coverage_set.gavi_support_level</code>.
     */
    public String getGaviSupportLevel() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.coverage_set.activity_type</code>.
     */
    public void setActivityType(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.coverage_set.activity_type</code>.
     */
    public String getActivityType() {
        return (String) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CoverageSet.COVERAGE_SET.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return CoverageSet.COVERAGE_SET.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return CoverageSet.COVERAGE_SET.TOUCHSTONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return CoverageSet.COVERAGE_SET.VACCINE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CoverageSet.COVERAGE_SET.GAVI_SUPPORT_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return CoverageSet.COVERAGE_SET.ACTIVITY_TYPE;
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
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getTouchstone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getVaccine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getGaviSupportLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getActivityType();
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTouchstone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getVaccine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGaviSupportLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getActivityType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetRecord value3(String value) {
        setTouchstone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetRecord value4(String value) {
        setVaccine(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetRecord value5(String value) {
        setGaviSupportLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetRecord value6(String value) {
        setActivityType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6) {
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
     * Create a detached CoverageSetRecord
     */
    public CoverageSetRecord() {
        super(CoverageSet.COVERAGE_SET);
    }

    /**
     * Create a detached, initialised CoverageSetRecord
     */
    public CoverageSetRecord(Integer id, String name, String touchstone, String vaccine, String gaviSupportLevel, String activityType) {
        super(CoverageSet.COVERAGE_SET);

        set(0, id);
        set(1, name);
        set(2, touchstone);
        set(3, vaccine);
        set(4, gaviSupportLevel);
        set(5, activityType);
    }
}
