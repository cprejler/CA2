package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PhoneDTO;
import dto.PhoneDTO;
import dto.PhonesDTO;
import exceptions.PhoneNotFoundException;
import utils.EMF_Creator;
import facades.PhoneFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("phone")
public class PhoneResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/ca2",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final PhoneFacade FACADE =  PhoneFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getPhoneCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    
    @GET
    @Path("{number}")
    @Produces({MediaType.APPLICATION_JSON}) 
    public String getPersonByPhoneNumber(@PathParam("number") String number) throws PhoneNotFoundException {
        return GSON.toJson(FACADE.getPersonByPhoneNumber(number)); 
    }
    
    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON}) 
    @Consumes({MediaType.APPLICATION_JSON}) 
    public String addPhone(String phone){
        PhoneDTO pDTO = GSON.fromJson(phone, PhoneDTO.class);
        PhoneDTO phoneDTO = FACADE.addPhone(pDTO.getNumber(), pDTO.getDescription(), pDTO.getpDTOID());
        return GSON.toJson(phoneDTO);
    }
    
    @POST
    @Path("add/{id}")
    @Produces({MediaType.APPLICATION_JSON}) 
    @Consumes({MediaType.APPLICATION_JSON}) 
    public String addPhone(@PathParam("id") int id,String phone){
        PhoneDTO pDTO = GSON.fromJson(phone, PhoneDTO.class);
        pDTO.setpDTOID(id);
        PhoneDTO phoneDTO = FACADE.addPhone(pDTO.getNumber(), pDTO.getDescription(), pDTO.getpDTOID());
        return GSON.toJson(phoneDTO);
    }
    
    
    @PUT
    @Path("/edit/{value}")
    @Produces({MediaType.APPLICATION_JSON}) 
    @Consumes({MediaType.APPLICATION_JSON}) 
    public Response editPhone(@PathParam("value") int value, String phonesDTO) throws PhoneNotFoundException{
        PhonesDTO pDTO = GSON.fromJson(phonesDTO, PhonesDTO.class);
        List<PhoneDTO> phones = pDTO.getAll();
        
        PhonesDTO responseDTO = FACADE.editPhones(phones, value);
        return Response.ok(responseDTO).build();
    }
    
 
 
}
