import org.sql2o.*;
import java.util.*;

public class Patient {
  private String name;
  private int id;
  private int doctorId;

  public Patient (String name, int doctorId){
    this.name = name;
    this.doctorId = doctorId;
  }

  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public int getDoctorId(){
    return doctorId;
  }

  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof Patient)){
      return false;
    } else {
    Patient newPatient = (Patient) obj;
    return this.getName().equals(newPatient.getName()) && (this.getId() == newPatient.getId());
    }
  }

  public static List<Patient> all() {
    String sql = "SELECT id, name, doctorid FROM patients";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Patient.class);
    }
  }

  public void save() {
     try(Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO patients(name, doctorid) VALUES (:name , :doctorid)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("name", this.name)
         .addParameter("doctorid", this.doctorId)
         .executeUpdate()
         .getKey();
     }
   }

   public static Patient find(int id) {
     try(Connection con = DB.sql2o.open()){
       String sql = "SELECT * FROM patients WHERE id=:id";
       Patient patient= con.createQuery(sql)
       .addParameter("id", id)
       .executeAndFetchFirst(Patient.class);
       return patient;
     }
   }

}
