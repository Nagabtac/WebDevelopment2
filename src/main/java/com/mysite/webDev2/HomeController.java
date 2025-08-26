package com.mysite.webDev2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    // in-memory "database"
    private List<Car> cars = new ArrayList<>();

    public HomeController() {
        cars.add(new Car(0, 12345, "Toyota", "Corolla", 2020, "Red", "Sedan", "Petrol", "Automatic"));
        cars.add(new Car(1, 67890, "Honda", "Civic", 2021, "Blue", "Sedan", "Diesel", "Manual"));
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cars", cars);
        return "index";
    }

    // ----------- ADD ------------
    @GetMapping("/add")
    public String addCarForm(Model model) {
        model.addAttribute("car", new Car(0,0,"","",0,"","","",""));
        return "add";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car) {
        car.setCarId(cars.size()); // assign id
        cars.add(car);
        return "redirect:/";
    }

    // ----------- EDIT ------------
    @GetMapping("/edit")
    public String editCarForm(@RequestParam int id, Model model) {
        Car car = cars.stream().filter(c -> c.getCarId() == id).findFirst().orElse(null);
        model.addAttribute("car", car);
        return "edit";
    }

    @PostMapping("/edit")
    public String editCar(@ModelAttribute Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getCarId() == updatedCar.getCarId()) {
                cars.set(i, updatedCar);
                break;
            }
        }
        return "redirect:/";
    }

    // ----------- DELETE ------------
    @GetMapping("/delete")
    public String deleteCar(@RequestParam int id) {
        cars.removeIf(c -> c.getCarId() == id);
        return "redirect:/";
    }
}
