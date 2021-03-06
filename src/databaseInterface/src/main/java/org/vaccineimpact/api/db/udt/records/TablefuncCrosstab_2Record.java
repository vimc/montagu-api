/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.udt.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UDTRecordImpl;
import org.vaccineimpact.api.db.udt.TablefuncCrosstab_2;


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
public class TablefuncCrosstab_2Record extends UDTRecordImpl<TablefuncCrosstab_2Record> implements Record3<String, String, String> {

    private static final long serialVersionUID = -2142405601;

    /**
     * Setter for <code>public.tablefunc_crosstab_2.row_name</code>.
     */
    public void setRowName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.tablefunc_crosstab_2.row_name</code>.
     */
    public String getRowName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.tablefunc_crosstab_2.category_1</code>.
     */
    public void setCategory_1(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.tablefunc_crosstab_2.category_1</code>.
     */
    public String getCategory_1() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.tablefunc_crosstab_2.category_2</code>.
     */
    public void setCategory_2(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.tablefunc_crosstab_2.category_2</code>.
     */
    public String getCategory_2() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return TablefuncCrosstab_2.ROW_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TablefuncCrosstab_2.CATEGORY_1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TablefuncCrosstab_2.CATEGORY_2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getRowName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getCategory_1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getCategory_2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getRowName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCategory_1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCategory_2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TablefuncCrosstab_2Record value1(String value) {
        setRowName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TablefuncCrosstab_2Record value2(String value) {
        setCategory_1(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TablefuncCrosstab_2Record value3(String value) {
        setCategory_2(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TablefuncCrosstab_2Record values(String value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TablefuncCrosstab_2Record
     */
    public TablefuncCrosstab_2Record() {
        super(TablefuncCrosstab_2.TABLEFUNC_CROSSTAB_2);
    }

    /**
     * Create a detached, initialised TablefuncCrosstab_2Record
     */
    public TablefuncCrosstab_2Record(String rowName, String category_1, String category_2) {
        super(TablefuncCrosstab_2.TABLEFUNC_CROSSTAB_2);

        set(0, rowName);
        set(1, category_1);
        set(2, category_2);
    }
}
