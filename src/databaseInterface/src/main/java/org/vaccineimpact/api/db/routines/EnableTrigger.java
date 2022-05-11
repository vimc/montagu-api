/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.routines;


import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.vaccineimpact.api.db.Public;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EnableTrigger extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter <code>public.enable_trigger.table_name</code>.
     */
    public static final Parameter<String> TABLE_NAME = Internal.createParameter("table_name", SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.enable_trigger.trigger_name</code>.
     */
    public static final Parameter<String> TRIGGER_NAME = Internal.createParameter("trigger_name", SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public EnableTrigger() {
        super("enable_trigger", Public.PUBLIC);

        addInParameter(TABLE_NAME);
        addInParameter(TRIGGER_NAME);
    }

    /**
     * Set the <code>table_name</code> parameter IN value to the routine
     */
    public void setTableName(String value) {
        setValue(TABLE_NAME, value);
    }

    /**
     * Set the <code>trigger_name</code> parameter IN value to the routine
     */
    public void setTriggerName(String value) {
        setValue(TRIGGER_NAME, value);
    }
}
