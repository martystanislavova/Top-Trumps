package a11922120;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Objects;

public class VehicleCard implements Comparable<VehicleCard> {
	
	public enum Category { 
		ECONOMY_MPG("Miles/Gallon"), CYLINDERS_CNT("Zylinder"), 
		DISPLACEMENT_CCM("Hubraum[cc]"), POWER_HP("Leistung[hp]"), 
		WEIGHT_LBS("Gewicht[lbs]"){ 
			@Override 
			public boolean isInverted() { 
				return true;
			}
		}, ACCELERATION("Beschleunigung"){
			@Override
			public boolean isInverted() { 
				return true;
			}
		},  YEAR("Baujahr[19xx]");
		
		private final String categoryName;
		
		private Category(final String categoryName) {
			if(categoryName == null || categoryName.isEmpty()) throw new IllegalArgumentException("Name ist ungueltig");
			this.categoryName = categoryName;
		}
		
		public boolean isInverted() {
			return false;
		}
		
		public int bonus(final Double value) {
			int res = value.intValue();
			
			if(this.isInverted()) {
				return -res;
			}
			else return res;
		}
		
		@Override
		public String toString() {
			return categoryName;
		}
	}
	
	private String name;
	private Map<Category, Double> categories;
	
	public VehicleCard(final String name, final Map<Category, Double> categories) {
		
		if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name ist empty oder 0");
		if(categories == null || categories.size() != 7) throw new IllegalArgumentException("Kategorie ist 0 oder besitzt nicht alle Kategorie Werte");
		
		for(Map.Entry<Category, Double> cat : categories.entrySet()) { 
		 if(cat.getValue() == null || cat.getValue() < 0) throw new IllegalArgumentException("Kategorie ist ungueltig");
		  if(cat.getKey() == null) throw new IllegalArgumentException("Kategorie ist ungueltig");
		}
		
	    this.name = name;
	    this.categories = new LinkedHashMap<Category, Double>(categories);
	}
	
	public String getName() { 
		return this.name;
	}
	
	public Map<Category, Double> getCategories(){
		return new LinkedHashMap<Category, Double>(categories);
	}
	
	public static Map<Category, Double> newMap(double economy, double 
			cylinders, double displacement, double power, double weight, double 
			acceleration, double year) {
		
		 Map<Category, Double> nMap = new LinkedHashMap<Category, Double>();
		
	     nMap.put(Category.ECONOMY_MPG, economy);
		 nMap.put(Category.CYLINDERS_CNT, cylinders);
		 nMap.put(Category.DISPLACEMENT_CCM, displacement);
		 nMap.put(Category.POWER_HP, power);
		 nMap.put(Category.WEIGHT_LBS, weight);
		 nMap.put(Category.ACCELERATION, acceleration);
		 nMap.put(Category.YEAR, year);
		 
		 return nMap;
	}
	
	@Override
	public int compareTo(final VehicleCard other) {
		return this.totalBonus() - other.totalBonus();
 	}
	
	public int totalBonus() {
		int totalBonus = 0;
		 for(Map.Entry<Category, Double> cat : this.categories.entrySet()) {
			totalBonus += cat.getKey().bonus(cat.getValue());
		 }
		return totalBonus;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, totalBonus());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		if(obj instanceof VehicleCard) {
			VehicleCard o = (VehicleCard) obj;
			if(o.name.equals(this.name) && o.totalBonus() == this.totalBonus()) return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "- " + name  + "(" + totalBonus() +  ") -> " + categories;
	}
} 