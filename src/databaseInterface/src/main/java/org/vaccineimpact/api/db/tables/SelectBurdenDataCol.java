/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.SelectBurdenDataColRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SelectBurdenDataCol extends TableImpl<SelectBurdenDataColRecord> {

    private static final long serialVersionUID = 1L;

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
    public final TableField<SelectBurdenDataColRecord, String> COUNTRY = createField(DSL.name("country"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.select_burden_data_col.year</code>.
     */
    public final TableField<SelectBurdenDataColRecord, Integer> YEAR = createField(DSL.name("year"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.select_burden_data_col.value</code>.
     */
    public final TableField<SelectBurdenDataColRecord, BigDecimal> VALUE = createField(DSL.name("value"), SQLDataType.NUMERIC, this, "");

    private SelectBurdenDataCol(Name alias, Table<SelectBurdenDataColRecord> aliased) {
        this(alias, aliased, new Field[] {
            DSL.val(null, SQLDataType.INTEGER),
            DSL.val(null, SQLDataType.INTEGER)
        });
    }

    private SelectBurdenDataCol(Name alias, Table<SelectBurdenDataColRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.function());
    }

    /**
     * Create an aliased <code>public.select_burden_data_col</code> table
     * reference
     */
    public SelectBurdenDataCol(String alias) {
        this(DSL.name(alias), SELECT_BURDEN_DATA_COL);
    }

    /**
     * Create an aliased <code>public.select_burden_data_col</code> table
     * reference
     */
    public SelectBurdenDataCol(Name alias) {
        this(alias, SELECT_BURDEN_DATA_COL);
    }

    /**
     * Create a <code>public.select_burden_data_col</code> table reference
     */
    public SelectBurdenDataCol() {
        this(DSL.name("select_burden_data_col"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public SelectBurdenDataCol as(String alias) {
        return new SelectBurdenDataCol(DSL.name(alias), this, parameters);
    }

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

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, Integer, BigDecimal> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenDataCol call(
          Integer setId
        , Integer outcomeId
    ) {
        SelectBurdenDataCol result = new SelectBurdenDataCol(DSL.name("select_burden_data_col"), null, new Field[] {
            DSL.val(setId, SQLDataType.INTEGER),
            DSL.val(outcomeId, SQLDataType.INTEGER)
        });

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }

    /**
     * Call this table-valued function
     */
    public SelectBurdenDataCol call(
          Field<Integer> setId
        , Field<Integer> outcomeId
    ) {
        SelectBurdenDataCol result = new SelectBurdenDataCol(DSL.name("select_burden_data_col"), null, new Field[] {
            setId,
            outcomeId
        });

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }
}
