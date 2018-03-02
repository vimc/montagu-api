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
import org.vaccineimpact.api.db.tables.records.BurdenOutcomeRecord;


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
public class BurdenOutcome extends TableImpl<BurdenOutcomeRecord> {

    private static final long serialVersionUID = -145650783;

    /**
     * The reference instance of <code>public.burden_outcome</code>
     */
    public static final BurdenOutcome BURDEN_OUTCOME = new BurdenOutcome();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BurdenOutcomeRecord> getRecordType() {
        return BurdenOutcomeRecord.class;
    }

    /**
     * The column <code>public.burden_outcome.id</code>.
     */
    public final TableField<BurdenOutcomeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('burden_outcome_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.burden_outcome.code</code>.
     */
    public final TableField<BurdenOutcomeRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_outcome.name</code>.
     */
    public final TableField<BurdenOutcomeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.burden_outcome.proportion</code>.
     */
    public final TableField<BurdenOutcomeRecord, Boolean> PROPORTION = createField("proportion", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * Create a <code>public.burden_outcome</code> table reference
     */
    public BurdenOutcome() {
        this(DSL.name("burden_outcome"), null);
    }

    /**
     * Create an aliased <code>public.burden_outcome</code> table reference
     */
    public BurdenOutcome(String alias) {
        this(DSL.name(alias), BURDEN_OUTCOME);
    }

    /**
     * Create an aliased <code>public.burden_outcome</code> table reference
     */
    public BurdenOutcome(Name alias) {
        this(alias, BURDEN_OUTCOME);
    }

    private BurdenOutcome(Name alias, Table<BurdenOutcomeRecord> aliased) {
        this(alias, aliased, null);
    }

    private BurdenOutcome(Name alias, Table<BurdenOutcomeRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.BURDEN_OUTCOME_CODE_KEY, Indexes.BURDEN_OUTCOME_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<BurdenOutcomeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_BURDEN_OUTCOME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BurdenOutcomeRecord> getPrimaryKey() {
        return Keys.BURDEN_OUTCOME_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BurdenOutcomeRecord>> getKeys() {
        return Arrays.<UniqueKey<BurdenOutcomeRecord>>asList(Keys.BURDEN_OUTCOME_PKEY, Keys.BURDEN_OUTCOME_CODE_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenOutcome as(String alias) {
        return new BurdenOutcome(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenOutcome as(Name alias) {
        return new BurdenOutcome(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenOutcome rename(String name) {
        return new BurdenOutcome(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BurdenOutcome rename(Name name) {
        return new BurdenOutcome(name, null);
    }
}
