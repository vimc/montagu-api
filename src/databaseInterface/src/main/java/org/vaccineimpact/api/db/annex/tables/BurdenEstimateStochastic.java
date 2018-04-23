/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.annex.tables;


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
import org.vaccineimpact.api.db.annex.Indexes;
import org.vaccineimpact.api.db.annex.Keys;
import org.vaccineimpact.api.db.annex.Public;
import org.vaccineimpact.api.db.annex.tables.records.BurdenEstimateStochasticRecord;


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
public class BurdenEstimateStochastic extends TableImpl<BurdenEstimateStochasticRecord> {

    private static final long serialVersionUID = -1626816819;

    /**
     * The reference instance of <code>public.burden_estimate_stochastic</code>
     */
    public static final BurdenEstimateStochastic BURDEN_ESTIMATE_STOCHASTIC = new BurdenEstimateStochastic();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BurdenEstimateStochasticRecord> getRecordType() {
        return BurdenEstimateStochasticRecord.class;
    }

    /**
     * The column <code>public.burden_estimate_stochastic.burden_estimate_set</code>.
     */
    public final TableField<BurdenEstimateStochasticRecord, Integer> BURDEN_ESTIMATE_SET = createField("burden_estimate_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_stochastic.model_run</code>.
     */
    public final TableField<BurdenEstimateStochasticRecord, Integer> MODEL_RUN = createField("model_run", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_stochastic.country</code>.
     */
    public final TableField<BurdenEstimateStochasticRecord, Short> COUNTRY = createField("country", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_stochastic.year</code>.
     */
    public final TableField<BurdenEstimateStochasticRecord, Short> YEAR = createField("year", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_stochastic.burden_outcome</code>.
     */
    public final TableField<BurdenEstimateStochasticRecord, Short> BURDEN_OUTCOME = createField("burden_outcome", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_stochastic.value</code>.
     */
    public final TableField<BurdenEstimateStochasticRecord, Float> VALUE = createField("value", org.jooq.impl.SQLDataType.REAL, this, "");

    /**
     * The column <code>public.burden_estimate_stochastic.age</code>.
     */
    public final TableField<BurdenEstimateStochasticRecord, Short> AGE = createField("age", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * Create a <code>public.burden_estimate_stochastic</code> table reference
     */
    public BurdenEstimateStochastic() {
        this(DSL.name("burden_estimate_stochastic"), null);
    }

    /**
     * Create an aliased <code>public.burden_estimate_stochastic</code> table reference
     */
    public BurdenEstimateStochastic(String alias) {
        this(DSL.name(alias), BURDEN_ESTIMATE_STOCHASTIC);
    }

    /**
     * Create an aliased <code>public.burden_estimate_stochastic</code> table reference
     */
    public BurdenEstimateStochastic(Name alias) {
        this(alias, BURDEN_ESTIMATE_STOCHASTIC);
    }

    private BurdenEstimateStochastic(Name alias, Table<BurdenEstimateStochasticRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenEstimateStochastic(Name alias, Table<BurdenEstimateStochasticRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.BURDEN_ESTIMATE_STOCHASTIC_BURDEN_ESTIMATE_SET_IDX, Indexes.BURDEN_ESTIMATE_STOCHASTIC_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BurdenEstimateStochasticRecord> getPrimaryKey() {
        return Keys.BURDEN_ESTIMATE_STOCHASTIC_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BurdenEstimateStochasticRecord>> getKeys() {
        return Arrays.<UniqueKey<BurdenEstimateStochasticRecord>>asList(Keys.BURDEN_ESTIMATE_STOCHASTIC_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateStochastic as(String alias) {
        return new BurdenEstimateStochastic(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateStochastic as(Name alias) {
        return new BurdenEstimateStochastic(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateStochastic rename(String name) {
        return new BurdenEstimateStochastic(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateStochastic rename(Name name) {
        return new BurdenEstimateStochastic(name, null);
    }
}
