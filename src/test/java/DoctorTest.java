import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class DoctorTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
  }

  @After
  public void tearDown() {
  try(Connection con = DB.sql2o.open()) {
    String deletePatientQuery = "DELETE FROM patients *;";
    String deleteDoctorsQuery = "DELETE FROM doctors *;";
    con.createQuery(deletePatientQuery).executeUpdate();
    con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void doctor_instantiatesCorrectly_true() {
    Doctor testDoctor = new Doctor("garrett");
    assertEquals(true, testDoctor instanceof Doctor);
  }

  @Test
  public void doctor_instantiesWithName_String() {
    Doctor testDoctor = new Doctor("garrett");
    assertEquals("garrett",testDoctor.getName());
  }

  @Test
  public void all_returnsTrueIfDescriptionAretheSame() {
    Doctor firstDoctor = new Doctor("garrett");
    Doctor otherDoctor = new Doctor("garrett");

    assertTrue(firstDoctor.equals(otherDoctor));
  }

  @Test
  public void save_returnsTrueIfDescriptionMatches_true() {
    Doctor newDoctor = new Doctor("garrett");
    newDoctor.save();
    assertTrue(Doctor.all().get(0).equals(newDoctor));
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
   Doctor firstDoctor = new Doctor("garrett");
   firstDoctor.save();
   Doctor secondDoctor = new Doctor("Buy groceries");
   secondDoctor.save();
   assertEquals(true, Doctor.all().get(0).equals(firstDoctor));
   assertEquals(true, Doctor.all().get(1).equals(secondDoctor));
  }

  @Test
  public void getId_instantiatesWithId() {
    Doctor myDoctor = new Doctor("Jackson");
    myDoctor.save();
    assertTrue(myDoctor.getId() > 0);
  }

  @Test
  public void find_findsTheDoctorFromID_true() {
    Doctor newDoctor = new Doctor("Jackson");
    newDoctor.save();
    assertEquals(Doctor.find(newDoctor.getId()) , newDoctor);
  }


   @Test
   public void getTasks_retrievesAllTasksFromDatabase_taskList() {
     Doctor myCategory = new Doctor("Household");
     myCategory.save();
     Patient firstTask = new Patient("Mow the lawn", myCategory.getId());
     firstTask.save();
     Patient secondTask = new Patient("Do the dishes", myCategory.getId());
     secondTask.save();
     Patient[] tasks = new Patient[] {firstTask, secondTask};
     assertTrue(myCategory.getPatients().containsAll(Arrays.asList(tasks)));
   }
}
