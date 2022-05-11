/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.OnetimeTokenRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OnetimeToken extends TableImpl<OnetimeTokenRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.onetime_token</code>
     */
    public static final OnetimeToken ONETIME_TOKEN = new OnetimeToken();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OnetimeTokenRecord> getRecordType() {
        return OnetimeTokenRecord.class;
    }

    /**
     * The column <code>public.onetime_token.token</code>.
     */
    public final TableField<OnetimeTokenRecord, String> TOKEN = createField(DSL.name("token"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.onetime_token.id</code>.
     */
    public final TableField<OnetimeTokenRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    private OnetimeToken(Name alias, Table<OnetimeTokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private OnetimeToken(Name alias, Table<OnetimeTokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.onetime_token</code> table reference
     */
    public OnetimeToken(String alias) {
        this(DSL.name(alias), ONETIME_TOKEN);
    }

    /**
     * Create an aliased <code>public.onetime_token</code> table reference
     */
    public OnetimeToken(Name alias) {
        this(alias, ONETIME_TOKEN);
    }

    /**
     * Create a <code>public.onetime_token</code> table reference
     */
    public OnetimeToken() {
        this(DSL.name("onetime_token"), null);
    }

    public <O extends Record> OnetimeToken(Table<O> child, ForeignKey<O, OnetimeTokenRecord> key) {
        super(child, key, ONETIME_TOKEN);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<OnetimeTokenRecord, Integer> getIdentity() {
        return (Identity<OnetimeTokenRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<OnetimeTokenRecord> getPrimaryKey() {
        return Keys.ONETIME_TOKEN_PKEY;
    }

    @Override
    public OnetimeToken as(String alias) {
        return new OnetimeToken(DSL.name(alias), this);
    }

    @Override
    public OnetimeToken as(Name alias) {
        return new OnetimeToken(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OnetimeToken rename(String name) {
        return new OnetimeToken(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OnetimeToken rename(Name name) {
        return new OnetimeToken(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
