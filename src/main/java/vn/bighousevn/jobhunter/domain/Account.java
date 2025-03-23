package vn.bighousevn.jobhunter.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name= "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 

    private String _id; 
    private String username;
    private String phone;
    private String fullName;
    private String dob;
    private String createdAt;
    private String updatedAt;
    private String gender;
    private String role;
}
