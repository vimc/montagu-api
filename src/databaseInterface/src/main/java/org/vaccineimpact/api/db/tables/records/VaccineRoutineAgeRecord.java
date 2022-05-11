/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.VaccineRoutineAge;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VaccineRoutineAgeRecord extends UpdatableRecordImpl<VaccineRoutineAgeRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.vaccine_routine_age.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.vaccine_routine_age.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.vaccine_routine_age.vaccine</code>.
     */
    public void setVaccine(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.vaccine_routine_age.vaccine</code>.
     */
    public String getVaccine() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.vaccine_routine_age.age</code>.
     */
    public void setAge(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.vaccine_routine_age.age</code>.
     */
    public Integer getAge() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return VaccineRoutineAge.VACCINE_ROUTINE_AGE.ID;
    }

    @Override
    public Field<String> field2() {
        return VaccineRoutineAge.VACCINE_ROUTINE_AGE.VACCINE;
    }

    @Override
    public Field<Integer> field3() {
        return VaccineRoutineAge.VACCINE_ROUTINE_AGE.AGE;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getVaccine();
    }

    @Override
    public Integer component3() {
        return getAge();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getVaccine();
    }

    @Override
    public Integer value3() {
        return getAge();
    }

    @Override
    public VaccineRoutineAgeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public VaccineRoutineAgeRecord value2(String value) {
        setVaccine(value);
        return this;
    }

    @Override
    public VaccineRoutineAgeRecord value3(Integer value) {
        setAge(value);
        return this;
    }

    @Override
    public VaccineRoutineAgeRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VaccineRoutineAgeRecord
     */
    public VaccineRoutineAgeRecord() {
        super(VaccineRoutineAge.VACCINE_ROUTINE_AGE);
    }

    /**
     * Create a detached, initialised VaccineRoutineAgeRecord
     */
    public VaccineRoutineAgeRecord(Integer id, String vaccine, Integer age) {
        super(VaccineRoutineAge.VACCINE_ROUTINE_AGE);

        setId(id);
        setVaccine(vaccine);
        setAge(age);
    }
}
