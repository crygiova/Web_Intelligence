package ntnu.tdt4215.project;

import java.util.Comparator;

public class StringAndFloatComparator implements Comparator<StringAndFloat>{
	    @Override
	    public int compare(StringAndFloat o1, StringAndFloat o2) {
		return o1.compareTo(o2);
	    }
}
