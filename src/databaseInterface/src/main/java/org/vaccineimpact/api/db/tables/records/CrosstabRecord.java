/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.TableRecordImpl;
import org.vaccineimpact.api.db.tables.Crosstab;


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
public class CrosstabRecord extends TableRecordImpl<CrosstabRecord> implements Record1<Record> {

    private static final long serialVersionUID = -1296695122;

    /**
     * Setter for <code>public.crosstab.crosstab</code>.
     */
    public void setCrosstab(Record value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.crosstab.crosstab</code>.
     */
    public Record getCrosstab() {
        return (Record) get(0);
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row1<Record> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row1<Record> valuesRow() {
        return (Row1) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Record> field1() {
        return Crosstab.CROSSTAB.CROSSTAB_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Record component1() {
        return getCrosstab();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Record value1() {
        return getCrosstab();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CrosstabRecord value1(Record value) {
        setCrosstab(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CrosstabRecord values(Record value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CrosstabRecord
     */
    public CrosstabRecord() {
        super(Crosstab.CROSSTAB);
    }

    /**
     * Create a detached, initialised CrosstabRecord
     */
    public CrosstabRecord(Record crosstab) {
        super(Crosstab.CROSSTAB);

        set(0, crosstab);
    }
}
