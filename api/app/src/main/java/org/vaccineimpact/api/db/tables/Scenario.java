/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ScenarioRecord;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Scenario extends TableImpl<ScenarioRecord> {

    private static final long serialVersionUID = 391950543;

    /**
     * The reference instance of <code>public.scenario</code>
     */
    public static final Scenario SCENARIO = new Scenario();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ScenarioRecord> getRecordType() {
        return ScenarioRecord.class;
    }

    /**
     * The column <code>public.scenario.id</code>.
     */
    public final TableField<ScenarioRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('scenario_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.scenario.touchstone</code>.
     */
    public final TableField<ScenarioRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false).defaultValue(org.jooq.impl.DSL.field("'NULL'::text", org.jooq.impl.SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.scenario.scenario_description</code>.
     */
    public final TableField<ScenarioRecord, String> SCENARIO_DESCRIPTION = createField("scenario_description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.scenario</code> table reference
     */
    public Scenario() {
        this("scenario", null);
    }

    /**
     * Create an aliased <code>public.scenario</code> table reference
     */
    public Scenario(String alias) {
        this(alias, SCENARIO);
    }

    private Scenario(String alias, Table<ScenarioRecord> aliased) {
        this(alias, aliased, null);
    }

    private Scenario(String alias, Table<ScenarioRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ScenarioRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SCENARIO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ScenarioRecord> getPrimaryKey() {
        return Keys.SCENARIO_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ScenarioRecord>> getKeys() {
        return Arrays.<UniqueKey<ScenarioRecord>>asList(Keys.SCENARIO_PKEY, Keys.SCENARIO_TOUCHSTONE_SCENARIO_DESCRIPTION_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ScenarioRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ScenarioRecord, ?>>asList(Keys.SCENARIO__SCENARIO_TOUCHSTONE_FKEY, Keys.SCENARIO__SCENARIO_SCENARIO_DESCRIPTION_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scenario as(String alias) {
        return new Scenario(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Scenario rename(String name) {
        return new Scenario(name, null);
    }
}
