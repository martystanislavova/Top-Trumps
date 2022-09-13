package a11922120;

import java.util.Set;

import a11922120.VehicleCard.Category;
import java.util.Map;
import java.util.LinkedHashSet;
import static java.lang.Math.abs;

public class FoilVehicleCard extends VehicleCard{

	private Set<Category> specials;
	
	public FoilVehicleCard(final String name, final Map<Category, Double>
	categories, final Set<Category> specials) {
		
		super(name, categories);
		if(specials == null || specials.isEmpty() || specials.size() > 3 ) throw new IllegalArgumentException("Specials hat ungueltiges Item");
		
		for(VehicleCard.Category s : specials) {
		 if(s == null) throw new IllegalArgumentException("Specials besitzt ungueltiges Item");
		}
		
		this.specials = new LinkedHashSet<Category> (specials);
	}
	
	public Set<Category> getSpecials() {
		return new LinkedHashSet<Category>(specials);
	}
	
	@Override
	public int totalBonus() {
		int specialTotalBonus = super.totalBonus();
		for (Category spec : specials) {
		   for(Map.Entry<Category,Double> cat : this.getCategories().entrySet()) {
		 	  if(cat.getKey() == spec) {
		 	    specialTotalBonus+= abs(spec.bonus(cat.getValue()));
		 	    break;
			  }
		   }
		}
		return specialTotalBonus;
	}
	
	@Override 
    public String toString() {
		boolean first = false;
		String result = "- " + getName()  + "(" 
		  + totalBonus() +  ") -> {";
		
		for(Map.Entry<Category, Double> cat : this.getCategories().entrySet()) {
			if(first) {
				result += ", ";
				if(specials.contains(cat.getKey())) 
					result += "*" + cat.getKey() + "*=" + cat.getValue();
				else 
					result += cat;
			} 
			 else {
				first = true;
				if(specials.contains(cat.getKey())) 
                   result += "*" + cat.getKey() + "*=" + cat.getValue();
				else result += cat;
			}
		}
		return result +=  "}";
	} 
}

