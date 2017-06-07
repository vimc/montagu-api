/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.BurdenEstimateRecord;


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
public class BurdenEstimate extends TableImpl<BurdenEstimateRecord> {

    private static final long serialVersionUID = -1183518021;

    /**
     * The reference instance of <code>public.burden_estimate</code>
     */
    public static final BurdenEstimate BURDEN_ESTIMATE = new BurdenEstimate();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BurdenEstimateRecord> getRecordType() {
        return BurdenEstimateRecord.class;
    }

    /**
     * The column <code>public.burden_estimate.id</code>.
     */
    public final TableField<BurdenEstimateRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('burden_estimate_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.burden_estimate.burden_estimate_set</code>.
     */
    public final TableField<BurdenEstimateRecord, Integer> BURDEN_ESTIMATE_SET = createField("burden_estimate_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.country</code>.
     */
    public final TableField<BurdenEstimateRecord, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.year</code>.
     */
    public final TableField<BurdenEstimateRecord, Integer> YEAR = createField("year", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.burden_outcome</code>.
     */
    public final TableField<BurdenEstimateRecord, Integer> BURDEN_OUTCOME = createField("burden_outcome", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.burden_estimate.stochastic</code>.
     */
    public final TableField<BurdenEstimateRecord, Boolean> STOCHASTIC = createField("stochastic", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.value</code>.
     */
    public final TableField<BurdenEstimateRecord, BigDecimal> VALUE = createField("value", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * Create a <code>public.burden_estimate</code> table reference
     */
    public BurdenEstimate() {
        this("burden_estimate", null);
    }

    /**
     * Create an aliased <code>public.burden_estimate</code> table reference
     */
    public BurdenEstimate(String alias) {
        this(alias, BURDEN_ESTIMATE);
    }

    private BurdenEstimate(String alias, Table<BurdenEstimateRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenEstimate(String alias, Table<BurdenEstimateRecord> aliased, Field<?>[] parameters) {
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
    public Identity<BurdenEstimateRecord, Integer> getIdentity() {
        return Keys.IDENTITY_BURDEN_ESTIMATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BurdenEstimateRecord> getPrimaryKey() {
        return Keys.BURDEN_ESTIMATE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BurdenEstimateRecord>> getKeys() {
        return Arrays.<UniqueKey<BurdenEstimateRecord>>asList(Keys.BURDEN_ESTIMATE_PKEY, Keys.BURDEN_ESTIMATE_BURDEN_ESTIMATE_SET_COUNTRY_YEAR_BURDEN_OUT_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<BurdenEstimateRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<BurdenEstimateRecord, ?>>asList(Keys.BURDEN_ESTIMATE__BURDEN_ESTIMATE_BURDEN_ESTIMATE_SET_FKEY, Keys.BURDEN_ESTIMATE__BURDEN_ESTIMATE_COUNTRY_FKEY, Keys.BURDEN_ESTIMATE__BURDEN_ESTIMATE_BURDEN_OUTCOME_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimate as(String alias) {
        return new BurdenEstimate(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimate rename(String name) {
        return new BurdenEstimate(name, null);
    }
}
