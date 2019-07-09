/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.CountryCofinance;


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
public class CountryCofinanceRecord extends UpdatableRecordImpl<CountryCofinanceRecord> implements Record5<Integer, String, String, Integer, String> {

    private static final long serialVersionUID = 1957414411;

    /**
     * Setter for <code>public.country_cofinance.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.country_cofinance.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.country_cofinance.touchstone</code>.
     */
    public void setTouchstone(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.country_cofinance.touchstone</code>.
     */
    public String getTouchstone() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.country_cofinance.country</code>.
     */
    public void setCountry(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.country_cofinance.country</code>.
     */
    public String getCountry() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.country_cofinance.year</code>.
     */
    public void setYear(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.country_cofinance.year</code>.
     */
    public Integer getYear() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.country_cofinance.cofinance_status</code>.
     */
    public void setCofinanceStatus(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.country_cofinance.cofinance_status</code>.
     */
    public String getCofinanceStatus() {
        return (String) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, Integer, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, Integer, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CountryCofinance.COUNTRY_COFINANCE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return CountryCofinance.COUNTRY_COFINANCE.TOUCHSTONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return CountryCofinance.COUNTRY_COFINANCE.COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return CountryCofinance.COUNTRY_COFINANCE.YEAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CountryCofinance.COUNTRY_COFINANCE.COFINANCE_STATUS;
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
        return getTouchstone();
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
    public String component5() {
        return getCofinanceStatus();
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
        return getTouchstone();
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
    public String value5() {
        return getCofinanceStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryCofinanceRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryCofinanceRecord value2(String value) {
        setTouchstone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryCofinanceRecord value3(String value) {
        setCountry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryCofinanceRecord value4(Integer value) {
        setYear(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryCofinanceRecord value5(String value) {
        setCofinanceStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryCofinanceRecord values(Integer value1, String value2, String value3, Integer value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CountryCofinanceRecord
     */
    public CountryCofinanceRecord() {
        super(CountryCofinance.COUNTRY_COFINANCE);
    }

    /**
     * Create a detached, initialised CountryCofinanceRecord
     */
    public CountryCofinanceRecord(Integer id, String touchstone, String country, Integer year, String cofinanceStatus) {
        super(CountryCofinance.COUNTRY_COFINANCE);

        set(0, id);
        set(1, touchstone);
        set(2, country);
        set(3, year);
        set(4, cofinanceStatus);
    }
}
