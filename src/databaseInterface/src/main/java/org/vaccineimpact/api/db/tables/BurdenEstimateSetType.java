/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Indexes;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.BurdenEstimateSetTypeRecord;


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
public class BurdenEstimateSetType extends TableImpl<BurdenEstimateSetTypeRecord> {

    private static final long serialVersionUID = -968383324;

    /**
     * The reference instance of <code>public.burden_estimate_set_type</code>
     */
    public static final BurdenEstimateSetType BURDEN_ESTIMATE_SET_TYPE = new BurdenEstimateSetType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BurdenEstimateSetTypeRecord> getRecordType() {
        return BurdenEstimateSetTypeRecord.class;
    }

    /**
     * The column <code>public.burden_estimate_set_type.code</code>.
     */
    public final TableField<BurdenEstimateSetTypeRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set_type.description</code>.
     */
    public final TableField<BurdenEstimateSetTypeRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set_type.is_valid_option</code>. New burden_estimate_set rows should only be created with set types that have is_valid_option to true. Those with false are for legacy data
     */
    public final TableField<BurdenEstimateSetTypeRecord, Boolean> IS_VALID_OPTION = createField("is_valid_option", org.jooq.impl.SQLDataType.BOOLEAN, this, "New burden_estimate_set rows should only be created with set types that have is_valid_option to true. Those with false are for legacy data");

    /**
     * Create a <code>public.burden_estimate_set_type</code> table reference
     */
    public BurdenEstimateSetType() {
        this(DSL.name("burden_estimate_set_type"), null);
    }

    /**
     * Create an aliased <code>public.burden_estimate_set_type</code> table reference
     */
    public BurdenEstimateSetType(String alias) {
        this(DSL.name(alias), BURDEN_ESTIMATE_SET_TYPE);
    }

    /**
     * Create an aliased <code>public.burden_estimate_set_type</code> table reference
     */
    public BurdenEstimateSetType(Name alias) {
        this(alias, BURDEN_ESTIMATE_SET_TYPE);
    }

    private BurdenEstimateSetType(Name alias, Table<BurdenEstimateSetTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenEstimateSetType(Name alias, Table<BurdenEstimateSetTypeRecord> aliased, Field<?>[] parameters) {
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
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.BURDEN_ESTIMATE_SET_TYPE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BurdenEstimateSetTypeRecord> getPrimaryKey() {
        return Keys.BURDEN_ESTIMATE_SET_TYPE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BurdenEstimateSetTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<BurdenEstimateSetTypeRecord>>asList(Keys.BURDEN_ESTIMATE_SET_TYPE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateSetType as(String alias) {
        return new BurdenEstimateSetType(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateSetType as(Name alias) {
        return new BurdenEstimateSetType(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateSetType rename(String name) {
        return new BurdenEstimateSetType(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateSetType rename(Name name) {
        return new BurdenEstimateSetType(name, null);
    }
}
