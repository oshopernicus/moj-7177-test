package uk.gov.hmcts.reform.dev.models;

public enum TaskStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    OVERDUE("Overdue"),
    COMPLETED("Completed");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String status) {
        for (TaskStatus ts : values()) {
            if (ts.getValue().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

    public static TaskStatus fromDisplayValue(String value) {
        for (TaskStatus ts : values()) {
            if (ts.getValue().equalsIgnoreCase(value) || ts.name().equalsIgnoreCase(value.replace(" ", "_"))) {
                return ts;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
