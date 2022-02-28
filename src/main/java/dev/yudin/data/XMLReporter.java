package dev.yudin.data;

import dev.yudin.entities.Equipment;
import dev.yudin.entities.WellDTO;
import dev.yudin.exceptions.XMLDataException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

@Log4j
@Component
public class XMLReporter implements Reporter {
    private static final String XML_ERROR = "Error during XML file creating";

    @Value("${xml.name}")
    private String xmlFileName;

    @Override
    public void create(List<WellDTO> input, String fileName) {
        log.debug("Call method create()");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement(fileName);
            document.appendChild(rootElement);

            for (WellDTO wellDTO : input) {
                Element well = document.createElement("well");
                rootElement.appendChild(well);

                Attr attribute1 = document.createAttribute("name");
                attribute1.setValue(wellDTO.getWellName());

                Attr attribute2 = document.createAttribute("id");
                attribute2.setValue(String.valueOf(wellDTO.getWellId()));

                well.setAttributeNode(attribute1);
                well.setAttributeNode(attribute2);

                for (Equipment equipment : wellDTO.getEquipments()) {
                    Element equipmentElement = document.createElement("equipment");

                    Attr attribute3 = document.createAttribute("name");
                    attribute3.setValue(equipment.getName());
                    Attr attribute4 = document.createAttribute("id");
                    attribute4.setValue(String.valueOf(equipment.getId()));

                    equipmentElement.setAttributeNode(attribute3);
                    equipmentElement.setAttributeNode(attribute4);

                    well.appendChild(equipmentElement);
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(xmlFileName));
                transformer.transform(source, result);
            }
            log.debug("XML file created in root of the project");
        } catch (TransformerException | ParserConfigurationException ex) {
            log.error(XML_ERROR, ex);
            throw new XMLDataException(XML_ERROR, ex);
        }
    }
}
