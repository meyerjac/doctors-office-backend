import org.sql2o.*;
import java.util.*;

public class Doctor {
  private String name;
  private int id;

  public Doctor (String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof Doctor)){
      return false;
    } else {
    Doctor newDoctor = (Doctor) obj;
    return this.getName().equals(newDoctor.getName()) && (this.getId() == newDoctor.getId());
    }
  }

  public static List<Doctor> all() {
    String sql = "SELECT id, name FROM doctors";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  public int getId(){
    return id;
  }

  public void save() {
     try(Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO doctors(name) VALUES (:name)";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("name", this.name)
         .executeUpdate()
         .getKey();
     }
   }

   public static Doctor find(int id) {
     try(Connection con = DB.sql2o.open()){
       String sql = "SELECT * FROM doctors WHERE id=:id";
       Doctor doctor = con.createQuery(sql)
       .addParameter("id", id)
       .executeAndFetchFirst(Doctor.class);
       return doctor;
     }
   }

   public List<Patient> getPatients(){
     try(Connection con = DB.sql2o.open()){
       String sql = "SELECT * FROM patients where doctorID=:id";
       return con.createQuery(sql)
          .addParameter("id",this.id)
          .executeAndFetch(Patient.class);
     }
   }

}
