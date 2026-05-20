package com.inventory.inventory.service;

import com.inventory.inventory.model.Item;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final String FILE_PATH = "items.txt";

    // ---------------- SAVE ----------------
    public void saveItem(Item item) throws IOException {

        FileWriter writer = new FileWriter(FILE_PATH, true);

        writer.write(
                item.getId() + "," +
                        item.getName() + "," +
                        item.getQuantity() + "," +
                        item.getPrice() + "\n"
        );

        writer.close();
    }

    // ---------------- GET ALL ----------------
    public List<Item> getAllItems() throws IOException {

        List<Item> items = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.createNewFile();
            return items;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            Item item = new Item(
                    data[0],
                    data[1],
                    Integer.parseInt(data[2]),
                    Double.parseDouble(data[3])
            );

            items.add(item);
        }

        reader.close();

        return items;
    }

    // ---------------- UPDATE ----------------
    public void updateItem(Item updatedItem) throws IOException {

        List<Item> items = getAllItems();

        for (Item item : items) {

            if (item.getId().equals(updatedItem.getId())) {

                item.setName(updatedItem.getName());
                item.setQuantity(updatedItem.getQuantity());
                item.setPrice(updatedItem.getPrice());
            }
        }

        rewriteFile(items);
    }

    // ---------------- DELETE (FIXED STRING ID) ----------------
    public void deleteItem(String id) throws IOException {

        List<Item> items = getAllItems();

        items.removeIf(item -> item.getId().equals(id));

        rewriteFile(items);
    }

    // ---------------- FIND BY ID ----------------
    public Item findById(String id) throws IOException {

        List<Item> items = getAllItems();

        for (Item item : items) {

            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    // ---------------- REWRITE FILE ----------------
    private void rewriteFile(List<Item> items) throws IOException {

        FileWriter writer = new FileWriter(FILE_PATH);

        for (Item item : items) {

            writer.write(
                    item.getId() + "," +
                            item.getName() + "," +
                            item.getQuantity() + "," +
                            item.getPrice() + "\n"
            );
        }

        writer.close();
    }

    // ---------------- PDF REPORT ----------------
    public String generatePdfReport() throws Exception {

        List<Item> items = getAllItems();

        String filePath = "inventory-report.pdf";

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        document.open();

        document.add(new Paragraph("INVENTORY REPORT"));
        document.add(new Paragraph("=================================\n"));

        for (Item i : items) {

            document.add(new Paragraph(
                    "ID: " + i.getId() +
                            " | Name: " + i.getName() +
                            " | Qty: " + i.getQuantity() +
                            " | Price: " + i.getPrice()
            ));
        }

        document.close();

        return filePath;
    }
}