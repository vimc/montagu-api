/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


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
import org.vaccineimpact.api.db.tables.records.ScenarioCoverageSetRecord;


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
public class ScenarioCoverageSet extends TableImpl<ScenarioCoverageSetRecord> {

    private static final long serialVersionUID = -408247449;

    /**
     * The reference instance of <code>public.scenario_coverage_set</code>
     */
    public static final ScenarioCoverageSet SCENARIO_COVERAGE_SET = new ScenarioCoverageSet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ScenarioCoverageSetRecord> getRecordType() {
        return ScenarioCoverageSetRecord.class;
    }

    /**
     * The column <code>public.scenario_coverage_set.id</code>.
     */
    public final TableField<ScenarioCoverageSetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('scenario_coverage_set_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.scenario_coverage_set.scenario</code>.
     */
    public final TableField<ScenarioCoverageSetRecord, Integer> SCENARIO = createField("scenario", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.scenario_coverage_set.coverage_set</code>.
     */
    public final TableField<ScenarioCoverageSetRecord, Integer> COVERAGE_SET = createField("coverage_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.scenario_coverage_set</code> table reference
     */
    public ScenarioCoverageSet() {
        this("scenario_coverage_set", null);
    }

    /**
     * Create an aliased <code>public.scenario_coverage_set</code> table reference
     */
    public ScenarioCoverageSet(String alias) {
        this(alias, SCENARIO_COVERAGE_SET);
    }

    private ScenarioCoverageSet(String alias, Table<ScenarioCoverageSetRecord> aliased) {
        this(alias, aliased, null);
    }

    private ScenarioCoverageSet(String alias, Table<ScenarioCoverageSetRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ScenarioCoverageSetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SCENARIO_COVERAGE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ScenarioCoverageSetRecord> getPrimaryKey() {
        return Keys.SCENARIO_COVERAGE_SET_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ScenarioCoverageSetRecord>> getKeys() {
        return Arrays.<UniqueKey<ScenarioCoverageSetRecord>>asList(Keys.SCENARIO_COVERAGE_SET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ScenarioCoverageSetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ScenarioCoverageSetRecord, ?>>asList(Keys.SCENARIO_COVERAGE_SET__SCENARIO_COVERAGE_SET_SCENARIO_FKEY, Keys.SCENARIO_COVERAGE_SET__SCENARIO_COVERAGE_SET_COVERAGE_SET_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScenarioCoverageSet as(String alias) {
        return new ScenarioCoverageSet(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ScenarioCoverageSet rename(String name) {
        return new ScenarioCoverageSet(name, null);
    }
}
