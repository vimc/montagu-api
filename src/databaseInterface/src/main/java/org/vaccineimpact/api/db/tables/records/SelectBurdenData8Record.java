/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.TableRecordImpl;
import org.vaccineimpact.api.db.tables.SelectBurdenData8;


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
public class SelectBurdenData8Record extends TableRecordImpl<SelectBurdenData8Record> implements Record10<String, Integer, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal> {

    private static final long serialVersionUID = -2038119081;

    /**
     * Setter for <code>public.select_burden_data8.country</code>.
     */
    public void setCountry(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.country</code>.
     */
    public String getCountry() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.select_burden_data8.year</code>.
     */
    public void setYear(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.year</code>.
     */
    public Integer getYear() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.select_burden_data8.value1</code>.
     */
    public void setValue1(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value1</code>.
     */
    public BigDecimal getValue1() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>public.select_burden_data8.value2</code>.
     */
    public void setValue2(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value2</code>.
     */
    public BigDecimal getValue2() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>public.select_burden_data8.value3</code>.
     */
    public void setValue3(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value3</code>.
     */
    public BigDecimal getValue3() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>public.select_burden_data8.value4</code>.
     */
    public void setValue4(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value4</code>.
     */
    public BigDecimal getValue4() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>public.select_burden_data8.value5</code>.
     */
    public void setValue5(BigDecimal value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value5</code>.
     */
    public BigDecimal getValue5() {
        return (BigDecimal) get(6);
    }

    /**
     * Setter for <code>public.select_burden_data8.value6</code>.
     */
    public void setValue6(BigDecimal value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value6</code>.
     */
    public BigDecimal getValue6() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>public.select_burden_data8.value7</code>.
     */
    public void setValue7(BigDecimal value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value7</code>.
     */
    public BigDecimal getValue7() {
        return (BigDecimal) get(8);
    }

    /**
     * Setter for <code>public.select_burden_data8.value8</code>.
     */
    public void setValue8(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.select_burden_data8.value8</code>.
     */
    public BigDecimal getValue8() {
        return (BigDecimal) get(9);
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<String, Integer, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<String, Integer, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.YEAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field4() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field5() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field7() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field8() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE6;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field9() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE7;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field10() {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.VALUE8;
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
        return getValue1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component4() {
        return getValue2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component5() {
        return getValue3();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getValue4();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component7() {
        return getValue5();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component8() {
        return getValue6();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component9() {
        return getValue7();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component10() {
        return getValue8();
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
        return getValue1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value4() {
        return getValue2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value5() {
        return getValue3();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getValue4();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value7() {
        return getValue5();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value8() {
        return getValue6();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value9() {
        return getValue7();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value10() {
        return getValue8();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value1(String value) {
        setCountry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value2(Integer value) {
        setYear(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value3(BigDecimal value) {
        setValue1(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value4(BigDecimal value) {
        setValue2(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value5(BigDecimal value) {
        setValue3(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value6(BigDecimal value) {
        setValue4(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value7(BigDecimal value) {
        setValue5(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value8(BigDecimal value) {
        setValue6(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value9(BigDecimal value) {
        setValue7(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record value10(BigDecimal value) {
        setValue8(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData8Record values(String value1, Integer value2, BigDecimal value3, BigDecimal value4, BigDecimal value5, BigDecimal value6, BigDecimal value7, BigDecimal value8, BigDecimal value9, BigDecimal value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SelectBurdenData8Record
     */
    public SelectBurdenData8Record() {
        super(SelectBurdenData8.SELECT_BURDEN_DATA8);
    }

    /**
     * Create a detached, initialised SelectBurdenData8Record
     */
    public SelectBurdenData8Record(String country, Integer year, BigDecimal value1, BigDecimal value2, BigDecimal value3, BigDecimal value4, BigDecimal value5, BigDecimal value6, BigDecimal value7, BigDecimal value8) {
        super(SelectBurdenData8.SELECT_BURDEN_DATA8);

        set(0, country);
        set(1, year);
        set(2, value1);
        set(3, value2);
        set(4, value3);
        set(5, value4);
        set(6, value5);
        set(7, value6);
        set(8, value7);
        set(9, value8);
    }
}
