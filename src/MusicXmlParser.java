package GiveNotes.src;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.nio.file.Path;

public class MusicXmlParser {

  public static void main(String[] args) {
    String cwd = Path.of("").toAbsolutePath().toString(); // System.getProperty("user.dir");

    System.out.println(cwd);

    String inputPath = cwd + "/doc/mxlFiles/extracted/test.xml"; // Path to the extracted XML file

    try {
      // Parse the XML file
      parseXmlFile(inputPath);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Method to parse the XML file and extract relevant data
  public static void parseXmlFile(String xmlFilePath) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

      // Disable DTD loading and validation
      dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      dbFactory.setFeature("http://xml.org/sax/features/validation", false);

      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(new File(xmlFilePath));

      // Normalize the document (optional, but helps clean up the XML structure)
      doc.getDocumentElement().normalize();

      // Extract instrument names
      NodeList instrumentList = doc.getElementsByTagName("instrument-name");
      for (int i = 0; i < instrumentList.getLength(); i++) {
        Node node = instrumentList.item(i);
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String instrumentName = element.getTextContent();
          System.out.println("Instrument: " + instrumentName);
        }
      }

      // Extract part name, composer etc
      NodeList partNameList = doc.getElementsByTagName("part-name");
      for (int i = 0; i < partNameList.getLength(); i++) {
        Node nPartName = partNameList.item(i);
        if (nPartName != null && nPartName.getNodeType() == Node.ELEMENT_NODE) {
          Element ePartName = (Element) nPartName;
          System.out.println("Part Name: " + ePartName.getTextContent());
        }
      }

      // // Extract note data: step (note), octave, and duration
      // NodeList noteList = doc.getElementsByTagName("note");
      // for (int i = 0; i < noteList.getLength(); i++) {
      //   node = noteList.item(i);
      //   if (node.getNodeType() == Node.ELEMENT_NODE) {
      //     Element noteElement = (Element) node;

      //     // Extract note/rest info
      //     NodeList restNode = noteElement.getElementsByTagName("rest");
      //     if (restNode.getLength() > 0) {
      //       System.out.println("Note: Rest");
      //     } else {
      //       // Extract pitch information
      //       Element pitchElement = (Element) noteElement.getElementsByTagName("pitch").item(0);
      //       String step = pitchElement.getElementsByTagName("step").item(0).getTextContent();
      //       String octave = pitchElement.getElementsByTagName("octave").item(0).getTextContent();

      //       System.out.println("Note: " + step + octave);
      //     }

      //     // Extract duration (note length in MusicXML divisions)
      //     String duration = noteElement.getElementsByTagName("duration").item(0).getTextContent();
      //     String noteType = noteElement.getElementsByTagName("type").item(0).getTextContent();

      //     System.out.println("Duration: " + duration + " (" + noteType + ")");
      //     System.out.println("------");
      //   }
      // }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
