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
import org.vaccineimpact.api.db.tables.records.SelectBurdenDataColRecord;


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
public class SelectBurdenDataCol extends TableImpl<SelectBurdenDataColRecord> {

    private static final long serialVersionUID = 1670765192;

    /**
     * The reference instance of <code>public.select_burden_data_col</code>
     */
    public static final SelectBurdenDataCol SELECT_BURDEN_DATA_COL = new SelectBurdenDataCol();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SelectBurdenDataColRecord> getRecordType() {
        return SelectBurdenDataColRecord.class;
    }

    /**
     * The column <code>public.select_burden_data_col.country</code>.
     */
    public final TableField<SelectBurdenDataColRecord, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.select_burden_data_col.year</code>.
     */
    public final TableField<SelectBurdenDataColRecord, Integer> YEAR = createField("year", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.select_burden_data_col.value</code>.
     */
    public final TableField<SelectBurdenDataColRecord, BigDecimal> VALUE = createField("value", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * Create a <code>public.select_burden_data_col</code> table reference
     */
    public SelectBurdenDataCol() {
        this(DSL.name("select_burden_data_col"), null);
    }

    /**
     * Create an aliased <code>public.select_burden_data_col</code> table reference
     */
    public SelectBurdenDataCol(String alias) {
        this(DSL.name(alias), SELECT_BURDEN_DATA_COL);
    }

    /**
     * Create an aliased <code>public.select_burden_data_col</code> table reference
     */
    public SelectBurdenDataCol(Name alias) {
        this(alias, SELECT_BURDEN_DATA_COL);
    }

    private SelectBurdenDataCol(Name alias, Table<SelectBurdenDataColRecord> aliased) {
        this(alias, aliased, new Field[2]);
    }

    private SelectBurdenDataCol(Name alias, Table<SelectBurdenDataColRecord> aliased, Field<?>[] parameters) {
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
    public SelectBurdenDataCol as(String alias) {
        return new SelectBurdenDataCol(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectBurdenDataCol as(Name alias) {
        return new SelectBurdenDataCol(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenDataCol rename(String name) {
        return new SelectBurdenDataCol(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public SelectBurdenDataCol rename(Name name) {
        return new SelectBurdenDataCol(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenDataCol call(Integer setId, Integer outcomeId) {
        return new SelectBurdenDataCol(DSL.name(getName()), null, new Field[] { 
              DSL.val(setId, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(outcomeId, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenDataCol call(Field<Integer> setId, Field<Integer> outcomeId) {
        return new SelectBurdenDataCol(DSL.name(getName()), null, new Field[] { 
              setId
            , outcomeId
        });
    }
}
