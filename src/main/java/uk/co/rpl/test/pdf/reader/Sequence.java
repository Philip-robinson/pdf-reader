/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 *
 * @author philip
 */
	public class Sequence {
		private Double min = null;
		private Double max = null;
		private Double low = null;
		private Double top= null;
		private final Function<DisplayedField, Double> colMin;
		private final Function<DisplayedField, Double> colWidth;
		private final Function<DisplayedField, Double> colBase;
		private final Function<DisplayedField, Double> colHeight;

		private final TreeMap<Double, DisplayedField> newMap = new TreeMap<>();

		public Sequence(Function<DisplayedField, Double> colMin,
				        Function<DisplayedField, Double> colWidth,
						Function<DisplayedField, Double> colBase,
				        Function<DisplayedField, Double> colHeight) {
			this.colMin=colMin;
			this.colWidth=colWidth;
			this.colBase=colBase;
			this.colHeight=colHeight;
		}

		public void put(double index, DisplayedField field){
			newMap.put(index, field);
			if (min==null || colMin.apply(field)<min){
				min = colMin.apply(field);
			}
			if (max==null || colMin.apply(field)+colWidth.apply(field)>max){
				max = colMin.apply(field)+colWidth.apply(field);
			}
			if (low==null || colBase.apply(field)<low){
				low = colBase.apply(field);
			}
			if (top==null || colBase.apply(field)+colHeight.apply(field)>top){
				top = colBase.apply(field)+colHeight.apply(field);
			}
		}

		public void merge(Sequence seq){
			for (Map.Entry<Double, DisplayedField> e: seq.newMap.entrySet()){
				put(e.getKey(), e.getValue());
			}
		}

		public double getMin(){
			return min==null?0:min;			
		}

		public double getWidth(){
			return max==null||min==null?0:(max-min);
		}

		public double getMax() {
			return max==null?0:max;
		}

		public Sequence getNumeric(){
			Sequence seq = new Sequence(colMin, colWidth, colBase, colHeight);
			for (Map.Entry<Double, DisplayedField> e: newMap.entrySet()){
				if (e.getValue().isNumber()){
					seq.put(e.getKey(), e.getValue());
					System.out.println("Adding as Number "+e.getValue().getValue());
				}
			}
			return seq;
		}

		public List<DisplayedField> list() {
			return new ArrayList(newMap.values());
		}

		public double getBase() {
			return low;
		}

		public double getHeight() {
			return top-low;
		}

		public int size() {
			return newMap.size();
		}

		@Override
		public String toString() {
			return "Sequence{" + "min=" + min + ", max=" + max + ", low=" + low + ", high=" + top + ", newMap=" + newMap + '}';
		}

		public double getTop() {
			return top;
		}
		
	}
