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
import org.vaccineimpact.api.db.tables.records.VaccineRecord;


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
public class Vaccine extends TableImpl<VaccineRecord> {

    private static final long serialVersionUID = -1878585231;

    /**
     * The reference instance of <code>public.vaccine</code>
     */
    public static final Vaccine VACCINE = new Vaccine();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VaccineRecord> getRecordType() {
        return VaccineRecord.class;
    }

    /**
     * The column <code>public.vaccine.id</code>.
     */
    public final TableField<VaccineRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false).defaultValue(org.jooq.impl.DSL.field("'NULL'::text", org.jooq.impl.SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.vaccine.name</code>.
     */
    public final TableField<VaccineRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.vaccine</code> table reference
     */
    public Vaccine() {
        this(DSL.name("vaccine"), null);
    }

    /**
     * Create an aliased <code>public.vaccine</code> table reference
     */
    public Vaccine(String alias) {
        this(DSL.name(alias), VACCINE);
    }

    /**
     * Create an aliased <code>public.vaccine</code> table reference
     */
    public Vaccine(Name alias) {
        this(alias, VACCINE);
    }

    private Vaccine(Name alias, Table<VaccineRecord> aliased) {
        this(alias, aliased, null);
    }

    private Vaccine(Name alias, Table<VaccineRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.VACCINE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VaccineRecord> getPrimaryKey() {
        return Keys.VACCINE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VaccineRecord>> getKeys() {
        return Arrays.<UniqueKey<VaccineRecord>>asList(Keys.VACCINE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vaccine as(String alias) {
        return new Vaccine(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vaccine as(Name alias) {
        return new Vaccine(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Vaccine rename(String name) {
        return new Vaccine(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Vaccine rename(Name name) {
        return new Vaccine(name, null);
    }
}
