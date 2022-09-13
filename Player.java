package a11922120;

import java.util.*;

public class Player implements Comparable<Player> {
	
	private String name;
	private Queue<VehicleCard> deck = new ArrayDeque<>();
	
	public Player(final String name) {
		if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name ist null oder leer");
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getScore() {
		int score = 0;
		for(VehicleCard card : deck) {
			score += card.totalBonus();
		}
		return score;
	}
	
	public void addCards(final Collection<VehicleCard> cards) {
		if(cards == null) throw new IllegalArgumentException("Cards sind leer");
		for(VehicleCard c : cards) {
			if(c == null) throw new IllegalArgumentException("Card ist leer");
		  deck.add(c);
		}
	}
	
	public void addCard(final VehicleCard card) {
		if(card == null) throw new IllegalArgumentException("Card ist leer");
		deck.add(card);
	}
	
	public void clearDeck() {
		deck.clear();
	}
	
	public List<VehicleCard> getDeck(){
		return new ArrayList<VehicleCard>(deck);
	}
	
	protected VehicleCard peekNextCard() {
		return deck.peek();
	}
	
	public VehicleCard playNextCard() {
		return deck.poll();
	}
	
	@Override
	public int compareTo(final Player other) {
		if(other == null) throw new IllegalArgumentException("Name ist leer");
		return this.name.compareToIgnoreCase(other.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name.toLowerCase());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		
		if(obj instanceof Player) {
			Player pl = (Player) obj;
			if(pl.name.equalsIgnoreCase(this.name)) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String result = name + "(" + getScore() + "):" ;
		for(VehicleCard c : deck) {
			result += '\n' + c.toString();
		}
		
		return result;
	}
	
	public boolean challengePlayer(Player p) {
		if(p == null || p.equals(this)) throw new IllegalArgumentException("Player Objekt ist null oder ungueltig");
		
		int deck_size = this.getDeck().size();
		Collection<VehicleCard> p1 = new ArrayList<>();
		Collection<VehicleCard> p2 = new ArrayList<>();
		
		while(true) {
		  if(this.getDeck().isEmpty() || p.getDeck().isEmpty()) {
			this.addCards(p1);
			p.addCards(p2);
			return false;
		  }  
		
		int winner = this.peekNextCard().compareTo(p.peekNextCard());
		p1.add(this.playNextCard());
		p2.add(p.playNextCard());
		
		  if(winner > 0) {
			 this.addCards(p2);
			 this.addCards(p1);
			 break;
		  } else if(winner < 0) {
			 p.addCards(p1);
			 p.addCards(p2);
			 break;
		    }
		}
		 return this.getDeck().size() > deck_size;
	} 
	
	public static Comparator<Player> compareByScore(){
		return new Comparator<Player> () {
			@Override 
			public int compare(Player a, Player b) {
				return Integer.compare(a.getScore(), b.getScore());
			}	
		};
	}
	public static Comparator<Player> compareByDeckSize(){
		return new Comparator<Player> () {
			@Override 
			public int compare(Player a, Player b) {
				return Integer.compare(a.deck.size(), b.deck.size());
			}
		};
	}
} 