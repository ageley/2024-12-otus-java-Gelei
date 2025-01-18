package homework;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> customersData;

    public CustomerService() {
        customersData = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    private Map.Entry<Customer, String> getImmutableEntry(Map.Entry<Customer, String> entry) {
        return Optional.ofNullable(entry)
                .map(customerData -> new Customer(customerData.getKey()))
                .map(customer -> new SimpleImmutableEntry<>(customer, entry.getValue()))
                .orElse(null);
    }

    public Map.Entry<Customer, String> getSmallest() {
        return getImmutableEntry(customersData.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getImmutableEntry(customersData.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customersData.put(new Customer(customer), data);
    }
}
