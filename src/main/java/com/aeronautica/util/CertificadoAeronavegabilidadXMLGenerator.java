package com.aeronautica.util;

import com.aeronautica.model.Aeronave;
import com.aeronautica.model.Revision;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Generador de Certificados de Aeronavegabilidad en formato XML.
 */
public class CertificadoAeronavegabilidadXMLGenerator {

    /**
     * Genera un certificado de aeronavegabilidad en XML para una aeronave.
     * 
     * @param aeronave La aeronave para la cual generar el certificado
     * @param ultimaRevision La última revisión realizada
     * @param outputPath Ruta donde guardar el archivo XML
     * @return true si se generó correctamente, false en caso contrario
     */
    public static boolean generarCertificado(Aeronave aeronave, Revision ultimaRevision, String outputPath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Elemento raíz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("CertificadoAeronavegabilidad");
            doc.appendChild(rootElement);

            // Información general
            Element info = doc.createElement("InformacionGeneral");
            rootElement.appendChild(info);

            addElement(doc, info, "NumeroSerie", aeronave.getId().toString());
            addElement(doc, info, "Matricula", aeronave.getMatricula());
            addElement(doc, info, "Modelo", aeronave.getModelo());
            addElement(doc, info, "FechaEmision", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            addElement(doc, info, "ValidoHasta", LocalDateTime.now().plusYears(1).format(DateTimeFormatter.ISO_DATE_TIME));

            // Información de la aeronave
            Element detalles = doc.createElement("DetallesAeronave");
            rootElement.appendChild(detalles);

            addElement(doc, detalles, "HorasVueloTotales", String.valueOf(aeronave.getHorasVuelo()));
            addElement(doc, detalles, "Estado", "APTA PARA VOLAR");
            
            // Información de la última revisión
            if (ultimaRevision != null) {
                Element revision = doc.createElement("UltimaRevision");
                rootElement.appendChild(revision);

                addElement(doc, revision, "Fecha", ultimaRevision.getFechaRevision().format(DateTimeFormatter.ISO_DATE_TIME));
                addElement(doc, revision, "Tipo", ultimaRevision.getTipoRevision());
                addElement(doc, revision, "Mecanico", ultimaRevision.getMecanico().getNombre());
                addElement(doc, revision, "Certificacion", ultimaRevision.getMecanico().getCertificacion());
                addElement(doc, revision, "HorasEnRevision", String.valueOf(ultimaRevision.getHorasAcumuladas()));
                addElement(doc, revision, "FirmadoJefe", String.valueOf(ultimaRevision.isFirmadoJefe()));
                addElement(doc, revision, "Observaciones", ultimaRevision.getObservaciones());
            }

            // Certificación
            Element certificacion = doc.createElement("Certificacion");
            rootElement.appendChild(certificacion);
            
            addElement(doc, certificacion, "AutoridadEmisora", "Dirección General de Aviación Civil");
            addElement(doc, certificacion, "Responsable", "Inspector Jefe de Mantenimiento");
            addElement(doc, certificacion, "Firma", "CERTIFICADO_DIGITAL_" + aeronave.getMatricula());

            // Escribir el contenido en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath));

            transformer.transform(source, result);

            System.out.println("✓ Certificado de Aeronavegabilidad generado: " + outputPath);
            return true;

        } catch (Exception e) {
            System.err.println("✗ Error al generar certificado XML: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método auxiliar para agregar elementos al documento XML.
     */
    private static void addElement(Document doc, Element parent, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value != null ? value : ""));
        parent.appendChild(element);
    }

    /**
     * Genera el nombre de archivo estándar para el certificado.
     */
    public static String generarNombreArchivo(Aeronave aeronave) {
        return "certificado_" + aeronave.getMatricula().replace("-", "_") + "_" + 
               LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xml";
    }
}
