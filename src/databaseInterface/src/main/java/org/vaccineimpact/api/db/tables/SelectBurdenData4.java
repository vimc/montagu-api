/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData4Record;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SelectBurdenData4 extends TableImpl<SelectBurdenData4Record> {

    private static final long serialVersionUID = 1036595917;

    /**
     * The reference instance of <code>public.select_burden_data4</code>
     */
    public static final SelectBurdenData4 SELECT_BURDEN_DATA4 = new SelectBurdenData4();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SelectBurdenData4Record> getRecordType() {
        return SelectBurdenData4Record.class;
    }

    /**
     * The column <code>public.select_burden_data4.country</code>.
     */
    public final TableField<SelectBurdenData4Record, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.select_burden_data4.year</code>.
     */
    public final TableField<SelectBurdenData4Record, Integer> YEAR = createField("year", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.select_burden_data4.value1</code>.
     */
    public final TableField<SelectBurdenData4Record, BigDecimal> VALUE1 = createField("value1", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data4.value2</code>.
     */
    public final TableField<SelectBurdenData4Record, BigDecimal> VALUE2 = createField("value2", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data4.value3</code>.
     */
    public final TableField<SelectBurdenData4Record, BigDecimal> VALUE3 = createField("value3", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.select_burden_data4.value4</code>.
     */
    public final TableField<SelectBurdenData4Record, BigDecimal> VALUE4 = createField("value4", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * Create a <code>public.select_burden_data4</code> table reference
     */
    public SelectBurdenData4() {
        this("select_burden_data4", null);
    }

    /**
     * Create an aliased <code>public.select_burden_data4</code> table reference
     */
    public SelectBurdenData4(String alias) {
        this(alias, SELECT_BURDEN_DATA4);
    }

    private SelectBurdenData4(String alias, Table<SelectBurdenData4Record> aliased) {
        this(alias, aliased, new Field[8]);
    }

    private SelectBurdenData4(String alias, Table<SelectBurdenData4Record> aliased, Field<?>[] parameters) {
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
    public SelectBurdenData4 as(String alias) {
        return new SelectBurdenData4(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenData4 rename(String name) {
        return new SelectBurdenData4(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData4 call(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4) {
        return new SelectBurdenData4(getName(), null, new Field[] { DSL.val(set1), DSL.val(outcome1), DSL.val(set2), DSL.val(outcome2), DSL.val(set3), DSL.val(outcome3), DSL.val(set4), DSL.val(outcome4) });
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenData4 call(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3, Field<Integer> set4, Field<Integer> outcome4) {
        return new SelectBurdenData4(getName(), null, new Field[] { set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4 });
    }
}
