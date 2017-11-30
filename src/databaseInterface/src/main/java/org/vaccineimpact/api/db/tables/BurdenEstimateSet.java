/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
import org.vaccineimpact.api.db.tables.records.BurdenEstimateSetRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BurdenEstimateSet extends TableImpl<BurdenEstimateSetRecord> {

    private static final long serialVersionUID = 913659704;

    /**
     * The reference instance of <code>public.burden_estimate_set</code>
     */
    public static final BurdenEstimateSet BURDEN_ESTIMATE_SET = new BurdenEstimateSet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BurdenEstimateSetRecord> getRecordType() {
        return BurdenEstimateSetRecord.class;
    }

    /**
     * The column <code>public.burden_estimate_set.id</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('burden_estimate_set_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.burden_estimate_set.model_version</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Integer> MODEL_VERSION = createField("model_version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.responsibility</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Integer> RESPONSIBILITY = createField("responsibility", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.run_info</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> RUN_INFO = createField("run_info", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.validation</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> VALIDATION = createField("validation", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.burden_estimate_set.comment</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.burden_estimate_set.interpolated</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Boolean> INTERPOLATED = createField("interpolated", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.complete</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Boolean> COMPLETE = createField("complete", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.burden_estimate_set.uploaded_by</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> UPLOADED_BY = createField("uploaded_by", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.uploaded_on</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Timestamp> UPLOADED_ON = createField("uploaded_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("date_trunc('milliseconds'::text, now())", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.burden_estimate_set.status</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.CLOB.nullable(false).defaultValue(org.jooq.impl.DSL.field("'complete'::text", org.jooq.impl.SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.burden_estimate_set.set_type</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> SET_TYPE = createField("set_type", org.jooq.impl.SQLDataType.CLOB.nullable(false).defaultValue(org.jooq.impl.DSL.field("'central-unknown'::text", org.jooq.impl.SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.burden_estimate_set.set_type_details</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> SET_TYPE_DETAILS = createField("set_type_details", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.burden_estimate_set.model_run_parameter_set</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Integer> MODEL_RUN_PARAMETER_SET = createField("model_run_parameter_set", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.burden_estimate_set</code> table reference
     */
    public BurdenEstimateSet() {
        this(DSL.name("burden_estimate_set"), null);
    }

    /**
     * Create an aliased <code>public.burden_estimate_set</code> table reference
     */
    public BurdenEstimateSet(String alias) {
        this(DSL.name(alias), BURDEN_ESTIMATE_SET);
    }

    /**
     * Create an aliased <code>public.burden_estimate_set</code> table reference
     */
    public BurdenEstimateSet(Name alias) {
        this(alias, BURDEN_ESTIMATE_SET);
    }

    private BurdenEstimateSet(Name alias, Table<BurdenEstimateSetRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenEstimateSet(Name alias, Table<BurdenEstimateSetRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.BURDEN_ESTIMATE_SET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<BurdenEstimateSetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_BURDEN_ESTIMATE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BurdenEstimateSetRecord> getPrimaryKey() {
        return Keys.BURDEN_ESTIMATE_SET_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BurdenEstimateSetRecord>> getKeys() {
        return Arrays.<UniqueKey<BurdenEstimateSetRecord>>asList(Keys.BURDEN_ESTIMATE_SET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<BurdenEstimateSetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<BurdenEstimateSetRecord, ?>>asList(Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_MODEL_VERSION_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_RESPONSIBILITY_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_UPLOADED_BY_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_STATUS_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_SET_TYPE_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_MODEL_RUN_PARAMETER_SET_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateSet as(String alias) {
        return new BurdenEstimateSet(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateSet as(Name alias) {
        return new BurdenEstimateSet(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateSet rename(String name) {
        return new BurdenEstimateSet(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateSet rename(Name name) {
        return new BurdenEstimateSet(name, null);
    }
}
