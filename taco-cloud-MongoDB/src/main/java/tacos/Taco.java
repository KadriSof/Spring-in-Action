package tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

// Taco is persisted as a member of the TacoOrder-rooted aggregate.
// Hence, Taco class doesnt' need to be annotated as "Document".
@Data
public class Taco {
	
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long.")
	private String name;
	
	private Date createdAt = new Date();
	
	@Size(min=1, message="You must choose at least 1 ingredient.")
	private List<Ingredient> ingredients = new ArrayList<>();

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
}