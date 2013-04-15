package ntnu.tdt4215.project;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    private final static String PRTNFILE = "File not found";
    private final static String PRTNREAD = "File not read";
    private final static String PRTNWRITE = "Writing nt done";
    private final static String PRTNEND = "Il file non stato chiuso correttamente";
    private final static String DOCU_FOLDER = "./docu/";
    private final static String T_FOLDER = DOCU_FOLDER + "NLH/T/";
    private final static String OPEN_DOC = "\n<doc>\n";
    private final static String CLOSE_DOC = "\n</doc>\n";

    public static String parseHTM(String fnameInput, String fnameOut)
	    throws IOException {

	boolean first = true;
	File input = new File(fnameInput);
	Document doc = Jsoup.parse(input, "UTF-8");
	Elements table = doc.getElementsByTag("table");
	Element firstTable = table.get(0);
	Elements thingsLevel = firstTable.getElementsByTag("a");
	// for every element I have to create the xml
	for (Element secondLevel : thingsLevel) {
	    String linkSecondLevel = secondLevel.attr("href");
	    String contentSecondLevel = secondLevel.text();
	    if (first) { // TODO DELETE
		first = false;
		// second level file
		String fileSecondLevel = linkSecondLevel;
		String document2nd = OPEN_DOC;

		input = new File(T_FOLDER + fileSecondLevel);
		doc = Jsoup.parse(input, "UTF-8");
		Element bodyframe2nd = doc.getElementById("bodyframe");
		// extract the title of 2nd level
		Element titleH1 = bodyframe2nd.getElementsByTag("h1").get(0);
		System.out.println(titleH1.text());
		document2nd += createField("id", titleH1.text());
		// TODO handle the relates links
		// TODO extract also the others contents

		// SUBCLASSES from the table
		Element sonsTableSecondLevel = bodyframe2nd.getElementsByTag(
			"table").get(0);
		Elements linksThirdLevel = sonsTableSecondLevel
			.getElementsByTag("a");

		for (Element link3rdLevel : linksThirdLevel) {

		    String hrefOf3rdLevel = link3rdLevel.attr("href");
		    hrefOf3rdLevel = hrefOf3rdLevel.split("#")[0];
		    if (hrefOf3rdLevel.compareTo(".htm") != 0) {
			// Split the link T1.1.htm#i264

			String contentOf3rdLevel = link3rdLevel.text();
			document2nd += createField("subClass",
				contentOf3rdLevel);
			// System.out.println(hrefOf3rdLevel + " \t "
			// + contentOf3rdLevel);
			String thirdLevelFile = T_FOLDER + hrefOf3rdLevel;

			// third level file
			String thirdLevelXML = extract3rdXML(thirdLevelFile);
			//TODO write thirdlevelXML
			input = new File(thirdLevelFile);
			doc = Jsoup.parse(input, "UTF-8");

			
			Element article = doc.getElementsByTag("article")
				.get(0);
			// System.out.println(article.attr("id") + " ");
		    }
		}
		// CLOSE THE DOCU AND AFTER I SHOULD PRINT IN A FILE
		document2nd += CLOSE_DOC;
		System.out.println(document2nd);

	    } else {
		return "END";
	    }
	}
	return null;
    }

    private static String extract3rdXML(String thirdLevelFile) {
	//All or SUB ALL
	return null;
    }

    public static String parserOWL(String fnameInput, String fnameOut)
	    throws IOException {

	FileInputStream fstream = new FileInputStream(fnameInput);
	DataInputStream in = new DataInputStream(fstream);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	String strLine;
	int count = 0;
	String obj = "";
	PrintWriter writer = getPrinter(fnameOut);
	writer.append("<add>\n\n<doc>\n");
	while ((strLine = br.readLine()) != null) {

	    if (strLine.contains("<owl:Class")) {
		// System.out.println((count++) + " " + strLine);
		String whatever = strLine.split("\"")[1];
		String[] www = whatever.split(";");
		if (www.length > 1) {
		    // System.out.println((count++) + " " + www[1]);
		    obj += createField("id", www[1]);
		    String next = "";
		    // System.out.println(obj);
		    while ((next = br.readLine()) != null
			    && !next.contains("</owl:Class>")) {
			String name = extractName(next);
			String content;
			if (name.contains("rdfs:subClassOf")
				|| name.contains("exclusion")) {
			    content = extractContentInside(next);
			} else {
			    content = extractContent(next);
			}
			if (name != "" || content != "")
			    obj += createField(name, content);

		    }
		    // TODO obj.printInFIle
		    count++;

		    // System.out.println(" "+ obj +"");
		    obj += "</doc>\n\n";

		    writer.append(obj);
		    obj = "\n\n<doc>\n";
		    /*
		     * if(count ==500 || count ==1000 || count ==1500 || count
		     * ==2000 || count ==2500 || count ==3000 || count ==3500 ||
		     * count ==4000 || count ==4500 || count ==5000 || count
		     * ==5500) { writer.append("</add>"); writer.close(); writer
		     * = getPrinter(count+fnameOut); writer.append("<add>\n\n");
		     * 
		     * }
		     */
		}

	    }

	}
	writer.append("</add>");
	writer.close();
	return null;
    }

    private static String extractContent(String str) {
	Pattern pattern = Pattern.compile(".*>(.*?)<.*");
	Matcher m = pattern.matcher(str);
	while (m.find()) {
	    return m.group(1);
	}
	return "";

    }

    private static String extractContentInside(String str) {
	Pattern pattern = Pattern.compile(";(.*?)\"");
	Matcher m = pattern.matcher(str);
	while (m.find()) {
	    return m.group(1);
	}
	return "";
    }

    private static String extractName(String str) {
	Pattern pattern = Pattern.compile("<(.*?)\\s");
	Matcher m = pattern.matcher(str);
	while (m.find()) {
	    return m.group(1);
	}
	return "";
    }

    private static String createField(String name, String content) {

	return "\t<field name=\"" + name + "\">" + content + "</field>\n";
    }

    public static ArrayList<String> importPatientCases(String fname) {

	boolean first = true;
	ArrayList<String> patient = new ArrayList<String>();
	String patientCaseStr = "";
	try {
	    FileInputStream fstream = new FileInputStream(fname);
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;

	    while ((strLine = br.readLine()) != null) {
		// patientCase++;
		if (strLine.compareTo("&&&") == 0)// new case patient
		{
		    if (first) {
			first = false;
		    } else {
			patient.add(patientCaseStr);
			patientCaseStr = "";
		    }
		} else if (strLine.compareTo("") != 0) {
		    patientCaseStr += strLine;
		}
	    }
	} catch (FileNotFoundException excNotFound) {
	    System.out.println(PRTNFILE + fname);
	} catch (IOException excLettura) {
	    System.out.println(PRTNREAD + fname);
	}
	patient.add(patientCaseStr);
	return patient;
    }

    public static String[] extractSentences(String str) {
	return str.split("\\.|!");
    }

    public static String[] extractWords(String el) {

	return el.split("[,|;|:|\"|\'| |(|)|-|_|+|/|>|<|Ç|È|-]");
    }

    public static int[] readStr(String fname) {
	int count = 5;
	int i = 0;
	Hashtable<String, Integer> h = new Hashtable<String, Integer>();
	int vector[] = new int[4432461];
	try {
	    FileInputStream fstream = new FileInputStream(fname);
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    while ((strLine = br.readLine()) != null) {

		if (h.get(strLine) != null) {
		    h.put(strLine, h.get(strLine) + 1);
		} else { // if the first time in the hash
		    h.put(strLine, 1);
		    int j = 0;
		    int number = 0;
		    for (j = 0; j < strLine.length(); j++) {
			switch (strLine.charAt(j)) {
			case 'A':
			    number = 1;
			    break;
			case 'C':
			    number = 2;
			    break;
			case 'G':
			    number = 3;
			    break;
			case 'T':
			    number = 4;
			    break;

			}
			vector[j + i] = number;
		    }
		    vector[j + i] = count;
		    count++;
		    i += strLine.length() + 1;
		}
	    }
	    in.close();
	} catch (FileNotFoundException excNotFound) {
	    System.out.println(PRTNFILE + fname);
	} catch (IOException excLettura) {
	    System.out.println(PRTNREAD + fname);
	}
	// saveObj("./ciao.txt",h.toString());
	return vector;
	// return k;
    }

    public static void writeOn(String fname, int result[]) throws IOException {
	BufferedWriter wrt = new BufferedWriter(new OutputStreamWriter(
		new FileOutputStream(fname)));
	for (int i = 0; i < result.length; i++) {

	    /* VECTOR value */
	    wrt.newLine();
	    wrt.append((Integer.toString(i)));
	    wrt.append(' ');
	    wrt.append('-');
	    wrt.append('>');
	    wrt.append(' ');
	    wrt.append(Integer.toString(result[i]));

	    /* */

	}
	wrt.close();
    }

    public static void generaFile(String char_speciale, String char_sostituto,
	    String url_scheletro_file, String url_nuovo_file) {
	try {
	    BufferedReader rdr = new BufferedReader(new InputStreamReader(
		    new FileInputStream(url_scheletro_file)));
	    BufferedWriter wrt = new BufferedWriter(new OutputStreamWriter(
		    new FileOutputStream(url_nuovo_file)));
	    String line = rdr.readLine();
	    while (line != null) {
		line = line.replaceAll(char_speciale, char_sostituto);
		wrt.append(line);
		wrt.newLine();
		line = rdr.readLine();
	    }
	    wrt.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static PrintWriter getPrinter(String f) {
	PrintWriter write = null;
	try {
	    write = new PrintWriter(new FileOutputStream(f));

	} catch (IOException excScrittura) {
	    System.out.println(PRTNWRITE + f);
	}
	return write;
    }

    public static void saveStr(String f, String str) {
	PrintWriter write = null;
	try {
	    write = new PrintWriter(new FileOutputStream(f));
	    write.append(str);
	} catch (IOException excScrittura) {
	    System.out.println(PRTNWRITE + f);
	} finally {
	    if (write != null) {
		write.close();
	    }
	}
    }

    public static void saveObj(String f, Object obj)

    {
	ObjectOutputStream write = null;
	try {
	    write = new ObjectOutputStream(new BufferedOutputStream(
		    new FileOutputStream(f)));
	    write.writeObject(obj);
	} catch (IOException excScrittura) {
	    System.out.println(PRTNWRITE + f);
	} finally {
	    if (write != null) {
		try {
		    write.close();
		} catch (IOException excChiusura) {
		    System.out.println(PRTNEND + f);
		}
	    }
	}
    }

    public static void delete(File f) {
	f.delete();
    }

}
