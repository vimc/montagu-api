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
import org.vaccineimpact.api.db.tables.records.PermissionRecord;


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
public class Permission extends TableImpl<PermissionRecord> {

    private static final long serialVersionUID = -1529354082;

    /**
     * The reference instance of <code>public.permission</code>
     */
    public static final Permission PERMISSION = new Permission();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PermissionRecord> getRecordType() {
        return PermissionRecord.class;
    }

    /**
     * The column <code>public.permission.name</code>.
     */
    public final TableField<PermissionRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.permission</code> table reference
     */
    public Permission() {
        this(DSL.name("permission"), null);
    }

    /**
     * Create an aliased <code>public.permission</code> table reference
     */
    public Permission(String alias) {
        this(DSL.name(alias), PERMISSION);
    }

    /**
     * Create an aliased <code>public.permission</code> table reference
     */
    public Permission(Name alias) {
        this(alias, PERMISSION);
    }

    private Permission(Name alias, Table<PermissionRecord> aliased) {
        this(alias, aliased, null);
    }

    private Permission(Name alias, Table<PermissionRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.PERMISSION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PermissionRecord> getPrimaryKey() {
        return Keys.PERMISSION_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PermissionRecord>> getKeys() {
        return Arrays.<UniqueKey<PermissionRecord>>asList(Keys.PERMISSION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Permission as(String alias) {
        return new Permission(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Permission as(Name alias) {
        return new Permission(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Permission rename(String name) {
        return new Permission(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Permission rename(Name name) {
        return new Permission(name, null);
    }
}
