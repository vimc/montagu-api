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
import org.vaccineimpact.api.db.tables.records.NormalRandRecord;


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
public class NormalRand extends TableImpl<NormalRandRecord> {

    private static final long serialVersionUID = -1893103837;

    /**
     * The reference instance of <code>public.normal_rand</code>
     */
    public static final NormalRand NORMAL_RAND = new NormalRand();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NormalRandRecord> getRecordType() {
        return NormalRandRecord.class;
    }

    /**
     * The column <code>public.normal_rand.normal_rand</code>.
     */
    public final TableField<NormalRandRecord, Double> NORMAL_RAND_ = createField("normal_rand", org.jooq.impl.SQLDataType.DOUBLE, this, "");

    /**
     * Create a <code>public.normal_rand</code> table reference
     */
    public NormalRand() {
        this(DSL.name("normal_rand"), null);
    }

    /**
     * Create an aliased <code>public.normal_rand</code> table reference
     */
    public NormalRand(String alias) {
        this(DSL.name(alias), NORMAL_RAND);
    }

    /**
     * Create an aliased <code>public.normal_rand</code> table reference
     */
    public NormalRand(Name alias) {
        this(alias, NORMAL_RAND);
    }

    private NormalRand(Name alias, Table<NormalRandRecord> aliased) {
        this(alias, aliased, new Field[3]);
    }

    private NormalRand(Name alias, Table<NormalRandRecord> aliased, Field<?>[] parameters) {
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
    public NormalRand as(String alias) {
        return new NormalRand(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NormalRand as(Name alias) {
        return new NormalRand(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public NormalRand rename(String name) {
        return new NormalRand(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public NormalRand rename(Name name) {
        return new NormalRand(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public NormalRand call(Integer __1, Double __2, Double __3) {
        return new NormalRand(DSL.name(getName()), null, new Field[] { 
              DSL.val(__1, org.jooq.impl.SQLDataType.INTEGER)
            , DSL.val(__2, org.jooq.impl.SQLDataType.DOUBLE)
            , DSL.val(__3, org.jooq.impl.SQLDataType.DOUBLE)
        });
    }

    /**
     * Call this table-valued function
     */
    public NormalRand call(Field<Integer> __1, Field<Double> __2, Field<Double> __3) {
        return new NormalRand(DSL.name(getName()), null, new Field[] { 
              __1
            , __2
            , __3
        });
    }
}
