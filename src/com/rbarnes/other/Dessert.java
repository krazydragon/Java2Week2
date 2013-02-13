/*
 * project	Java2Week2
 * 
 * package	com.rbarnes.other
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Feb 12, 2013
 */
//Java 1 week 4        
//January 2013         
//Full Sail University 
package com.rbarnes.other;



// TODO: Auto-generated Javadoc
/**
 * The Class Dessert.
 */
public class Dessert implements Inventory {

	
	String name;

	/**
	 * Instantiates a new dessert.
	 *
	 * @param name the name
	 */
	public Dessert(String name){
		setName(name);
	}
	
	/* (non-Javadoc)
	 * @see com.rbarnes.other.Inventory#setName(java.lang.String)
	 */
	@Override
	public boolean setName(String name) {
		this.name = name;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.rbarnes.other.Inventory#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
