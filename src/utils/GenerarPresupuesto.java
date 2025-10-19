package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import Modelos.Presupuestos;
import Modelos.Clientes;
import Modelos.Empleados;
import Modelos.DetalleItemPres;
import Modelos.Items;

import dao.ClientesDAO;
import dao.EmpleadosDAO;
import dao.DetalleItemDAO;
import dao.ItemsDAO;
import javax.swing.JOptionPane;

public class GenerarPresupuesto {
    
    public void generarFacturaDesdePresupuesto(Presupuestos presupuesto) {
        Clientes cliente = new ClientesDAO().obtenerClientePorId(presupuesto.getClientes_idClientes());
        Empleados empleado = new EmpleadosDAO().obtenerEmpleadoPorId(presupuesto.getEmpleados_idEmpleados());
        List<DetalleItemPres> detalles = new DetalleItemDAO().obtenerDetallePorPresupuesto(presupuesto.getIdPresupuesto());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Presupuesto PDF");
        fileChooser.setSelectedFile(new File("Presupuesto_" + presupuesto.getIdPresupuesto() + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) return;

        File fileToSave = fileChooser.getSelectedFile();
        Document documento = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(fileToSave));
            documento.open();

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 11);
            Font fontMini = new Font(Font.FontFamily.HELVETICA, 10);

            // Encabezado - Empresa ficticia
            Paragraph empresa = new Paragraph("Vivero Jardines S.A. de C.V.", fontTitulo);
            empresa.setAlignment(Element.ALIGN_CENTER);
            documento.add(empresa);

            Paragraph direccionEmpresa = new Paragraph("La casa del Jarvis, Ciudad Verde, México\nTel: 555-1234-567\nCorreo: contacto@viverojardines.com", fontMini);
            direccionEmpresa.setAlignment(Element.ALIGN_CENTER);
            documento.add(direccionEmpresa);
            documento.add(new Paragraph(" "));

            // Información del presupuesto
            PdfPTable tablaInfo = new PdfPTable(2);
            tablaInfo.setWidthPercentage(100);
            tablaInfo.setSpacingBefore(10);
            tablaInfo.setSpacingAfter(10);

            tablaInfo.addCell(getCell("Presupuesto #: " + presupuesto.getIdPresupuesto(), PdfPCell.ALIGN_LEFT, fontNormal));
            tablaInfo.addCell(getCell("Fecha: " + presupuesto.getFechaMovimiento(), PdfPCell.ALIGN_RIGHT, fontNormal));
            tablaInfo.addCell(getCell("Estatus: " + presupuesto.getEstatus(), PdfPCell.ALIGN_LEFT, fontNormal));
            tablaInfo.addCell(getCell("", PdfPCell.ALIGN_RIGHT, fontNormal));

            documento.add(tablaInfo);

            // Cliente y Empleado
            Paragraph datosCliente = new Paragraph("Cliente:\n" +
                cliente.getNombreClie() + " " + cliente.getApellidosClie() + "\n" +
                cliente.getDireccionClie() + "\n" +
                "RFC: " + cliente.getRFC_Clie(), fontNormal);
            datosCliente.setSpacingAfter(10);
            documento.add(datosCliente);

            Paragraph datosEmpleado = new Paragraph("Empleado que atendió:\n" +
                empleado.getNombreEmp() + " " + empleado.getApellidoEmp(), fontNormal);
            datosEmpleado.setSpacingAfter(20);
            documento.add(datosEmpleado);

            // Tabla de productos
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new int[]{4, 2, 1, 2, 2});

            tabla.addCell(getCeldaHeader("Producto", fontSubtitulo));
            tabla.addCell(getCeldaHeader("Descripción", fontSubtitulo));
            tabla.addCell(getCeldaHeader("Cantidad", fontSubtitulo));
            tabla.addCell(getCeldaHeader("Precio Unitario", fontSubtitulo));
            tabla.addCell(getCeldaHeader("Subtotal", fontSubtitulo));

            double subtotal = 0;
            ItemsDAO itemsDAO = new ItemsDAO();

            for (DetalleItemPres detalle : detalles) {
                Items item = itemsDAO.obtenerItemPorId(detalle.getItems_idItems());

                double totalItem = detalle.getCantidad() * detalle.getPrecioUnitario();
                subtotal += totalItem;

                tabla.addCell(new PdfPCell(new Phrase(item.getNombreItem(), fontNormal)));
                tabla.addCell(new PdfPCell(new Phrase(item.getDescripcionItem(), fontMini)));
                tabla.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), fontNormal)));
                tabla.addCell(new PdfPCell(new Phrase(String.format("$%.2f", detalle.getPrecioUnitario()), fontNormal)));
                tabla.addCell(new PdfPCell(new Phrase(String.format("$%.2f", totalItem), fontNormal)));
            }

            documento.add(tabla);
            documento.add(new Paragraph(" "));

            // Totales
            double iva = subtotal * 0.16;
            double total = subtotal + iva;

            Paragraph subtotalParrafo = new Paragraph("Subtotal: " + String.format("$%.2f", subtotal), fontNormal);
            subtotalParrafo.setAlignment(Element.ALIGN_RIGHT);
            documento.add(subtotalParrafo);

            Paragraph ivaParrafo = new Paragraph("IVA (16%): " + String.format("$%.2f", iva), fontNormal);
            ivaParrafo.setAlignment(Element.ALIGN_RIGHT);
            documento.add(ivaParrafo);

            Paragraph totalParrafo = new Paragraph("TOTAL: " + String.format("$%.2f", total), fontSubtitulo);
            totalParrafo.setAlignment(Element.ALIGN_RIGHT);
            documento.add(totalParrafo);

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Gracias por su preferencia.", fontNormal));

            documento.add(new Paragraph("\n\n_____________________________\nFirma del cliente", fontMini));

            documento.close();
            JOptionPane.showMessageDialog(null, "Presupuesto generado con éxito.");

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el presupuesto.");
        }
    }
    
    private static PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private static PdfPCell getCeldaHeader(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
    
}
