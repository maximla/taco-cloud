package tacos.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tacos.TacoOrder;

/**
 * EXAMPLES OF KEYWORDS
 *  IsAfter, After, IsGreaterThan, GreaterThan
 *  IsGreaterThanEqual, GreaterThanEqual
 *  IsBefore, Before, IsLessThan, LessThan
 *  IsLessThanEqual, LessThanEqual
 *  IsBetween, Between
 *  IsNull, Null
 *  IsNotNull, NotNull
 *  IsIn, In
 *  IsNotIn, NotIn
 *  IsStartingWith, StartingWith, StartsWith
 *  IsEndingWith, EndingWith, EndsWith
 *  IsContaining, Containing, Contains
 *  IsLike, Like
 *  IsNotLike, NotLike
 *  IsTrue, True
 *  IsFalse, False
 *  Is, Equals
 *  IsNot, Not
 *  IgnoringCase, IgnoresCase
 */
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    /**
     * Builds requests for custom methods using its names.
     *
     * @param deliveryZip
     * @return
     */
    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    /**
     * Builds requests for custom methods using its names.
     *
     * @param deliveryZip
     * @param startDate
     * @param endDate
     * @return
     */
    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    /**
     * Builds requests for custom methods using its names.
     *
     * @param deliveryZip
     * @param deliveryCity
     * @return
     */
    List<TacoOrder> findByDeliveryZipIgnoreCaseAndDeliveryCityIgnoreCase(String deliveryZip, String deliveryCity);

    /**
     * Builds requests for custom methods using its names.
     *
     * @param city
     * @return
     */
    List<TacoOrder> findByDeliveryCityOrderByDeliveryStateDesc(String city);

    /**
     * Using of HQL
     *
     * @return
     */
//    @Query("select o from TacoOrder where o.deliveryCity='Seattle'")
//    List<TacoOrder> readOrdersDeliveredInSeattle();
}
