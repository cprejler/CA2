package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityInfoDTO;
import facades.CityInfoFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("zipcode")
public class CityInfoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/ca2",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final CityInfoFacade FACADE =  CityInfoFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCityInfoCount() {
        long count = FACADE.getCityInfoCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    
    @GET
    @Path("{zipcode}")
    @Produces({MediaType.APPLICATION_JSON}) 
    public String getPersonByZipcode(@PathParam("zipcode") int zipcode) {
        return GSON.toJson(FACADE.getPersonsByCityInfo(zipcode)); 
    }
    
    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON}) 
    @Consumes({MediaType.APPLICATION_JSON}) 
    public String addPhone(String cityInfo){
        CityInfoDTO cDTO = GSON.fromJson(cityInfo, CityInfoDTO.class);
        CityInfoDTO cityDTO = FACADE.addCity(cDTO.getZipCode(), cDTO.getCity(), cDTO.getcDTOID());
        return GSON.toJson(cityDTO);
    }
    
 
 
}
