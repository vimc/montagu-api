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
import org.vaccineimpact.api.db.tables.records.FrancophoneStatusRecord;


/**
 * Status within the Organisation internationale de la Francophonie
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FrancophoneStatus extends TableImpl<FrancophoneStatusRecord> {

    private static final long serialVersionUID = 399734472;

    /**
     * The reference instance of <code>public.francophone_status</code>
     */
    public static final FrancophoneStatus FRANCOPHONE_STATUS = new FrancophoneStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FrancophoneStatusRecord> getRecordType() {
        return FrancophoneStatusRecord.class;
    }

    /**
     * The column <code>public.francophone_status.id</code>.
     */
    public final TableField<FrancophoneStatusRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.francophone_status</code> table reference
     */
    public FrancophoneStatus() {
        this(DSL.name("francophone_status"), null);
    }

    /**
     * Create an aliased <code>public.francophone_status</code> table reference
     */
    public FrancophoneStatus(String alias) {
        this(DSL.name(alias), FRANCOPHONE_STATUS);
    }

    /**
     * Create an aliased <code>public.francophone_status</code> table reference
     */
    public FrancophoneStatus(Name alias) {
        this(alias, FRANCOPHONE_STATUS);
    }

    private FrancophoneStatus(Name alias, Table<FrancophoneStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private FrancophoneStatus(Name alias, Table<FrancophoneStatusRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Status within the Organisation internationale de la Francophonie");
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
        return Arrays.<Index>asList(Indexes.FRANCOPHONE_STATUS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<FrancophoneStatusRecord> getPrimaryKey() {
        return Keys.FRANCOPHONE_STATUS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<FrancophoneStatusRecord>> getKeys() {
        return Arrays.<UniqueKey<FrancophoneStatusRecord>>asList(Keys.FRANCOPHONE_STATUS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FrancophoneStatus as(String alias) {
        return new FrancophoneStatus(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FrancophoneStatus as(Name alias) {
        return new FrancophoneStatus(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FrancophoneStatus rename(String name) {
        return new FrancophoneStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FrancophoneStatus rename(Name name) {
        return new FrancophoneStatus(name, null);
    }
}