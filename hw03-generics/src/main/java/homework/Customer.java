package homework;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@AllArgsConstructor
public class Customer {
    @EqualsAndHashCode.Include
    private final long id;

    @Setter
    private String name;

    @Setter
    private long scores;

    public Customer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.scores = customer.getScores();
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", scores=" + scores + '}';
    }
}
