package unioeste.br.bocajuniorsapi.domain;

public enum SubmissionStatus {
    AC("Accepted"),
    WA("Wrong Answer"),
    COMPILATION_ERROR("Compilation Error"),
    TLE("Time Limit Exceeded"),
    MLE("Memory Limit Exceeded"),
    SLE("Size Limit Exceeded"),
    RE("Runtime Error"),
    SG("Program died on a signal"),
    XX("Internal Error"),
    WTF("Not Implemented"),
    IQ("In queue");

    private final String description;

    SubmissionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static SubmissionStatus fromDescription(String description) {
        for (SubmissionStatus status : SubmissionStatus.values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant with description: " + description);
    }
}
