package src.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;
}
