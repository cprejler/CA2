package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cors.CorsRequestFilter.class);
        resources.add(cors.CorsResponseFilter.class);
        resources.add(exceptions.AddressNotFoundExceptionMapper.class);
        resources.add(exceptions.CityInfoNotFoundExceptionMapper.class);
        resources.add(exceptions.GenericExceptionMapper.class);
        resources.add(exceptions.PersonNotFoundExceptionMapper.class);
        resources.add(exceptions.PhoneNotFoundExceptionMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(rest.AddressResource.class);
        resources.add(rest.CityInfoResource.class);
        resources.add(rest.PersonResource.class);
        resources.add(rest.PhoneResource.class);
    }
    
}
