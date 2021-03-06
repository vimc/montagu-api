/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.OnetimeToken;


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
public class OnetimeTokenRecord extends UpdatableRecordImpl<OnetimeTokenRecord> implements Record2<String, Integer> {

    private static final long serialVersionUID = -493294120;

    /**
     * Setter for <code>public.onetime_token.token</code>.
     */
    public void setToken(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.onetime_token.token</code>.
     */
    public String getToken() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.onetime_token.id</code>.
     */
    public void setId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.onetime_token.id</code>.
     */
    public Integer getId() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return OnetimeToken.ONETIME_TOKEN.TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return OnetimeToken.ONETIME_TOKEN.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnetimeTokenRecord value1(String value) {
        setToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnetimeTokenRecord value2(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnetimeTokenRecord values(String value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OnetimeTokenRecord
     */
    public OnetimeTokenRecord() {
        super(OnetimeToken.ONETIME_TOKEN);
    }

    /**
     * Create a detached, initialised OnetimeTokenRecord
     */
    public OnetimeTokenRecord(String token, Integer id) {
        super(OnetimeToken.ONETIME_TOKEN);

        set(0, token);
        set(1, id);
    }
}
