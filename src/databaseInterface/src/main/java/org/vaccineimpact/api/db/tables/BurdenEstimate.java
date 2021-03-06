/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
import org.vaccineimpact.api.db.tables.records.BurdenEstimateRecord;


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
public class BurdenEstimate extends TableImpl<BurdenEstimateRecord> {

    private static final long serialVersionUID = 1284904635;

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
     * The column <code>public.burden_estimate.burden_estimate_set</code>.
     */
    public final TableField<BurdenEstimateRecord, Integer> BURDEN_ESTIMATE_SET = createField("burden_estimate_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.country</code>.
     */
    public final TableField<BurdenEstimateRecord, Short> COUNTRY = createField("country", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.year</code>.
     */
    public final TableField<BurdenEstimateRecord, Short> YEAR = createField("year", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.burden_outcome</code>.
     */
    public final TableField<BurdenEstimateRecord, Short> BURDEN_OUTCOME = createField("burden_outcome", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate.value</code>.
     */
    public final TableField<BurdenEstimateRecord, Float> VALUE = createField("value", org.jooq.impl.SQLDataType.REAL, this, "");

    /**
     * The column <code>public.burden_estimate.age</code>.
     */
    public final TableField<BurdenEstimateRecord, Short> AGE = createField("age", org.jooq.impl.SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.burden_estimate.model_run</code>.
     */
    public final TableField<BurdenEstimateRecord, Integer> MODEL_RUN = createField("model_run", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.burden_estimate</code> table reference
     */
    public BurdenEstimate() {
        this(DSL.name("burden_estimate"), null);
    }

    /**
     * Create an aliased <code>public.burden_estimate</code> table reference
     */
    public BurdenEstimate(String alias) {
        this(DSL.name(alias), BURDEN_ESTIMATE);
    }

    /**
     * Create an aliased <code>public.burden_estimate</code> table reference
     */
    public BurdenEstimate(Name alias) {
        this(alias, BURDEN_ESTIMATE);
    }

    private BurdenEstimate(Name alias, Table<BurdenEstimateRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenEstimate(Name alias, Table<BurdenEstimateRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.BURDEN_ESTIMATE_BURDEN_ESTIMATE_SET_IDX, Indexes.BURDEN_ESTIMATE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BurdenEstimateRecord>> getKeys() {
        return Arrays.<UniqueKey<BurdenEstimateRecord>>asList(Keys.BURDEN_ESTIMATE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<BurdenEstimateRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<BurdenEstimateRecord, ?>>asList(Keys.BURDEN_ESTIMATE__BURDEN_ESTIMATE_BURDEN_ESTIMATE_SET, Keys.BURDEN_ESTIMATE__BURDEN_ESTIMATE_COUNTRY_NID, Keys.BURDEN_ESTIMATE__BURDEN_ESTIMATE_BURDEN_OUTCOME, Keys.BURDEN_ESTIMATE__BURDEN_ESTIMATE_MODEL_RUN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimate as(String alias) {
        return new BurdenEstimate(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimate as(Name alias) {
        return new BurdenEstimate(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimate rename(String name) {
        return new BurdenEstimate(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimate rename(Name name) {
        return new BurdenEstimate(name, null);
    }
}
