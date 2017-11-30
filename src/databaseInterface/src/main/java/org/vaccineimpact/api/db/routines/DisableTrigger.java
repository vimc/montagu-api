/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.routines;


import javax.annotation.Generated;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.vaccineimpact.api.db.Public;


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
public class DisableTrigger extends AbstractRoutine<java.lang.Void> {

    private static final long serialVersionUID = -1475849771;

    /**
     * The parameter <code>public.disable_trigger.table_name</code>.
     */
    public static final Parameter<String> TABLE_NAME = createParameter("table_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>public.disable_trigger.trigger_name</code>.
     */
    public static final Parameter<String> TRIGGER_NAME = createParameter("trigger_name", org.jooq.impl.SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public DisableTrigger() {
        super("disable_trigger", Public.PUBLIC);

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
