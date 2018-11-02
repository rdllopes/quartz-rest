package nitinka.quartzrest;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import nitinka.quartzrest.config.QuartzRestServiceConfiguration;
import nitinka.quartzrest.core.SchedulerFactory;
import nitinka.quartzrest.resource.JobSchedulingResource;
import nitinka.quartzrest.resource.SchedulerResource;
import nitinka.quartzrest.resource.TriggerResource;

public class QuartzRestService extends Application<QuartzRestServiceConfiguration> {

    @Override
    public void initialize(Bootstrap<QuartzRestServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/META-INF/resources/webjars/swagger-ui/3.19.4/", "/swagger-ui"));
        bootstrap.getObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public void run(QuartzRestServiceConfiguration configuration, Environment environment) throws Exception {
        SchedulerFactory.buildSchedulers(configuration.getSchedulerConfigsFolder());

        environment.jersey().register(new ApiListingResource());
        environment.jersey().register(new SwaggerSerializers());
        environment.jersey().register(new JobSchedulingResource());
        environment.jersey().register(new SchedulerResource());
        environment.jersey().register(new TriggerResource());

        environment.getObjectMapper().setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

        BeanConfig config = new BeanConfig();
        config.setTitle("nitinka quartzrest");
        config.setVersion("1.0.0");
        config.setResourcePackage("nitinka.quartzrest.resource");
        config.setBasePath("/quartz-interface");
        config.setSchemes(new String[]{"http"});
        config.setScan(true);
    }

    public static void main(String[] args) throws Exception {
        args = new String[]{"server", args[0]};
        new QuartzRestService().run(args);
    }
}
