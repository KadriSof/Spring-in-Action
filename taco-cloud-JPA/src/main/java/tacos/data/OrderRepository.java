package tacos.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tacos.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long>{

	// Fetch all orders delivered to a given ZIP code.
	List<TacoOrder> findByDeliveryZip(String deliveryZip);
	
	// Fetch all orders delivered to a given ZIP with a given date range.
	List<TacoOrder> findOrdersByDeliveryZipAndPlacedAtBetween(
			String deliveryZip, Date startDate, Date endDate);
	
	// Alternatively..
	@Query("Order o where o.deliveryCity='Seattle'")
	List<TacoOrder> readOrdersDeliveredInSeattle();
}
