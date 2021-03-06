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
import org.vaccineimpact.api.db.tables.records.Crosstab4Record;


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
public class Crosstab4 extends TableImpl<Crosstab4Record> {

    private static final long serialVersionUID = 1935021951;

    /**
     * The reference instance of <code>public.crosstab4</code>
     */
    public static final Crosstab4 CROSSTAB4 = new Crosstab4();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Crosstab4Record> getRecordType() {
        return Crosstab4Record.class;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.
     */
    @java.lang.Deprecated
    public final TableField<Crosstab4Record, Object> CROSSTAB4_ = createField("crosstab4", org.jooq.impl.DefaultDataType.getDefaultDataType("USER-DEFINED"), this, "");

    /**
     * Create a <code>public.crosstab4</code> table reference
     */
    public Crosstab4() {
        this(DSL.name("crosstab4"), null);
    }

    /**
     * Create an aliased <code>public.crosstab4</code> table reference
     */
    public Crosstab4(String alias) {
        this(DSL.name(alias), CROSSTAB4);
    }

    /**
     * Create an aliased <code>public.crosstab4</code> table reference
     */
    public Crosstab4(Name alias) {
        this(alias, CROSSTAB4);
    }

    private Crosstab4(Name alias, Table<Crosstab4Record> aliased) {
        this(alias, aliased, new Field[1]);
    }

    private Crosstab4(Name alias, Table<Crosstab4Record> aliased, Field<?>[] parameters) {
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
    public Crosstab4 as(String alias) {
        return new Crosstab4(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Crosstab4 as(Name alias) {
        return new Crosstab4(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Crosstab4 rename(String name) {
        return new Crosstab4(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public Crosstab4 rename(Name name) {
        return new Crosstab4(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public Crosstab4 call(String __1) {
        return new Crosstab4(DSL.name(getName()), null, new Field[] { 
              DSL.val(__1, org.jooq.impl.SQLDataType.CLOB)
        });
    }

    /**
     * Call this table-valued function
     */
    public Crosstab4 call(Field<String> __1) {
        return new Crosstab4(DSL.name(getName()), null, new Field[] { 
              __1
        });
    }
}
