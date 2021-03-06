/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
import org.vaccineimpact.api.db.tables.records.BurdenEstimateExpectationRecord;


/**
 * This table, in combination with burden_estimate_country_expectation and 
 * burden_estimate_outcome_expectation, describes in detail the burden estimates 
 * we expect to be uploaded for a particular responsibility. If you imagine 
 * plotting expected year and age combinations on x and y axes, then the year_* 
 * and age_* columns provide a rectangular area. Within those bounds, the 
 * cohort columns optionally give us the ability to describe a triangular 
 * area. If a cohort_min_inclusive is defined then only people born in that 
 * year and afterwards are included. So if this is set to  2000 then the only 
 * ages expected in 2000 are 0. Whereas by 2010, ages 0 - 10 are expected. 
 *  Similarly, if cohort_max_inclusive is defined then only people born in 
 * that year or before are included.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BurdenEstimateExpectation extends TableImpl<BurdenEstimateExpectationRecord> {

    private static final long serialVersionUID = -1304917704;

    /**
     * The reference instance of <code>public.burden_estimate_expectation</code>
     */
    public static final BurdenEstimateExpectation BURDEN_ESTIMATE_EXPECTATION = new BurdenEstimateExpectation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BurdenEstimateExpectationRecord> getRecordType() {
        return BurdenEstimateExpectationRecord.class;
    }

    /**
     * The column <code>public.burden_estimate_expectation.id</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('burden_estimate_expectation_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.burden_estimate_expectation.year_min_inclusive</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, Short> YEAR_MIN_INCLUSIVE = createField("year_min_inclusive", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_expectation.year_max_inclusive</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, Short> YEAR_MAX_INCLUSIVE = createField("year_max_inclusive", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_expectation.age_min_inclusive</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, Short> AGE_MIN_INCLUSIVE = createField("age_min_inclusive", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_expectation.age_max_inclusive</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, Short> AGE_MAX_INCLUSIVE = createField("age_max_inclusive", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_expectation.cohort_min_inclusive</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, Short> COHORT_MIN_INCLUSIVE = createField("cohort_min_inclusive", org.jooq.impl.SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.burden_estimate_expectation.cohort_max_inclusive</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, Short> COHORT_MAX_INCLUSIVE = createField("cohort_max_inclusive", org.jooq.impl.SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.burden_estimate_expectation.description</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_estimate_expectation.version</code>.
     */
    public final TableField<BurdenEstimateExpectationRecord, String> VERSION = createField("version", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.burden_estimate_expectation</code> table reference
     */
    public BurdenEstimateExpectation() {
        this(DSL.name("burden_estimate_expectation"), null);
    }

    /**
     * Create an aliased <code>public.burden_estimate_expectation</code> table reference
     */
    public BurdenEstimateExpectation(String alias) {
        this(DSL.name(alias), BURDEN_ESTIMATE_EXPECTATION);
    }

    /**
     * Create an aliased <code>public.burden_estimate_expectation</code> table reference
     */
    public BurdenEstimateExpectation(Name alias) {
        this(alias, BURDEN_ESTIMATE_EXPECTATION);
    }

    private BurdenEstimateExpectation(Name alias, Table<BurdenEstimateExpectationRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenEstimateExpectation(Name alias, Table<BurdenEstimateExpectationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "This table, in combination with burden_estimate_country_expectation and burden_estimate_outcome_expectation, describes in detail the burden estimates we expect to be uploaded for a particular responsibility. If you imagine plotting expected year and age combinations on x and y axes, then the year_* and age_* columns provide a rectangular area. Within those bounds, the cohort columns optionally give us the ability to describe a triangular area. If a cohort_min_inclusive is defined then only people born in that year and afterwards are included. So if this is set to  2000 then the only ages expected in 2000 are 0. Whereas by 2010, ages 0 - 10 are expected.  Similarly, if cohort_max_inclusive is defined then only people born in that year or before are included.");
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
        return Arrays.<Index>asList(Indexes.BURDEN_ESTIMATE_EXPECTATION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<BurdenEstimateExpectationRecord, Integer> getIdentity() {
        return Keys.IDENTITY_BURDEN_ESTIMATE_EXPECTATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BurdenEstimateExpectationRecord> getPrimaryKey() {
        return Keys.BURDEN_ESTIMATE_EXPECTATION_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BurdenEstimateExpectationRecord>> getKeys() {
        return Arrays.<UniqueKey<BurdenEstimateExpectationRecord>>asList(Keys.BURDEN_ESTIMATE_EXPECTATION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectation as(String alias) {
        return new BurdenEstimateExpectation(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectation as(Name alias) {
        return new BurdenEstimateExpectation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateExpectation rename(String name) {
        return new BurdenEstimateExpectation(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenEstimateExpectation rename(Name name) {
        return new BurdenEstimateExpectation(name, null);
    }
}
