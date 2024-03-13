package org.task.model;

import java.time.LocalDate;

public class InputRecord {
    private Long projectId;
    private Long employeeId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public InputRecord(){}
    public InputRecord(Long employeeId, Long projectId, LocalDate dateFrom, LocalDate dateTo){
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getProjectId(){
        return this.projectId;
    }

    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }

    public Long getEmployeeId(){
        return this.employeeId;
    }

    public void setEmployeeId(Long employeeId){
        this.employeeId = employeeId;
    }

    public LocalDate getDateFrom(){
        return this.dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom){
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo(){
        return this.dateTo;
    }

    public void setDateTo(LocalDate dateTo){
        this.dateTo = dateTo;
    }
}
