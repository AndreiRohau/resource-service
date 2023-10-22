package my.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "resources")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Integer resourceId;

    @Column(name = "resource")
    @Lob
    private byte[] resource;

    public Resource(byte[] resource) {
        this.resource = resource;
    }
}
