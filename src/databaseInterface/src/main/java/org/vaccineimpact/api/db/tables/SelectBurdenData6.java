/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData6Record;


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
public class SelectBurdenData6 extends TableImpl<SelectBurdenData6Record> {

    private static final long serialVersionUID = -875327643;

    /**
     * The reference instance of <code>public.select_burden_data6</code>
     */
    public static final SelectBurdenData6 SELECT_BURDEN_DATA6 = new SelectBurdenData6();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SelectBurdenData6Record> getRecordType() {
        return SelectBurdenData6Record.class;
    }

    /**
     * The column <code>public.select_burden_data6.country</code>.
     */
    public final TableField<SelectBurdenData6Record, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.select_burden_data6.year</code>.
     */
    public final TableField<SelectBurdenData6Record, Integer> YEAR = createField("year", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.select_burden_data6.value1</code>.
     */
    public final TableField<SelectBurdenData6Record, BigDecimal> VALUE1 = createField("value1", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data6.value2</code>.
     */
    public final TableField<SelectBurdenData6Record, BigDecimal> VALUE2 = createField("value2", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data6.value3</code>.
     */
    public final TableField<SelectBurdenData6Record, BigDecimal> VALUE3 = createField("value3", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data6.value4</code>.
     */
    public final TableField<SelectBurdenData6Record, BigDecimal> VALUE4 = createField("value4", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data6.value5</code>.
     */
    public final TableField<SelectBurdenData6Record, BigDecimal> VALUE5 = createField("value5", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data6.value6</code>.
     */
    public final TableField<SelectBurdenData6Record, BigDecimal> VALUE6 = createField("value6", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * Create a <code>public.select_burden_data6</code> table reference
     */
    public SelectBurdenData6() {
        this(DSL.name("select_burden_data6"), null);
    }

    /**
     * Create an aliased <code>public.select_burden_data6</code> table reference
     */
    public SelectBurdenData6(String alias) {
        this(DSL.name(alias), SELECT_BURDEN_DATA6);
    }

    /**
     * Create an aliased <code>public.select_burden_data6</code> table reference
     */
    public SelectBurdenData6(Name alias) {
        this(alias, SELECT_BURDEN_DATA6);
    }

    private SelectBurdenData6(Name alias, Table<SelectBurdenData6Record> aliased) {
        this(alias, aliased, new Field[12]);
    }

    private SelectBurdenData6(Name alias, Table<SelectBurdenData6Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData6 as(String alias) {
        return new SelectBurdenData6(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenData6 as(Name alias) {
        return new SelectBurdenData6(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenData6 rename(String name) {
        return new SelectBurdenData6(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenData6 rename(Name name) {
        return new SelectBurdenData6(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData6 call(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5, Integer set6, Integer outcome6) {
        return new SelectBurdenData6(DSL.name(getName()), null, new Field[] { 
              DSL.val(set1, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(outcome1, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(set2, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(outcome2, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(set3, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(outcome3, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(set4, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(outcome4, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(set5, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(outcome5, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(set6, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(outcome6, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData6 call(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3, Field<Integer> set4, Field<Integer> outcome4, Field<Integer> set5, Field<Integer> outcome5, Field<Integer> set6, Field<Integer> outcome6) {
        return new SelectBurdenData6(DSL.name(getName()), null, new Field[] { 
              set1
            , outcome1
            , set2
            , outcome2
            , set3
            , outcome3
            , set4
            , outcome4
            , set5
            , outcome5
            , set6
            , outcome6
        });
    }
}
