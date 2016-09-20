import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class PatientTest {

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
  public void patient_instantiatesCorrectly_true() {
    Patient testPatient = new Patient("garrett",1);
    assertEquals(true, testPatient instanceof Patient);
  }

  @Test
  public void patient_instantiesWithName_String() {
    Patient testPatient = new Patient("garrett",1);
    assertEquals("garrett",testPatient.getName());
  }

  @Test
  public void all_returnsTrueIfDescriptionAretheSame() {
    Patient firstPatient = new Patient("garrett",1);
    Patient otherPatient = new Patient("garrett",1);

    assertTrue(firstPatient.equals(otherPatient));
  }

  @Test
  public void save_returnsTrueIfDescriptionMatches_true() {
    Patient newPatient = new Patient("garrett",1);
    newPatient.save();
    assertTrue(Patient.all().get(0).equals(newPatient));
  }

  @Test
  public void all_returnsAllInstancesOfPatient_true() {
   Patient firstPatient = new Patient("garrett",1);
   firstPatient.save();
   Patient secondPatient = new Patient("Buy groceries",1);
   secondPatient.save();
   assertEquals(true, Patient.all().get(0).equals(firstPatient));
   assertEquals(true, Patient.all().get(1).equals(secondPatient));
  }

  @Test
  public void getId_instantiatesWithId() {
    Patient myPatient = new Patient("Jackson",1);
    myPatient.save();
    assertTrue(myPatient.getId() > 0);
  }

  @Test
  public void find_findsThePatientFromID_true() {
    Patient newPatient = new Patient("Jackson",1);
    newPatient.save();
    assertEquals(Patient.find(newPatient.getId()) , newPatient);
  }

}
