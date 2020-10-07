/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.Crosstab3Record;


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
public class Crosstab3 extends TableImpl<Crosstab3Record> {

    private static final long serialVersionUID = -12427078;

    /**
     * The reference instance of <code>public.crosstab3</code>
     */
    public static final Crosstab3 CROSSTAB3 = new Crosstab3();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Crosstab3Record> getRecordType() {
        return Crosstab3Record.class;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<Crosstab3Record, Object> CROSSTAB3_ = createField("crosstab3", org.jooq.impl.DefaultDataType.getDefaultDataType("USER-DEFINED"), this, "");

    /**
     * Create a <code>public.crosstab3</code> table reference
     */
    public Crosstab3() {
        this(DSL.name("crosstab3"), null);
    }

    /**
     * Create an aliased <code>public.crosstab3</code> table reference
     */
    public Crosstab3(String alias) {
        this(DSL.name(alias), CROSSTAB3);
    }

    /**
     * Create an aliased <code>public.crosstab3</code> table reference
     */
    public Crosstab3(Name alias) {
        this(alias, CROSSTAB3);
    }

    private Crosstab3(Name alias, Table<Crosstab3Record> aliased) {
        this(alias, aliased, new Field[1]);
    }

    private Crosstab3(Name alias, Table<Crosstab3Record> aliased, Field<?>[] parameters) {
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
    public Crosstab3 as(String alias) {
        return new Crosstab3(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Crosstab3 as(Name alias) {
        return new Crosstab3(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Crosstab3 rename(String name) {
        return new Crosstab3(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Crosstab3 rename(Name name) {
        return new Crosstab3(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Crosstab3 call(String __1) {
        return new Crosstab3(DSL.name(getName()), null, new Field[] { 
              DSL.val(__1, org.jooq.impl.SQLDataType.CLOB)
        });
    }

    /**
     * Call this table-valued function
     */
    public Crosstab3 call(Field<String> __1) {
        return new Crosstab3(DSL.name(getName()), null, new Field[] { 
              __1
        });
    }
}