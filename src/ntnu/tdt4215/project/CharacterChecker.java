package ntnu.tdt4215.project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterChecker {
    public static Pattern ALPHANUMERIC = Pattern.compile("[A-Za-z]+");

    public static Pattern SPLITTITLE = Pattern.compile("^(.*?) (.*)");

    public static boolean isStringUseful(String s) {

	if (s == null) {

	    return false;

	} else {

	    Matcher m = ALPHANUMERIC.matcher(s);

	    return m.find();

	}

    }

    public static String[] splitTitleMatcher(String s) {

	Matcher m = SPLITTITLE.matcher(s);

	m.find();

	String[] res = { m.group(1), m.group(2) };

	return res;

    }

    public static String[] splitTitle(String s) {
	boolean exit = false;
	int i;
	for (i = 0; i < s.length() && !exit; i++) {
	    if (Character.getType(s.charAt(i)) == 12) {
		exit = true;
	    }
	}
	String[] res = new String[2];
	res[0] = s.substring(0, i);
	res[1] = s.substring(i);
	return res;
    }

    public static String filterColumn(String toFilter) {
	return toFilter.replaceAll("[)|(|:]", " ");
    }
}