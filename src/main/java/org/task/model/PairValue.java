package org.task.model;

import java.util.HashMap;
import java.util.Map;

public class PairValue {
    private Long commonDays = 0L;
    private Map<Long, Long> details = new HashMap<>();

    public PairValue(Long commonDays, Map<Long, Long> details)
    {
        this.commonDays = commonDays;
        this.details = details;
    }
    public Long getCommonDays() {
        return commonDays;
    }

    public void setCommonDays(Long commonDays) {
        this.commonDays = commonDays;
    }

    public Map<Long, Long> getDetails() {
        return details;
    }

    public void setDetails(Map<Long, Long> details) {
        this.details = details;
    }
}
