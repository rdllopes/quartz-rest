package nitinka.quartzrest.config;

import io.dropwizard.Configuration;

public class QuartzRestServiceConfiguration extends Configuration{
    private String schedulerConfigsFolder;
//    private JMetricConfig jMetricConfig;

    public String getSchedulerConfigsFolder() {
        return schedulerConfigsFolder;
    }

    public QuartzRestServiceConfiguration setSchedulerConfigsFolder(String schedulerConfigsFolder) {
        this.schedulerConfigsFolder = schedulerConfigsFolder;
        return this;
    }

}
