package episen.sirius.ing2.proto_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String familyname;
    private String name;
    private Integer age;
    private String profession;
}
