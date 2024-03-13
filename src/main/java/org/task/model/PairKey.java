package org.task.model;

import java.util.Objects;

public class PairKey {
    private Long employeeId1 = 0L;
    private Long employeeId2 = 0L;

    public PairKey(){}
    public PairKey(Long employeeId1, Long employeeId2){
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof PairKey)) {
            return false;
        }

        PairKey pk = (PairKey) o;

        return (this.employeeId1.equals(pk.getEmployeeId1()) && this.employeeId2.equals(pk.getEmployeeId2()))
            || (this.employeeId1.equals(pk.getEmployeeId2()) && this.employeeId2.equals(pk.getEmployeeId1()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId1 + employeeId2);
    }

    public long getEmployeeId1() {
        return employeeId1;
    }

    public void setEmployeeId1(long employeeId1) {
        this.employeeId1 = employeeId1;
    }

    public long getEmployeeId2() {
        return employeeId2;
    }

    public void setEmployeeId2(long employeeId2) {
        this.employeeId2 = employeeId2;
    }
}
