/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rpl.test.pdf.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author philip
 */
public class TollerisableMap {
	private final double tollerance;
	private TreeMap<Double, Sequence> maps;
	private final Function<DisplayedField, Double> getColMin; 
	private final Function<DisplayedField, Double> getColWidth;
	private final Function<DisplayedField, Double> getColBase;
	private final Function<DisplayedField, Double> getColHigh;

	TollerisableMap(double tollerance, 
			        Function<DisplayedField, Double> getColMin, 
					Function<DisplayedField, Double> getColWidth, 
					Function<DisplayedField, Double> getColBase, 
					Function<DisplayedField, Double> getColHigh){
		this.tollerance=tollerance;
		maps = new TreeMap<>();
		this.getColWidth=getColWidth;
		this.getColMin=getColMin;
		this.getColBase=getColBase;
		this.getColHigh=getColHigh;
	}

	public void add(double baseAxis, double orthAxis, DisplayedField field){
		Sequence map = maps.get(baseAxis);
		System.out.println("base axis is "+baseAxis+" orth "+orthAxis);
		if (map==null){
			System.out.println("Creating "+baseAxis);
			map = new Sequence(getColMin, getColWidth, getColBase, getColHigh);
			maps.put(baseAxis, map);
			System.out.println("Maps size now "+maps.size());
		}
		map.put(orthAxis, field);
		System.out.println("Map size now "+map.size());
	}

	public void tidy(){
		System.out.println("-----> IN tidy");
		TreeMap<Double, Sequence> newMaps = new TreeMap<>();
		Double start = null;
		Double end = null;
		Sequence newMap = null;
		System.out.println("No of sequences = "+maps.size());
		for (Map.Entry<Double, Sequence> e: maps.entrySet()){
			double min = e.getValue().getMin();
			double max = e.getValue().getMax();

			System.out.println("Existing seq "+start+" to "+end);
			System.out.println("     New seq "+min+" to "+max);
			System.out.println("  Tollerance "+tollerance);

			if (start != null && min<end+tollerance && max>start-tollerance){
				System.out.println("Not generated new");
			}else{
				System.out.println("Generating new");
				newMap = new Sequence(getColMin, getColWidth, getColBase, getColHigh);
				start = e.getValue().getMin();
				end = e.getValue().getMax();
				newMaps.put(start, newMap);
			}
			System.out.println("Merging "+e.getValue().size()+" entries");
			newMap.merge(e.getValue());
			System.out.println("Merged to have  "+newMap.size()+" entries");
			end=e.getValue().getMax();
		}
		maps = newMaps;
	}

	public List<Sequence> getNumericNoneOverlappingSequences() {
		NavigableMap<Double, Sequence> newSeq =
				mergeLeft(numericOnly(maps));
		newSeq=mergeRight(newSeq);
		newSeq=mergeCentre(newSeq);
		return split(newSeq);
	}
	
	private NavigableMap<Double, Sequence> numericOnly(
			NavigableMap<Double, Sequence> seq) {
		return seq.entrySet().stream().collect(
				()->new TreeMap<Double, Sequence>(), 
				(a,b)->{
					Sequence num=b.getValue().getNumeric();
					System.out.println("Got numeric "+num);
					if (num!=null && num.size()>1){
						a.put(b.getKey(), num);
					}
				},
				TreeMap::putAll);
	}

	public List<Sequence> getSequences() {
		
		NavigableMap<Double, Sequence> newSeq = mergeLeft(maps);
		newSeq=mergeRight(newSeq);
		newSeq=mergeCentre(newSeq);
		return split(newSeq);
	}


	public NavigableMap<Double, Sequence> proc(NavigableMap<Double, Sequence> m,
				                              NavigableMap<Double, Sequence> a,
						                      double curKey, Sequence curSeq){
		if (m.size()==0) return a;
		Sequence ns = m.firstEntry().getValue();
		double nk = m.firstKey();
		NavigableMap<Double, Sequence> tail = m.tailMap(m.firstKey(), false);
		if (curSeq==null || Math.abs(nk-curKey) > tollerance){
			a.put(nk, ns);
			return proc(tail, a, nk, ns);
		}
		curSeq.merge(ns);
		return proc(tail, a, curKey, curSeq);

	}
	private NavigableMap<Double, Sequence> merge(
			NavigableMap<Double, Sequence> sequences,
			Function<Sequence, Double> ref) {
		NavigableMap<Double, Sequence> mapsResort= sequences.entrySet().
				stream().collect(
						()->new TreeMap<Double, Sequence>(),
				        (a, b)->a.put(ref.apply(b.getValue()), b.getValue()),
						TreeMap::putAll);
		return proc(mapsResort, new TreeMap<Double, Sequence>(), 0.0, null);
	}

	private NavigableMap<Double, Sequence> mergeLeft(
			NavigableMap<Double, Sequence> sequences) {
		return merge(sequences, seq->seq.getMin());
	}

	private NavigableMap<Double, Sequence> mergeRight(
			NavigableMap<Double, Sequence> sequences) {
		return merge(sequences, seq->seq.getMax());
	}

	private NavigableMap<Double, Sequence> mergeCentre(
			NavigableMap<Double, Sequence> sequences) {
		return merge(sequences, seq->(seq.getMax()+seq.getMin())/2.0);
	}

	private List<Sequence> split(NavigableMap<Double, Sequence> squences) {
		return squences.entrySet().stream().collect(
				()->new ArrayList<Sequence>(), 
				(a, e)->{
					a.add(e.getValue());
				}, 
				ArrayList::addAll);
	}

}
