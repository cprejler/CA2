package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import static entities.Address_.cityinfo;
import entities.CityInfo;
import utils.EMF_Creator;
import entities.Person;
import entities.Phone;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/ca2_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = PersonFacade.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            Person p1 = new Person("batman@arto.dk", "batman", "batmansen");
            
            Person p2 = new Person("superman@arto.dk", "superman", "supermansen");
            p1.setAddress(new Address("Valbylanggade", "den er lang"));
            p2.setAddress(new Address("valdemarsgade", "LOL"));
            List<Phone> phoneList1 = new ArrayList<>();
            Phone phone1 = new Phone("20204040", "Home");
            Phone phone2 = new Phone("20204040", "Work");
            phoneList1.add(phone1);
            phoneList1.add(phone2);
            List<Phone> phoneList2 = new ArrayList<>();
            Phone phone3 = new Phone("20204040", "Home");
            Phone phone4 = new Phone("20204040", "Work");
            phoneList2.add(phone3);
            phoneList2.add(phone4);
            CityInfo c1 = new CityInfo(2860, "Søborg");
            CityInfo c2 = new CityInfo(2450, "København sv");
            p1.getAddress().setCityinfo(c1);
            p2.getAddress().setCityinfo(c2);
            
            p1.setPhones(phoneList1);
            em.persist(p1);
            em.persist(p2);
            

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(2, facade.getPersonCount());
    }
    
    
    @Test
    public void testGetAllPersons(){
       List<PersonDTO> p = facade.getAllPersons().getAll();
       assertEquals(2, p.size());
    }
    
    @Test
    public void testGetPerson()throws PersonNotFoundException{
        List<PersonDTO> pList = facade.getAllPersons().getAll();
        
        String email = pList.get(0).getEmail();
        
        long fakeid = pList.get(0).getId();
        int id = (int)fakeid;
        assertTrue(facade.getPerson(id).getEmail().contains(email));
        assertEquals(pList.size(), 2);
        
        
    }
    
    
    @Test
    public void testAddPerson(){
    Person testP = new Person("Iron", "Man", "iron@man.dk");
    Address testA = new Address("WallStreet", "10 th");
    
    assertEquals(2, facade.getPersonCount());
    facade.addPerson("Iron", "Man", "iron@man.dk");
    
    assertEquals(3, facade.getPersonCount());
    facade.addAddress(testP, testA);
    
    assertEquals(testP.getAddress().getStreet(), "WallStreet");
    
    }
}
