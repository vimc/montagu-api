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
import org.vaccineimpact.api.db.tables.records.ScenarioDescriptionRecord;


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
public class ScenarioDescription extends TableImpl<ScenarioDescriptionRecord> {

    private static final long serialVersionUID = 701985134;

    /**
     * The reference instance of <code>public.scenario_description</code>
     */
    public static final ScenarioDescription SCENARIO_DESCRIPTION = new ScenarioDescription();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ScenarioDescriptionRecord> getRecordType() {
        return ScenarioDescriptionRecord.class;
    }

    /**
     * The column <code>public.scenario_description.id</code>.
     */
    public final TableField<ScenarioDescriptionRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.scenario_description.description</code>.
     */
    public final TableField<ScenarioDescriptionRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.scenario_description.disease</code>.
     */
    public final TableField<ScenarioDescriptionRecord, String> DISEASE = createField("disease", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.scenario_description</code> table reference
     */
    public ScenarioDescription() {
        this(DSL.name("scenario_description"), null);
    }

    /**
     * Create an aliased <code>public.scenario_description</code> table reference
     */
    public ScenarioDescription(String alias) {
        this(DSL.name(alias), SCENARIO_DESCRIPTION);
    }

    /**
     * Create an aliased <code>public.scenario_description</code> table reference
     */
    public ScenarioDescription(Name alias) {
        this(alias, SCENARIO_DESCRIPTION);
    }

    private ScenarioDescription(Name alias, Table<ScenarioDescriptionRecord> aliased) {
        this(alias, aliased, null);
    }

    private ScenarioDescription(Name alias, Table<ScenarioDescriptionRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.SCENARIO_DESCRIPTION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ScenarioDescriptionRecord> getPrimaryKey() {
        return Keys.SCENARIO_DESCRIPTION_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ScenarioDescriptionRecord>> getKeys() {
        return Arrays.<UniqueKey<ScenarioDescriptionRecord>>asList(Keys.SCENARIO_DESCRIPTION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ScenarioDescriptionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ScenarioDescriptionRecord, ?>>asList(Keys.SCENARIO_DESCRIPTION__SCENARIO_DESCRIPTION_DISEASE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScenarioDescription as(String alias) {
        return new ScenarioDescription(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScenarioDescription as(Name alias) {
        return new ScenarioDescription(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ScenarioDescription rename(String name) {
        return new ScenarioDescription(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ScenarioDescription rename(Name name) {
        return new ScenarioDescription(name, null);
    }
}
