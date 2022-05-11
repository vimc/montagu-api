/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row15;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.InstantConverter;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.BurdenEstimateSetRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BurdenEstimateSet extends TableImpl<BurdenEstimateSetRecord> {

    private static final long serialVersionUID = 1L;

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
    public final TableField<BurdenEstimateSetRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.burden_estimate_set.model_version</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Integer> MODEL_VERSION = createField(DSL.name("model_version"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.responsibility</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Integer> RESPONSIBILITY = createField(DSL.name("responsibility"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.run_info</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> RUN_INFO = createField(DSL.name("run_info"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.validation</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> VALIDATION = createField(DSL.name("validation"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.burden_estimate_set.comment</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> COMMENT = createField(DSL.name("comment"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.burden_estimate_set.interpolated</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Boolean> INTERPOLATED = createField(DSL.name("interpolated"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.complete</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Boolean> COMPLETE = createField(DSL.name("complete"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field("false", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.burden_estimate_set.uploaded_by</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> UPLOADED_BY = createField(DSL.name("uploaded_by"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_set.uploaded_on</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Instant> UPLOADED_ON = createField(DSL.name("uploaded_on"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("date_trunc('milliseconds'::text, now())", SQLDataType.LOCALDATETIME)), this, "", new InstantConverter());

    /**
     * The column <code>public.burden_estimate_set.status</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.CLOB.nullable(false).defaultValue(DSL.field("'complete'::text", SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.burden_estimate_set.set_type</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> SET_TYPE = createField(DSL.name("set_type"), SQLDataType.CLOB.nullable(false).defaultValue(DSL.field("'central-unknown'::text", SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.burden_estimate_set.set_type_details</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> SET_TYPE_DETAILS = createField(DSL.name("set_type_details"), SQLDataType.CLOB, this, "");

    /**
     * The column
     * <code>public.burden_estimate_set.model_run_parameter_set</code>.
     */
    public final TableField<BurdenEstimateSetRecord, Integer> MODEL_RUN_PARAMETER_SET = createField(DSL.name("model_run_parameter_set"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.burden_estimate_set.original_filename</code>.
     */
    public final TableField<BurdenEstimateSetRecord, String> ORIGINAL_FILENAME = createField(DSL.name("original_filename"), SQLDataType.CLOB, this, "");

    private BurdenEstimateSet(Name alias, Table<BurdenEstimateSetRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenEstimateSet(Name alias, Table<BurdenEstimateSetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
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

    /**
     * Create a <code>public.burden_estimate_set</code> table reference
     */
    public BurdenEstimateSet() {
        this(DSL.name("burden_estimate_set"), null);
    }

    public <O extends Record> BurdenEstimateSet(Table<O> child, ForeignKey<O, BurdenEstimateSetRecord> key) {
        super(child, key, BURDEN_ESTIMATE_SET);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<BurdenEstimateSetRecord, Integer> getIdentity() {
        return (Identity<BurdenEstimateSetRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<BurdenEstimateSetRecord> getPrimaryKey() {
        return Keys.BURDEN_ESTIMATE_SET_PKEY;
    }

    @Override
    public List<ForeignKey<BurdenEstimateSetRecord, ?>> getReferences() {
        return Arrays.asList(Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_MODEL_VERSION_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_RESPONSIBILITY_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_UPLOADED_BY_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_STATUS_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_SET_TYPE_FKEY, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_MODEL_RUN_PARAMETER_SET_FKEY);
    }

    private transient ModelVersion _modelVersion;
    private transient Responsibility _responsibility;
    private transient AppUser _appUser;
    private transient BurdenEstimateSetStatus _burdenEstimateSetStatus;
    private transient BurdenEstimateSetType _burdenEstimateSetType;
    private transient ModelRunParameterSet _modelRunParameterSet;

    /**
     * Get the implicit join path to the <code>public.model_version</code>
     * table.
     */
    public ModelVersion modelVersion() {
        if (_modelVersion == null)
            _modelVersion = new ModelVersion(this, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_MODEL_VERSION_FKEY);

        return _modelVersion;
    }

    /**
     * Get the implicit join path to the <code>public.responsibility</code>
     * table.
     */
    public Responsibility responsibility() {
        if (_responsibility == null)
            _responsibility = new Responsibility(this, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_RESPONSIBILITY_FKEY);

        return _responsibility;
    }

    /**
     * Get the implicit join path to the <code>public.app_user</code> table.
     */
    public AppUser appUser() {
        if (_appUser == null)
            _appUser = new AppUser(this, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_UPLOADED_BY_FKEY);

        return _appUser;
    }

    /**
     * Get the implicit join path to the
     * <code>public.burden_estimate_set_status</code> table.
     */
    public BurdenEstimateSetStatus burdenEstimateSetStatus() {
        if (_burdenEstimateSetStatus == null)
            _burdenEstimateSetStatus = new BurdenEstimateSetStatus(this, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_STATUS_FKEY);

        return _burdenEstimateSetStatus;
    }

    /**
     * Get the implicit join path to the
     * <code>public.burden_estimate_set_type</code> table.
     */
    public BurdenEstimateSetType burdenEstimateSetType() {
        if (_burdenEstimateSetType == null)
            _burdenEstimateSetType = new BurdenEstimateSetType(this, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_SET_TYPE_FKEY);

        return _burdenEstimateSetType;
    }

    /**
     * Get the implicit join path to the
     * <code>public.model_run_parameter_set</code> table.
     */
    public ModelRunParameterSet modelRunParameterSet() {
        if (_modelRunParameterSet == null)
            _modelRunParameterSet = new ModelRunParameterSet(this, Keys.BURDEN_ESTIMATE_SET__BURDEN_ESTIMATE_SET_MODEL_RUN_PARAMETER_SET_FKEY);

        return _modelRunParameterSet;
    }

    @Override
    public BurdenEstimateSet as(String alias) {
        return new BurdenEstimateSet(DSL.name(alias), this);
    }

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

    // -------------------------------------------------------------------------
    // Row15 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row15<Integer, Integer, Integer, String, String, String, Boolean, Boolean, String, Instant, String, String, String, Integer, String> fieldsRow() {
        return (Row15) super.fieldsRow();
    }
}
