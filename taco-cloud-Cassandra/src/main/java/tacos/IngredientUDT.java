package tacos;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/*
 * IngredientUDT serves as a UDT that defines the structure of the ingredients column in the tacos table.
 * It contains fields representing the attributes of an ingredient, such as id, name, and type.
 * Instances of IngredientUDT will be used to store ingredient data directly within the ingredients column of the tacos table.
 */

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@UserDefinedType("ingredient")
public class IngredientUDT {

	private final String name;
	
	private final Ingredient.Type type;
}
