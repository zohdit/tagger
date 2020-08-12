package usi.tagger.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import usi.tagger.utilities.Utilities;

public class Entity implements Comparable<Entity>, Serializable{

	private static final long serialVersionUID = -6920954405189674032L;

	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	private int id;
	private String textToShow;
	private String type;
	private boolean isInteresting;
	private ArrayList<String> tags = null;
	
	public String getTextToShow() {
		return textToShow;
	}
	public void setTextToShow(String textToShow) {
		this.textToShow = textToShow;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean hasAgreement() {
		
		if(tags == null || tags.size() == 0)
			return false;
		
		
		if(tags.size() >= Utilities.maxNumberOfEvaluators)
			return true;
		
		int overallEvaluations = tags.size();
		Map<String, Integer> assignedTags = new HashMap<String, Integer>();
		
		for(String tag:tags){
			if(assignedTags.containsKey(tag)){
				assignedTags.put(tag, assignedTags.get(tag)+1);
			} else {
				assignedTags.put(tag, 1);
			}
		}
		
		int maxValue = (Collections.max(assignedTags.values()));
		
		if(maxValue > 1 && ((double)maxValue/overallEvaluations) > 0.5)
			return true;
		
		return false;
	}
	
	
	public boolean hasAgreementForConflictsResolution() {
		
		if(tags == null)
			return false;
		
		
		int overallEvaluations = tags.size();
		Map<String, Integer> assignedTags = new HashMap<String, Integer>();
		
		if(tags.size() == 0)
			return false;
		
		
		for(String tag:tags){
			if(assignedTags.containsKey(tag)){
				assignedTags.put(tag, assignedTags.get(tag)+1);
			} else {
				assignedTags.put(tag, 1);
			}
		}
		
		int maxValue = (Collections.max(assignedTags.values()));
		
		if(maxValue > 1 && ((double)maxValue/overallEvaluations) > 0.5)
			return true;
		
		return false;
	}
	
	public boolean isFalsePositive() {
		
		if(tags == null)
			return false;
		
		if(tags.size() < 2)
			return false;
		
		int overallEvaluations = tags.size();
		Map<String, Integer> assignedTags = new HashMap<String, Integer>();
		
		for(String tag:tags){
			if(assignedTags.containsKey(tag)){
				assignedTags.put(tag, assignedTags.get(tag)+1);
			} else {
				assignedTags.put(tag, 1);
			}
		}
		
		int maxValue = (Collections.max(assignedTags.values()));
		String maxType = getMaxEntry(assignedTags).getKey();
		
		if(maxValue > 1 && ((double)maxValue/overallEvaluations) >= 0.5 && (maxType.toLowerCase().equals("false positive") 
				|| maxType.toLowerCase().equals("generic") || maxType.toLowerCase().equals("unclear")))
				//|| maxType.toLowerCase().equals("unclear"))) || ((double)falsePostivePlusUnclear/overallEvaluations) > 0.5))
			return true;
		
		return false;
	}
	
	public static Entry<String, Integer> getMaxEntry(Map<String, Integer> map) {        
	    Entry<String, Integer> maxEntry = null;
	    Integer max = Collections.max(map.values());

	    for(Entry<String, Integer> entry : map.entrySet()) {
	        Integer value = entry.getValue();

	        if(null != value && max == value) {
	            maxEntry = entry;
	        }
	    }

	    return maxEntry;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(Entity e) {
		return ((Integer)e.getTags().size()).compareTo((Integer)this.getTags().size());
	}
	public boolean isInteresting() {
		return isInteresting;
	}
	public void setInteresting(boolean isInteresting) {
		this.isInteresting = isInteresting;
	}
	
	
}
