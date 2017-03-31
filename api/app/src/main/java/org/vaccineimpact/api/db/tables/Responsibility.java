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
import org.vaccineimpact.api.db.tables.records.ResponsibilityRecord;


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
public class Responsibility extends TableImpl<ResponsibilityRecord> {

    private static final long serialVersionUID = -302415918;

    /**
     * The reference instance of <code>public.responsibility</code>
     */
    public static final Responsibility RESPONSIBILITY = new Responsibility();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ResponsibilityRecord> getRecordType() {
        return ResponsibilityRecord.class;
    }

    /**
     * The column <code>public.responsibility.id</code>.
     */
    public final TableField<ResponsibilityRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('responsibility_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.responsibility.responsibility_set</code>.
     */
    public final TableField<ResponsibilityRecord, Integer> RESPONSIBILITY_SET = createField("responsibility_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.responsibility.scenario</code>.
     */
    public final TableField<ResponsibilityRecord, Integer> SCENARIO = createField("scenario", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.responsibility</code> table reference
     */
    public Responsibility() {
        this("responsibility", null);
    }

    /**
     * Create an aliased <code>public.responsibility</code> table reference
     */
    public Responsibility(String alias) {
        this(alias, RESPONSIBILITY);
    }

    private Responsibility(String alias, Table<ResponsibilityRecord> aliased) {
        this(alias, aliased, null);
    }

    private Responsibility(String alias, Table<ResponsibilityRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ResponsibilityRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RESPONSIBILITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ResponsibilityRecord> getPrimaryKey() {
        return Keys.RESPONSIBILITY_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ResponsibilityRecord>> getKeys() {
        return Arrays.<UniqueKey<ResponsibilityRecord>>asList(Keys.RESPONSIBILITY_PKEY, Keys.RESPONSIBILITY_RESPONSIBILITY_SET_SCENARIO_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ResponsibilityRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ResponsibilityRecord, ?>>asList(Keys.RESPONSIBILITY__RESPONSIBILITY_RESPONSIBILITY_SET_FKEY, Keys.RESPONSIBILITY__RESPONSIBILITY_SCENARIO_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Responsibility as(String alias) {
        return new Responsibility(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Responsibility rename(String name) {
        return new Responsibility(name, null);
    }
}
