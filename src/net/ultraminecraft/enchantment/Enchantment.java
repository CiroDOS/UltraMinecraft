package net.ultraminecraft.enchantment;

import java.util.Locale;

import net.ultraminecraft.config.GameConfig;
import net.ultraminecraft.util.Resource;
import net.ultraminecraft.util.Resourcerized;

public enum Enchantment implements Resourcerized {
	PROTECTION(false), SHARPNESS(false), FIRE_ASPECT(false), CURSE_OF_BINDING(true), PROJECTILE_PROTECTION(false),
	CURSE_OF_VANISHING(true), SLOW_FALLING(false);

	private boolean curse = false;

	Enchantment(boolean curse) {
		this.curse = curse;
	}
	
	public static class Level {
		
		
		private final int level;
		
		public Level(int level) {
			this.level = level;
		}
		
		@Override
		public String toString() {
			return RomanNumerals.convertIntegerToRoman(this.level);
		}
		
	}
	
	public class RomanNumerals {
		
		public static final int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		public static final String[] romanLiterals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV",
		"I" };
		
		public static int value(char r) {
			return switch (r) {
			case 'I' -> 1;
			case 'V' -> 5;
			case 'X' -> 10;
			case 'L' -> 50;
			case 'C' -> 100;
			case 'D' -> 500;
			case 'M' -> 1000;
			default -> -1;
			};
		}
		
		public static int convertRomanToInt(String s) {
			
			int total = 0;
			for (int i = 0; i < s.length(); i++) {
				int s1 = value(s.charAt(i));
				if (i + 1 < s.length()) {
					int s2 = value(s.charAt(i + 1));
					if (s1 >= s2) {
						total = total + s1;
					} else {
						total = total - s1;
					}
				} else {
					total = total + s1;
				}
			}
			// returns corresponding integer value
			return total;
		}
		
		public static final String convertIntegerToRoman(int number) {
			
			StringBuilder s = new StringBuilder();
			
			for (int i = 0; i < values.length; i++) {
				while (number >= values[i]) {
					number -= values[i];
					s.append(romanLiterals[i]);
				}
			}
			return s.toString();
		}
		
	}
	
	public static Enchantment fromResource(Resource resource) {
		return switch (resource.toString()) {
		
			case "fire_aspect"              -> Enchantment.FIRE_ASPECT;
			case "protection"               -> Enchantment.PROTECTION;
			case "sharpness"                -> Enchantment.SHARPNESS;
			case "curse_of_binding"         -> Enchantment.CURSE_OF_BINDING;
			case "projectile_protection"    -> Enchantment.PROJECTILE_PROTECTION;
			case "curse_of_vanishing"       -> Enchantment.CURSE_OF_VANISHING;
			case "slow_falling"             -> Enchantment.SLOW_FALLING;
			
			default -> null;
		};
	}
	
	public boolean isCurse() {
		return this.curse;
	}

	@Override
	public Resource toResource() {
		return switch (this) {
			
			case FIRE_ASPECT			-> Resource.get("fire_aspect"); 
			case PROTECTION				-> Resource.get("protection");
			case SHARPNESS				-> Resource.get("sharpness");
			case CURSE_OF_BINDING		-> Resource.get("curse_of_binding");
			case PROJECTILE_PROTECTION	-> Resource.get("projectile_protection");
			case CURSE_OF_VANISHING		-> Resource.get("curse_of_vanishing");
			case SLOW_FALLING			-> Resource.get("slow_falling");
			
			default -> null;
		};
	}
	
	public String toString(Locale language) {
		
		if (language.equals(Locale.forLanguageTag("en-US"))) {

				return switch (this) {
				
				case FIRE_ASPECT            -> "Fire aspect";
				case PROTECTION             -> "Protection";
				case SHARPNESS              -> "Sharpness";
				case CURSE_OF_BINDING       -> "Curse of Binding";
				case PROJECTILE_PROTECTION  -> "Projectile protection";
				case CURSE_OF_VANISHING     -> "Curse of Vanishing";
				case SLOW_FALLING           -> "Slow falling";
					
				default -> null;
					
			};
				
		} else if (language.equals(Locale.forLanguageTag("es-ES"))) {

			return switch (this) {
				
				case FIRE_ASPECT            -> "Aspecto ígneo";
				case PROTECTION             -> "Protección";
				case SHARPNESS              -> "Filo";
				case CURSE_OF_BINDING       -> "Maldición de ligamiento";
				case PROJECTILE_PROTECTION  -> "Protección contra proyectiles";
				case CURSE_OF_VANISHING     -> "Maldición de desvanecimiento";
				case SLOW_FALLING           -> "Caída lenta";
					
				default -> null;
			};

		}
			
		return null;
	}
	

	@Override
	public String toString() {
		return toString(GameConfig.LANGUAGE);
	}
	
}
