/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row10;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData8Record;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SelectBurdenData8 extends TableImpl<SelectBurdenData8Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.select_burden_data8</code>
     */
    public static final SelectBurdenData8 SELECT_BURDEN_DATA8 = new SelectBurdenData8();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SelectBurdenData8Record> getRecordType() {
        return SelectBurdenData8Record.class;
    }

    /**
     * The column <code>public.select_burden_data8.country</code>.
     */
    public final TableField<SelectBurdenData8Record, String> COUNTRY = createField(DSL.name("country"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.select_burden_data8.year</code>.
     */
    public final TableField<SelectBurdenData8Record, Integer> YEAR = createField(DSL.name("year"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.select_burden_data8.value1</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE1 = createField(DSL.name("value1"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data8.value2</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE2 = createField(DSL.name("value2"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data8.value3</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE3 = createField(DSL.name("value3"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data8.value4</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE4 = createField(DSL.name("value4"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data8.value5</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE5 = createField(DSL.name("value5"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data8.value6</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE6 = createField(DSL.name("value6"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data8.value7</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE7 = createField(DSL.name("value7"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data8.value8</code>.
     */
    public final TableField<SelectBurdenData8Record, BigDecimal> VALUE8 = createField(DSL.name("value8"), SQLDataType.NUMERIC, this, "");

    private SelectBurdenData8(Name alias, Table<SelectBurdenData8Record> aliased) {
        this(alias, aliased, new Field[] {
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER)
        });
    }

    private SelectBurdenData8(Name alias, Table<SelectBurdenData8Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.function());
    }

    /**
     * Create an aliased <code>public.select_burden_data8</code> table reference
     */
    public SelectBurdenData8(String alias) {
        this(DSL.name(alias), SELECT_BURDEN_DATA8);
    }

    /**
     * Create an aliased <code>public.select_burden_data8</code> table reference
     */
    public SelectBurdenData8(Name alias) {
        this(alias, SELECT_BURDEN_DATA8);
    }

    /**
     * Create a <code>public.select_burden_data8</code> table reference
     */
    public SelectBurdenData8() {
        this(DSL.name("select_burden_data8"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public SelectBurdenData8 as(String alias) {
        return new SelectBurdenData8(DSL.name(alias), this, parameters);
    }

    @Override
    public SelectBurdenData8 as(Name alias) {
        return new SelectBurdenData8(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenData8 rename(String name) {
        return new SelectBurdenData8(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenData8 rename(Name name) {
        return new SelectBurdenData8(name, null, parameters);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<String, Integer, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal, BigDecimal> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData8 call(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
        , Integer set6
        , Integer outcome6
        , Integer set7
        , Integer outcome7
        , Integer set8
        , Integer outcome8
    ) {
        SelectBurdenData8 result = new SelectBurdenData8(DSL.name("select_burden_data8"), null, new Field[] {
            DSL.val(set1, SQLDataType.INTEGER),
            DSL.val(outcome1, SQLDataType.INTEGER),
            DSL.val(set2, SQLDataType.INTEGER),
            DSL.val(outcome2, SQLDataType.INTEGER),
            DSL.val(set3, SQLDataType.INTEGER),
            DSL.val(outcome3, SQLDataType.INTEGER),
            DSL.val(set4, SQLDataType.INTEGER),
            DSL.val(outcome4, SQLDataType.INTEGER),
            DSL.val(set5, SQLDataType.INTEGER),
            DSL.val(outcome5, SQLDataType.INTEGER),
            DSL.val(set6, SQLDataType.INTEGER),
            DSL.val(outcome6, SQLDataType.INTEGER),
            DSL.val(set7, SQLDataType.INTEGER),
            DSL.val(outcome7, SQLDataType.INTEGER),
            DSL.val(set8, SQLDataType.INTEGER),
            DSL.val(outcome8, SQLDataType.INTEGER)
        });

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData8 call(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
        , Field<Integer> set4
        , Field<Integer> outcome4
        , Field<Integer> set5
        , Field<Integer> outcome5
        , Field<Integer> set6
        , Field<Integer> outcome6
        , Field<Integer> set7
        , Field<Integer> outcome7
        , Field<Integer> set8
        , Field<Integer> outcome8
    ) {
        SelectBurdenData8 result = new SelectBurdenData8(DSL.name("select_burden_data8"), null, new Field[] {
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5,
            set6,
            outcome6,
            set7,
            outcome7,
            set8,
            outcome8
        });

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }
}
