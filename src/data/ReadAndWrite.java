package data;

import model.Contact;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ReadAndWrite {
    private static final String FILE_NAME = "data/contacts.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String[] HEADERS = {"Name", "Phone", "Address", "Email", "Group", "Birthdate", "Gender"};

    public List<Contact> readFromFile() {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("File not found: " + FILE_NAME);
            return contacts;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // Dùng -1 để chắc chắc tất cả trường dữ liệu được ghi nhận dù trống

                if (data.length == 7) {
                    try {
                        LocalDate birthdate = LocalDate.parse(data[5], DATE_FORMATTER);
                        Contact contact = new Contact(data[0], data[1], data[2], data[3], data[4], birthdate, data[6]);
                        contacts.add(contact);
                    } catch (DateTimeParseException e) {
                        System.out.println("Date format is incorrect: " + data[5]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Data format is incorrect in file.");
                    }
                } else {
                    System.out.println("Incorrect data length or format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return contacts;
    }

    public void writeToFile(List<Contact> contacts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Write the header
            bw.write(String.join(",", HEADERS));
            bw.newLine();

            for (Contact contact : contacts) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s",
                        contact.getName(),
                        contact.getPhone(),
                        contact.getAddress(),
                        contact.getEmail(),
                        contact.getGroup(),
                        contact.getBirthdate().format(DATE_FORMATTER),
                        contact.getGender());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
