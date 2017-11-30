/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.ApiAccessLog;


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
public class ApiAccessLogRecord extends UpdatableRecordImpl<ApiAccessLogRecord> implements Record6<Integer, String, Timestamp, String, Integer, String> {

    private static final long serialVersionUID = -519299532;

    /**
     * Setter for <code>public.api_access_log.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.api_access_log.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.api_access_log.who</code>.
     */
    public void setWho(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.api_access_log.who</code>.
     */
    public String getWho() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.api_access_log.timestamp</code>.
     */
    public void setTimestamp(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.api_access_log.timestamp</code>.
     */
    public Timestamp getTimestamp() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>public.api_access_log.what</code>.
     */
    public void setWhat(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.api_access_log.what</code>.
     */
    public String getWhat() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.api_access_log.result</code>. The HTTP status code returned by the API
     */
    public void setResult(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.api_access_log.result</code>. The HTTP status code returned by the API
     */
    public Integer getResult() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.api_access_log.ip_address</code>.
     */
    public void setIpAddress(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.api_access_log.ip_address</code>.
     */
    public String getIpAddress() {
        return (String) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, Timestamp, String, Integer, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, Timestamp, String, Integer, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ApiAccessLog.API_ACCESS_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ApiAccessLog.API_ACCESS_LOG.WHO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return ApiAccessLog.API_ACCESS_LOG.TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ApiAccessLog.API_ACCESS_LOG.WHAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return ApiAccessLog.API_ACCESS_LOG.RESULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ApiAccessLog.API_ACCESS_LOG.IP_ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getWho();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getWhat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getIpAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getWho();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getWhat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getIpAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLogRecord value2(String value) {
        setWho(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLogRecord value3(Timestamp value) {
        setTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLogRecord value4(String value) {
        setWhat(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLogRecord value5(Integer value) {
        setResult(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLogRecord value6(String value) {
        setIpAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLogRecord values(Integer value1, String value2, Timestamp value3, String value4, Integer value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ApiAccessLogRecord
     */
    public ApiAccessLogRecord() {
        super(ApiAccessLog.API_ACCESS_LOG);
    }

    /**
     * Create a detached, initialised ApiAccessLogRecord
     */
    public ApiAccessLogRecord(Integer id, String who, Timestamp timestamp, String what, Integer result, String ipAddress) {
        super(ApiAccessLog.API_ACCESS_LOG);

        set(0, id);
        set(1, who);
        set(2, timestamp);
        set(3, what);
        set(4, result);
        set(5, ipAddress);
    }
}
