package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRepo;
    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(@ModelAttribute TacoOrder tacoOrder) {
        fillDefaultData(tacoOrder);
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    private void fillDefaultData(@NonNull TacoOrder tacoOrder){
        if(Utils.isNullOrEmpty(tacoOrder.getDeliveryName())){
            tacoOrder.setDeliveryName("Name");
        }

        if(Utils.isNullOrEmpty(tacoOrder.getDeliveryStreet())){
            tacoOrder.setDeliveryStreet("Street");
        }

        if(Utils.isNullOrEmpty(tacoOrder.getDeliveryCity())){
            tacoOrder.setDeliveryCity("City");
        }

        if(Utils.isNullOrEmpty(tacoOrder.getDeliveryState())){
            tacoOrder.setDeliveryState("State");
        }

        if(Utils.isNullOrEmpty(tacoOrder.getDeliveryZip())){
            tacoOrder.setDeliveryZip("1234");
        }

        if(Utils.isNullOrEmpty(tacoOrder.getCcNumber())){
            tacoOrder.setCcNumber("378282246310005");
        }

        if(Utils.isNullOrEmpty(tacoOrder.getCcExpiration())){
            tacoOrder.setCcExpiration("11/24");
        }

        if(Utils.isNullOrEmpty(tacoOrder.getCcCVV())){
            tacoOrder.setCcCVV("123");
        }
    }
}
