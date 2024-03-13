package org.task.service;

import org.task.model.InputRecord;
import org.task.model.PairKey;
import org.task.model.PairValue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamLongestPeriodService {
    public static Map.Entry<PairKey, PairValue> getTeamLongestPeriod(List<InputRecord> data){
        Map<PairKey, PairValue> result = new HashMap<>();
        PairKey maxPairKey = null;

        if(data.size() > 1) {
            for (int i = 0; i < data.size(); i++) {
                for (int j = i + 1; j < data.size(); j++) {
                    Long commonDays = commonDaysCnt(data.get(i).getDateFrom(), data.get(i).getDateTo(), data.get(j).getDateFrom(), data.get(j).getDateTo());
                    if (isCommonRecord(data.get(i), data.get(j), commonDays)) {
                        PairKey key = new PairKey(data.get(i).getEmployeeId(), data.get(j).getEmployeeId());
                        PairValue value = null;

                        if (result.containsKey(key)) {
                            value = result.get(key);
                            value.setCommonDays(value.getCommonDays() + commonDays);

                            if (value.getDetails().containsKey(data.get(i).getProjectId())) {
                                value.getDetails().put(data.get(i).getProjectId(), value.getDetails().get(data.get(i).getProjectId()) + commonDays);
                            } else {
                                value.getDetails().put(data.get(i).getProjectId(), commonDays);
                            }
                        } else {
                            Map<Long, Long> details = new HashMap<Long, Long>();
                            details.put(data.get(i).getProjectId(), commonDays);

                            value = new PairValue(commonDays, details);
                            result.put(key, value);
                        }

                        if(maxPairKey == null){
                            maxPairKey = key;
                        }else if (value.getCommonDays() > result.get(maxPairKey).getCommonDays()) {
                            maxPairKey = key;
                        }
                    }
                }
            }
        }

        if(maxPairKey == null){
            return null;
        }else{
            final PairKey searchPairKey = new PairKey(maxPairKey.getEmployeeId1(), maxPairKey.getEmployeeId2());
            var maxEntry = result.entrySet().stream().filter(x -> x.getKey().equals(searchPairKey)).findFirst().orElse(null);

            return maxEntry;
        }
    }

    private static boolean isCommonRecord(InputRecord record1, InputRecord record2, Long commonDays){
        return !record1.getEmployeeId().equals(record2.getEmployeeId())
            && record1.getProjectId().equals(record2.getProjectId())
            && commonDays > 0;
    }

    private static Long commonDaysCnt(LocalDate dateFrom1, LocalDate dateTo1, LocalDate dateFrom2, LocalDate dateTo2){
        LocalDate startDate = null;
        LocalDate endDate = null;

        if((dateFrom1.isBefore(dateFrom2) && dateTo1.isBefore(dateFrom2))
        || (dateFrom1.isAfter(dateTo2) && dateTo1.isAfter(dateTo2))){
            startDate = LocalDate.now().plusDays(1);
            endDate = LocalDate.now();
        }else if(((dateFrom1.isAfter(dateFrom2) || dateFrom1.equals(dateFrom2)) && (dateTo1.isBefore(dateTo2) || dateTo1.equals(dateTo2)))){
            startDate = dateFrom1;
            endDate = dateTo1;
        }else if(((dateFrom2.isAfter(dateFrom1) || dateFrom2.equals(dateFrom1)) && (dateTo2.isBefore(dateTo1) || dateTo2.equals(dateTo1)))){
            startDate = dateFrom2;
            endDate = dateTo2;
        } else if(dateFrom1.isBefore(dateFrom2) && ((dateTo1.isBefore(dateTo2) || dateTo1.equals(dateTo2)) && (dateTo1.isAfter(dateFrom2) || dateTo1.equals(dateFrom2)))){
            startDate = dateFrom2;
            endDate = dateTo1;
        }else if(dateTo1.isAfter(dateTo2) && ((dateFrom1.isAfter(dateFrom2) || dateFrom1.equals(dateFrom2)) && (dateFrom1.isBefore(dateTo2) || dateFrom1.equals(dateTo2)))){
            startDate = dateFrom1;
            endDate = dateTo2;
        }

        if(startDate == null & endDate == null) {
            return 0L;
        }

        Long tmpCnt = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        if(tmpCnt < 0){
            return 0L;
        }else{
            return tmpCnt;
        }
    }

    public static Object[][] prepareTblData(Map.Entry<PairKey, PairValue> data){
        int i = 0;
        Object[][] tblData = new Object[data.getValue().getDetails().size()][];

        for (Map.Entry<Long, Long> entry : data.getValue().getDetails().entrySet()) {
            Object[] ddd = new Object[4];
            ddd[0] = data.getKey().getEmployeeId1();
            ddd[1] = data.getKey().getEmployeeId2();
            ddd[2] = entry.getKey();
            ddd[3] = entry.getValue();
            tblData[i] = ddd;
            i++;
        }
        return tblData;
    }
}
