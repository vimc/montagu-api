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
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ImpactEstimateSetRecord;


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
public class ImpactEstimateSet extends TableImpl<ImpactEstimateSetRecord> {

    private static final long serialVersionUID = -682149756;

    /**
     * The reference instance of <code>public.impact_estimate_set</code>
     */
    public static final ImpactEstimateSet IMPACT_ESTIMATE_SET = new ImpactEstimateSet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ImpactEstimateSetRecord> getRecordType() {
        return ImpactEstimateSetRecord.class;
    }

    /**
     * The column <code>public.impact_estimate_set.id</code>.
     */
    public final TableField<ImpactEstimateSetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('impact_estimate_set_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.impact_estimate_set.impact_estimate_recipe</code>.
     */
    public final TableField<ImpactEstimateSetRecord, Integer> IMPACT_ESTIMATE_RECIPE = createField("impact_estimate_recipe", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.impact_estimate_set.computed_on</code>.
     */
    public final TableField<ImpactEstimateSetRecord, Timestamp> COMPUTED_ON = createField("computed_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.impact_estimate_set.recipe_touchstone</code>.
     */
    public final TableField<ImpactEstimateSetRecord, String> RECIPE_TOUCHSTONE = createField("recipe_touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.impact_estimate_set.coverage_touchstone</code>.
     */
    public final TableField<ImpactEstimateSetRecord, String> COVERAGE_TOUCHSTONE = createField("coverage_touchstone", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.impact_estimate_set.focal_coverage_set</code>.
     */
    public final TableField<ImpactEstimateSetRecord, Integer> FOCAL_COVERAGE_SET = createField("focal_coverage_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.impact_estimate_set.focal_burden_estimate_set</code>.
     */
    public final TableField<ImpactEstimateSetRecord, Integer> FOCAL_BURDEN_ESTIMATE_SET = createField("focal_burden_estimate_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.impact_estimate_set</code> table reference
     */
    public ImpactEstimateSet() {
        this("impact_estimate_set", null);
    }

    /**
     * Create an aliased <code>public.impact_estimate_set</code> table reference
     */
    public ImpactEstimateSet(String alias) {
        this(alias, IMPACT_ESTIMATE_SET);
    }

    private ImpactEstimateSet(String alias, Table<ImpactEstimateSetRecord> aliased) {
        this(alias, aliased, null);
    }

    private ImpactEstimateSet(String alias, Table<ImpactEstimateSetRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ImpactEstimateSetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_IMPACT_ESTIMATE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ImpactEstimateSetRecord> getPrimaryKey() {
        return Keys.IMPACT_ESTIMATE_SET_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ImpactEstimateSetRecord>> getKeys() {
        return Arrays.<UniqueKey<ImpactEstimateSetRecord>>asList(Keys.IMPACT_ESTIMATE_SET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ImpactEstimateSetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ImpactEstimateSetRecord, ?>>asList(Keys.IMPACT_ESTIMATE_SET__IMPACT_ESTIMATE_SET_IMPACT_ESTIMATE_RECIPE_FKEY, Keys.IMPACT_ESTIMATE_SET__IMPACT_ESTIMATE_SET_RECIPE_TOUCHSTONE_FKEY, Keys.IMPACT_ESTIMATE_SET__IMPACT_ESTIMATE_SET_COVERAGE_TOUCHSTONE_FKEY, Keys.IMPACT_ESTIMATE_SET__IMPACT_ESTIMATE_SET_FOCAL_COVERAGE_SET_FKEY, Keys.IMPACT_ESTIMATE_SET__IMPACT_ESTIMATE_SET_FOCAL_BURDEN_ESTIMATE_SET_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactEstimateSet as(String alias) {
        return new ImpactEstimateSet(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ImpactEstimateSet rename(String name) {
        return new ImpactEstimateSet(name, null);
    }
}
