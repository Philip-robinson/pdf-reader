/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;

/**
 *
 * @author philip
 */
public class FieldStructure {
	private final List<DisplayedField> fields = new ArrayList<>();
	private final MapHolder left;

	public FieldStructure(double tollerance){
		left = new MapHolder(f->f.getLeft(), tollerance);
	}
	
	public void add(DisplayedField field){
		left.add(field);
	}

	public class MapHolder{
		private final TollerisableMap columns;
		private final TollerisableMap rows;
		private final Function<DisplayedField, Double> getX; 
		public MapHolder(Function<DisplayedField, Double> getX,
				                                   double tollerance){
			this.getX = getX;
			columns = new TollerisableMap(tollerance, 
					                      df->df.getLeft(), 
					                      df->df.getRight()-df.getLeft(),
			                              df->df.getBottom(),
			                              df->df.getTop()-df.getBottom());
			rows = new TollerisableMap(tollerance,
					                   df->df.getBottom(),
			                           df->df.getTop()-df.getBottom(), 
					                   df->df.getLeft(), 
					                   df->df.getRight()-df.getLeft());
		}

		public synchronized void add(DisplayedField field){
			Double x = getX.apply(field);
			Double y = field.getBottom();
			columns.add(x, y, field);
			rows.add(y, x, field);
		}

		public synchronized void tidy(){
			columns.tidy();
			rows.tidy();
		}

		private List<Sequence> getNumericIsolatedSequences() {
			return columns.getNumericNoneOverlappingSequences();
			
		}
	}

	public List<Sequence> getNumericIsolatedSequences(){
		List<Sequence> res = new ArrayList<>();
		res.addAll(left.getNumericIsolatedSequences());
		return res;
	}


}
