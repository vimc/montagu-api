/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData3Record;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SelectBurdenData3 extends TableImpl<SelectBurdenData3Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.select_burden_data3</code>
     */
    public static final SelectBurdenData3 SELECT_BURDEN_DATA3 = new SelectBurdenData3();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SelectBurdenData3Record> getRecordType() {
        return SelectBurdenData3Record.class;
    }

    /**
     * The column <code>public.select_burden_data3.country</code>.
     */
    public final TableField<SelectBurdenData3Record, String> COUNTRY = createField(DSL.name("country"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.select_burden_data3.year</code>.
     */
    public final TableField<SelectBurdenData3Record, Integer> YEAR = createField(DSL.name("year"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.select_burden_data3.value1</code>.
     */
    public final TableField<SelectBurdenData3Record, BigDecimal> VALUE1 = createField(DSL.name("value1"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data3.value2</code>.
     */
    public final TableField<SelectBurdenData3Record, BigDecimal> VALUE2 = createField(DSL.name("value2"), SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data3.value3</code>.
     */
    public final TableField<SelectBurdenData3Record, BigDecimal> VALUE3 = createField(DSL.name("value3"), SQLDataType.NUMERIC, this, "");

    private SelectBurdenData3(Name alias, Table<SelectBurdenData3Record> aliased) {
        this(alias, aliased, new Field[] {
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER)
        });
    }

    private SelectBurdenData3(Name alias, Table<SelectBurdenData3Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.function());
    }

    /**
     * Create an aliased <code>public.select_burden_data3</code> table reference
     */
    public SelectBurdenData3(String alias) {
        this(DSL.name(alias), SELECT_BURDEN_DATA3);
    }

    /**
     * Create an aliased <code>public.select_burden_data3</code> table reference
     */
    public SelectBurdenData3(Name alias) {
        this(alias, SELECT_BURDEN_DATA3);
    }

    /**
     * Create a <code>public.select_burden_data3</code> table reference
     */
    public SelectBurdenData3() {
        this(DSL.name("select_burden_data3"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public SelectBurdenData3 as(String alias) {
        return new SelectBurdenData3(DSL.name(alias), this, parameters);
    }

    @Override
    public SelectBurdenData3 as(Name alias) {
        return new SelectBurdenData3(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenData3 rename(String name) {
        return new SelectBurdenData3(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenData3 rename(Name name) {
        return new SelectBurdenData3(name, null, parameters);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<String, Integer, BigDecimal, BigDecimal, BigDecimal> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData3 call(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
    ) {
        SelectBurdenData3 result = new SelectBurdenData3(DSL.name("select_burden_data3"), null, new Field[] {
            DSL.val(set1, SQLDataType.INTEGER),
            DSL.val(outcome1, SQLDataType.INTEGER),
            DSL.val(set2, SQLDataType.INTEGER),
            DSL.val(outcome2, SQLDataType.INTEGER),
            DSL.val(set3, SQLDataType.INTEGER),
            DSL.val(outcome3, SQLDataType.INTEGER)
        });

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData3 call(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
    ) {
        SelectBurdenData3 result = new SelectBurdenData3(DSL.name("select_burden_data3"), null, new Field[] {
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3
        });

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }
}
