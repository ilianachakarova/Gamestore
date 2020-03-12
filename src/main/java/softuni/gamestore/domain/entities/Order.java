package softuni.gamestore.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    public Order() {
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order",cascade ={CascadeType.PERSIST, CascadeType.MERGE})

    private Set<Game>games;
}
