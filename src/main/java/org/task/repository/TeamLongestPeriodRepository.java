package org.task.repository;

import org.task.model.InputRecord;
import org.task.utils.DateUtils;
import org.task.utils.NumberUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeamLongestPeriodRepository {
    private List<InputRecord> data = new ArrayList<>();

    public List<InputRecord> getData(){
        return data;
    }

    public void clear(){
        data.clear();
    }

    public void loadData(String filePath) throws Exception {
        data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                add(values);
            }
        }catch(Exception ex){
            throw ex;
        }
    }

    public void add(String[] params) throws Exception {
        long projectId = 0;
        long employeeId = 0;
        LocalDate dateFrom = null;
        LocalDate dateTo = null;

        if(params.length < 4){
            throw new Exception("[" + String.join(",", params) + "]: Invalid data format");
        }

        try {
            employeeId = NumberUtils.parseId(params[0]);
        }catch(Exception ex){
            throw new Exception("[" + String.join(",", params) + "]: Invalid 'EmployeeId'");
        }

        try {
            projectId = NumberUtils.parseId(params[1]);
        }catch(Exception ex){
            throw new Exception("[" + String.join(",", params) + "]: Invalid 'ProjectId'");
        }

        try {
            dateFrom = DateUtils.parseDate(params[2], false);
        }catch(Exception ex){
            throw new Exception("[" + String.join(",", params) + "]:  Invalid 'DateFrom'");
        }

        try {
            dateTo = DateUtils.parseDate(params[3], true);
        }catch(Exception ex){
            throw new Exception("[" + String.join(",", params) + "]:  Invalid 'DateFrom'");
        }

        InputRecord record = new InputRecord(employeeId, projectId, dateFrom, dateTo);

        data.add(record);
    }
}
