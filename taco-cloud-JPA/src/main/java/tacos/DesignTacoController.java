package tacos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	@Autowired
	private final IngredientRepository ingredientRepo;
	
	public DesignTacoController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	public void addIngredientsToModel(Model model) {
		Iterable<Ingredient> ingredients = ingredientRepo.findAll();
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(
					type.toString().toLowerCase(),
					filterByType((List<Ingredient>) ingredients, type)); // The casting operation needs to be verified.
		}
	}
	
	
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	@PostMapping
	public String processTaco(
			@Valid Taco taco, Errors errors, 
			@ModelAttribute TacoOrder tacoOrder) {
		
		if (errors.hasErrors()) {
			return "design";
		}
		
		log.info("accessing processTaco method..");
		tacoOrder.addTaco(taco);
		log.info("Processing taco: {}", taco);
		
		return "redirect:/orders/current";
	}

	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
}