package ntnu.tdt4215.project;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IOFileTxt {

    private final static String PRTNFILE = "File not found";
    private final static String PRTNREAD = "File not read";
    private final static String PRTNWRITE = "Writing nt done";
    private final static String FINISHED = "END";

    private final static String DOCU_FOLDER = "./docu/";
    private final static String T_FOLDER = DOCU_FOLDER + "NLH/T/";
    private final static String OPEN_DOC = "\n<doc>\n";
    private final static String CLOSE_DOC = "\n</doc>\n";

    private static File inputF;

    private final static String[] CLASSES_HANDLED = { "seksjon2", "seksjon3",
	    "seksjon4", "seksjon8" };// , "seksjon8" };
    private final static String[] TAGS_SKIPED = { "br", "input", "img", "tr",
	    "hr" };

    private final static String H1 = "h1";
    private final static String H2 = "h2";
    private final static String H3 = "h3";
    private final static String H4 = "h4";
    private final static String H5 = "h5";
    private final static String[] TAG = { H2, H3, H4, H5 };

    private final static String[] CLASS_NO_HANDLE = { "", "revidert" };
    private final static String DIV_SUB8 = "sub8";
    private final static String TAG_ARTICLE = "article";
    private final static String END_ARTICLE = "/article";
    private final static String CLASS_TEXT = "defa";
    private final static String CLASS_LINK = "tonea";
    private static final String PRTNEND = null;

    public static void test(String name) throws IOException {
	ArrayList<Chapter> parse = parseHTML(name, "");
	for (Chapter p : parse) {
	    System.out.println(p.toString());
	}
	// for (int i = 0; i < parse.size(); i++) {
	// System.out.println(parse.get(i).toString() + "\n\n");
	// }
    }

    // this function parse a html of the 3rd level
    public static ArrayList<Chapter> parseHTML(String fnameInput,
	    String fnameOut) throws IOException {
	// opening the input file
	inputF = new File(fnameInput);
	Document mainDoc = Jsoup.parse(inputF, "UTF-8");
	// I have the article
	Element article = mainDoc.getElementsByTag(TAG_ARTICLE).get(0);
	Elements articleChildren = article.children();
	return extractSubSeksjon(articleChildren, 0, 0, "",
		new ArrayList<Chapter>());
	// return fnameInput;
    }

    // EXtract a subseksjon
    private static ArrayList<Chapter> extractSubSeksjon(
	    Elements seksjonChildren, int indexClass, int indexH,
	    String useless, ArrayList<Chapter> chapters) {
	// outOfBound condition
	if (indexClass >= CLASSES_HANDLED.length || indexH >= TAG.length) {
	    return chapters;
	}
	for (int j = 0; j < seksjonChildren.size(); j++) {
	    // if they are in the section 3
	    if (seksjonChildren.get(j).className()
		    .compareToIgnoreCase(CLASSES_HANDLED[indexClass]) == 0) {
		// EXtract the chapterTitle and split it into CODE + CONTENT
		String[] currentTitle = CharacterChecker
			.splitTitle(extractTitle(seksjonChildren.get(j),
				TAG[indexH]));
		// create a chapter with title code and the content
		chapters.add(new Chapter(currentTitle[1], currentTitle[0],
			extractContentSeksjon(seksjonChildren.get(j),
				indexClass)));
		// extract the subsession
		extractSubSeksjon(seksjonChildren.get(j).children(),
			indexClass + 1, indexH + 1, useless + "\t", chapters);
		// System.out.println(useless + seksjonChildren.get(j).tagName()
				// + " "
				// + extractTitle(seksjonChildren.get(j), TAG[indexH])
				// + " " + seksjonChildren.get(j).className());

				// System.out.println(useless + " "
				// + extractTitle(seksjonChildren.get(j), TAG[indexH])
				// + " " + seksjonChildren.get(j).className());
	    }
	}
	return chapters;

    }

    // this method extracts a content of a seksjon de
    private static ArrayList<Info> extractContentSeksjon(Element element,
	    int indexClass) {
	ArrayList<Info> fields = new ArrayList<Info>();
	Elements children = element.children();
	Element e;
	switch (indexClass) {
	// seksjon 2
	case 0:
	    for (int i = 0; i < children.size(); i++) {
		e = children.get(i);
		if (isBreak2(e)) {
		    if (iHaveToTakeIt2(e)) {
			// goDeeplyWorks(e);
			fields = goDeeplyStart(e, fields);
		    }
		}
	    }
	    break;
	 // seksjon 3
	case 1:
	    for (int i = 0; i < children.size(); i++) {
		e = children.get(i);
		// cindition to EXIT
		if (isBreak3(e)) {
		    // FILTERING
		    if (iHaveToTakeIt3(e)) {
			// System.out.println(i + " " + e.text());
			// goDeeplyWorks(e);
			fields = goDeeplyStart(e, fields);
		    }
		}
	    }
	    break;
	 // seksjon 4
	case 2:
	    for (int i = 0; i < children.size(); i++) {
		e = children.get(i);
		// cindition to EXIT
		if (isBreak4(e)) {
		    // FILTERING
		    if (iHaveToTakeIt4(e)) {
			// goDeeplyWorks(e);
			fields = goDeeplyStart(e, fields);
		    }
		}
	    }
	    break;

	case 3: // seksjon 8
	    for (int i = 0; i < children.size(); i++) {
		e = children.get(i);
		// cindition to EXIT
		if (isBreak8(e)) {
		    // FILTERING
		    if (iHaveToTakeIt8(e)) {
			// goDeeplyWorks(e);
			fields = goDeeplyStart(e, fields);
		    }
		}
	    }
	    break;
	}
	return fields;
    }

    private static ArrayList<Info> goDeeplyStart(Element child,
	    ArrayList<Info> info) {
	// goo down in the document
	Info content;
	String title = "";
	String strContent = "";
	// System.out.println(i + "\t " + child.id() + " " + child.tagName()
	// + " : " + child.text());
	if (iHaveToGo(child)) {
	    goDeeply(child, info);
	} else // is a son with a content
	if (CharacterChecker.isStringUseful(child.text())
		&& !inVector(TAGS_SKIPED, child.tagName())
		&& child.text() != null) {

	    if (child.tagName().compareTo("h5") == 0) {
		title = child.text();

	    } else {
		strContent += child.text();
	    }
	}
	// System.out.println(title + "\n\t" + strContent);
	if (!title.isEmpty() | !strContent.isEmpty()) {
	    info.add(new Info(title, strContent));
	}
	return info;
    }

    private static ArrayList<Info> goDeeply(Element father, ArrayList<Info> info) {
	String title = "";
	String strContent = "";
	for (int i = 0; i < father.children().size(); i++) {

	    Element child = father.child(i);
	    // System.out.println(i + "\t " + child.id() + " " + child.tagName()
	    // + " : " + child.text());
	    if (iHaveToGo(child)) {
		goDeeply(child, info);
	    } else // is a son with a content
	    if (CharacterChecker.isStringUseful(child.text())
		    && !inVector(TAGS_SKIPED, child.tagName())
		    && child.text() != null) {

		if (child.tagName().compareTo("h5") == 0) {
		    title = child.text();

		} else {
		    // TODO FILTERS FOR LINKS
		    strContent += child.text();
		}
	    }
	}
	info.add(new Info(title, strContent));
	return info;
    }

    private static boolean iHaveToGo(Element child) {
	return (child.tagName().compareTo("div") == 0 || child.tagName()
		.compareTo("ul") == 0);
    }

    private static boolean iHaveToTakeIt8(Element e) {
	return (e.className().compareTo("revidert") != 0
		&& e.text().compareTo("") != 0
		&& !inVector(TAGS_SKIPED, e.tagName()) && e.tagName()
		.compareTo(H5) != 0);
    }

    private static boolean isBreak8(Element e) {
	return (e.className().compareTo("seksjon3") != 0)
		&& (e.className().compareTo("seksjon4") != 0);
    }

    private static boolean iHaveToTakeIt2(Element e) {
	return (e.className().compareTo("revidert") != 0 && !e.text().isEmpty()
		&& !inVector(TAG, e.tagName()) && !inVector(TAGS_SKIPED,
		    e.tagName()));
    }

    private static boolean isBreak2(Element e) {
	return (e.className().compareTo("seksjon3") != 0)
		&& (e.className().compareTo("seksjon4") != 0);
    }

    private static boolean iHaveToTakeIt4(Element e) {
	return (e.className().compareTo("revidert") != 0
		&& e.text().compareTo("") != 0 && !inVector(TAG, e.tagName()) && !inVector(
		    TAGS_SKIPED, e.tagName()));
    }

    private static boolean isBreak4(Element e) {
	return (e.className().compareTo("seksjon8") != 0 || e.className()
		.compareTo("seksjon4") != 0);
    }

    private static boolean iHaveToTakeIt3(Element e) {
	return (e.className().compareTo("revidert") != 0
		&& e.text().compareTo("") != 0 && !inVector(TAG, e.tagName()) && !inVector(
		    TAGS_SKIPED, e.tagName()));
    }

    private static boolean isBreak3(Element e) {
	//
	return (e.className().compareTo("seksjon4") != 0
		&& e.className().compareTo("seksjon3") != 0 && e.className()
		.compareTo("seksjon8") != 0);
    }

    public static boolean inVector(String[] vector, String name) {
	for (String obj : vector) {
	    if (name.compareToIgnoreCase(obj) == 0) {
		return true;
	    }
	}
	return false;
    }

    private static String extractTitle(Element element, String h32) {

	if (element.getElementsByTag(h32).size() > 0) {
	    return element.getElementsByTag(h32).get(0).text();
	}
	return "";
    }

    public static String[] splitTitle(String mainTitle) {

	String[] title = new String[2];
	int i = 1;
	while (i < mainTitle.length()) {
	    if (Character.isWhitespace(mainTitle.charAt(i))) {
		title[0] = mainTitle.substring(0, i);
		title[1] = mainTitle.substring(i + 1);
		return title;
	    }
	    i++;
	}
	return title;
    }

    public static Handbook mainParserHtml(String fnameInput, String fnameOut)
	    throws IOException {

	Handbook handBook = new Handbook();

	boolean first = true; // TODO DELETE
	File input = new File(fnameInput);
	Document doc = Jsoup.parse(input, "UTF-8");
	Elements table = doc.getElementsByTag("table");
	Element firstTable = table.get(0);
	Elements thingsLevel = firstTable.getElementsByTag("a");
	// for every element I have to create the xml
	for (Element secondLevel : thingsLevel) {
	    String linkSecondLevel = secondLevel.attr("href");
	    // String contentSecondLevel = secondLevel.text();
	    if (first) { // TODO DELETE
		first = false;
		// second level file
		String fileSecondLevel = linkSecondLevel;
		input = new File(T_FOLDER + fileSecondLevel);
		doc = Jsoup.parse(input, "UTF-8");
		Element bodyframe2nd = doc.getElementById("bodyframe");

		// SUBCLASSES from the table
		Element sonsTableSecondLevel = bodyframe2nd.getElementsByTag(
			"table").get(0);
		Elements linksThirdLevel = sonsTableSecondLevel
			.getElementsByTag("a");
		for (Element link3rdLevel : linksThirdLevel) {
		    String hrefOf3rdLevel = link3rdLevel.attr("href");
		    // Split the link T1.1.htm#i264
		    hrefOf3rdLevel = hrefOf3rdLevel.split("#")[0];
		    if (hrefOf3rdLevel.compareTo(".htm") != 0) {
			handBook.addBook(parseHTML(T_FOLDER + hrefOf3rdLevel,
				hrefOf3rdLevel));
		    }
		}
		// CLOSE THE DOCU AND AFTER I SHOULD PRINT IN A FILE

	    } else {
		return handBook;
	    }
	}
	return handBook;
    }

    // parser OWL
    public static String parserOWL(String fnameInput, String fnameOut)
	    throws IOException {

	FileInputStream fstream = new FileInputStream(fnameInput);
	DataInputStream in = new DataInputStream(fstream);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	String strLine;
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
			    && !next.contains("</owl:Class>")
			    && !next.contains("rdfs:seeAlso")) {
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
		    // System.out.println(" "+ obj +"");
		    obj += "</doc>\n\n";

		    writer.append(obj);
		    obj = "\n\n<doc>\n";
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

	return el.split("[,|;|:|\"|\'| |(|)|-|_|+|/|>|<|�|�|-]");
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
