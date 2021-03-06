package de.hsh.grappa.config;

public class ServiceConfig {
    private int default_estimated_grading_seconds = 20;
    private int prev_grading_seconds_max_list_size = 10;
    private String logging_level;
    private int synchronous_submission_timeout_seconds = 600;
    private String default_grading_environment_setup_class_path;
    private String default_grading_environment_setup_class_name;

    public int getPrev_grading_seconds_max_list_size() {
        return prev_grading_seconds_max_list_size;
    }

    public void setPrev_grading_seconds_max_list_size(int prev_grading_seconds_max_list_size) {
        this.prev_grading_seconds_max_list_size = prev_grading_seconds_max_list_size;
    }

    public String getLogging_level() {
        return logging_level;
    }

    public void setLogging_level(String logging_level) {
        this.logging_level = logging_level;
    }

    public int getSynchronous_submission_timeout_seconds() {
        return synchronous_submission_timeout_seconds;
    }

    public void setSynchronous_submission_timeout_seconds(int synchronous_submission_timeout_seconds) {
        this.synchronous_submission_timeout_seconds = synchronous_submission_timeout_seconds;
    }

    public int getDefault_estimated_grading_seconds() {
        return default_estimated_grading_seconds;
    }

    public void setDefault_estimated_grading_seconds(int default_estimated_grading_seconds) {
        this.default_estimated_grading_seconds = default_estimated_grading_seconds;
    }

    public String getDefault_grading_environment_setup_class_path() {
        return default_grading_environment_setup_class_path;
    }

    public void setDefault_grading_environment_setup_class_path(String default_grading_environment_setup_class_path) {
        this.default_grading_environment_setup_class_path = default_grading_environment_setup_class_path;
    }

    public String getDefault_grading_environment_setup_class_name() {
        return default_grading_environment_setup_class_name;
    }

    public void setDefault_grading_environment_setup_class_name(String default_grading_environment_setup_class_name) {
        this.default_grading_environment_setup_class_name = default_grading_environment_setup_class_name;
    }

    @Override
    public String toString() {
        return "ServiceConfig{" +
            "default_estimated_grading_seconds=" + default_estimated_grading_seconds +
            ", prev_grading_seconds_max_list_size=" + prev_grading_seconds_max_list_size +
            ", logging_level='" + logging_level + '\'' +
            ", synchronous_submission_timeout_seconds=" + synchronous_submission_timeout_seconds +
            ", default_grading_environment_setup_class_path='" + default_grading_environment_setup_class_path + '\'' +
            ", default_grading_environment_setup_class_name='" + default_grading_environment_setup_class_name + '\'' +
            '}';
    }
}
