/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ConnectbyRecord;


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
public class Connectby extends TableImpl<ConnectbyRecord> {

    private static final long serialVersionUID = 1419020015;

    /**
     * The reference instance of <code>public.connectby</code>
     */
    public static final Connectby CONNECTBY = new Connectby();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConnectbyRecord> getRecordType() {
        return ConnectbyRecord.class;
    }

    /**
     * The column <code>public.connectby.connectby</code>.
     */
    public final TableField<ConnectbyRecord, Record> CONNECTBY_ = createField("connectby", org.jooq.impl.SQLDataType.RECORD, this, "");

    /**
     * Create a <code>public.connectby</code> table reference
     */
    public Connectby() {
        this(DSL.name("connectby"), null);
    }

    /**
     * Create an aliased <code>public.connectby</code> table reference
     */
    public Connectby(String alias) {
        this(DSL.name(alias), CONNECTBY);
    }

    /**
     * Create an aliased <code>public.connectby</code> table reference
     */
    public Connectby(Name alias) {
        this(alias, CONNECTBY);
    }

    private Connectby(Name alias, Table<ConnectbyRecord> aliased) {
        this(alias, aliased, new Field[6]);
    }

    private Connectby(Name alias, Table<ConnectbyRecord> aliased, Field<?>[] parameters) {
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
    public Connectby as(String alias) {
        return new Connectby(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connectby as(Name alias) {
        return new Connectby(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Connectby rename(String name) {
        return new Connectby(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Connectby rename(Name name) {
        return new Connectby(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Connectby call(String __1, String __2, String __3, String __4, String __5, Integer __6) {
        return new Connectby(DSL.name(getName()), null, new Field[] { 
              DSL.val(__1, org.jooq.impl.SQLDataType.CLOB)
            , DSL.val(__2, org.jooq.impl.SQLDataType.CLOB)
            , DSL.val(__3, org.jooq.impl.SQLDataType.CLOB)
            , DSL.val(__4, org.jooq.impl.SQLDataType.CLOB)
            , DSL.val(__5, org.jooq.impl.SQLDataType.CLOB)
            , DSL.val(__6, org.jooq.impl.SQLDataType.INTEGER)
        });
    }

    /**
     * Call this table-valued function
     */
    public Connectby call(Field<String> __1, Field<String> __2, Field<String> __3, Field<String> __4, Field<String> __5, Field<Integer> __6) {
        return new Connectby(DSL.name(getName()), null, new Field[] { 
              __1
            , __2
            , __3
            , __4
            , __5
            , __6
        });
    }
}
