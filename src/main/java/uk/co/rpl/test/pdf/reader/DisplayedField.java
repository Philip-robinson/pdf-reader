/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.util.regex.Pattern;

/**
 *
 * @author philip
 */
public class DisplayedField {
	private final double x;
	private final double y;
	private final double width;
	private final double height;
	private final FieldType type;
	private final String data;

	private static final Pattern NUMBER= Pattern.compile(
			"^\\(?[+-]?[0-9]+(\\.[0-9]{2,2})?\\)?$");
	public DisplayedField(double x, double y,
			              double width, double height, String data)
			throws FieldFormatException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.data = data.trim();
		if (NUMBER.matcher(data).matches()){
			System.out.println("NUMBER "+data);
			
			if (data.charAt(0)=='(' && !data.endsWith(")")) 
				throw new FieldFormatException(
						"Number with opening brace must have closing brace");
			type=FieldType.NUMBER;
		}else{
			System.out.println("TEXT "+data);
			if (data.length()==0) throw new FieldFormatException("Zero length field");
			type = FieldType.TEXT;
		}
	}

	public double getLeft() {
		return x;
	}
	public double getRight() {
		return x+width;
	}
	public double getCentre() {
		return x+width/2;
	}

	public double getBottom(){
		return y;
	}

	public FieldType getType(){
		return type;
	}

	public String getValue(){
		return data;
	}

	double getTop() {
		return y+height;
	}

	boolean isNumber() {
		return type==FieldType.NUMBER;
	}

	@Override
	public String toString() {
		return "DisplayedField{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", type=" + type + ", data=" + data + '}';
	}
	
}
