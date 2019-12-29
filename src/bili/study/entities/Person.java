package bili.study.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2019-12-28 13:24
 */
@Data
@NoArgsConstructor
public class Person {

    private Integer id;

    private String personName;

    public Person(String personName) {
        this.personName = personName;
    }

}
