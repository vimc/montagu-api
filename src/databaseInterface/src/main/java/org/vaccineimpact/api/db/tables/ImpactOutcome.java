/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


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
import org.vaccineimpact.api.db.Indexes;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ImpactOutcomeRecord;


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
public class ImpactOutcome extends TableImpl<ImpactOutcomeRecord> {

    private static final long serialVersionUID = -1142745132;

    /**
     * The reference instance of <code>public.impact_outcome</code>
     */
    public static final ImpactOutcome IMPACT_OUTCOME = new ImpactOutcome();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ImpactOutcomeRecord> getRecordType() {
        return ImpactOutcomeRecord.class;
    }

    /**
     * The column <code>public.impact_outcome.id</code>.
     */
    public final TableField<ImpactOutcomeRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.impact_outcome.name</code>.
     */
    public final TableField<ImpactOutcomeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.impact_outcome</code> table reference
     */
    public ImpactOutcome() {
        this(DSL.name("impact_outcome"), null);
    }

    /**
     * Create an aliased <code>public.impact_outcome</code> table reference
     */
    public ImpactOutcome(String alias) {
        this(DSL.name(alias), IMPACT_OUTCOME);
    }

    /**
     * Create an aliased <code>public.impact_outcome</code> table reference
     */
    public ImpactOutcome(Name alias) {
        this(alias, IMPACT_OUTCOME);
    }

    private ImpactOutcome(Name alias, Table<ImpactOutcomeRecord> aliased) {
        this(alias, aliased, null);
    }

    private ImpactOutcome(Name alias, Table<ImpactOutcomeRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.IMPACT_OUTCOME_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ImpactOutcomeRecord> getPrimaryKey() {
        return Keys.IMPACT_OUTCOME_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ImpactOutcomeRecord>> getKeys() {
        return Arrays.<UniqueKey<ImpactOutcomeRecord>>asList(Keys.IMPACT_OUTCOME_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactOutcome as(String alias) {
        return new ImpactOutcome(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImpactOutcome as(Name alias) {
        return new ImpactOutcome(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ImpactOutcome rename(String name) {
        return new ImpactOutcome(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ImpactOutcome rename(Name name) {
        return new ImpactOutcome(name, null);
    }
}
