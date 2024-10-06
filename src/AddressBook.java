import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private HashMap<String, String> contacts;
    private String filePath;

    public AddressBook(String filePath) {
        this.contacts = new HashMap<>();
        this.filePath = filePath;
        load(); // Cargar contactos al inicio
    }

    // Metodo para cargar los contactos desde el archivo de texto
    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    contacts.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Metodo para guardar los contactos en el archivo de texto
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + " : " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    // Metodo para listar los contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Metodo para crear un nuevo contacto
    public void create(String number, String name) {
        if (contacts.containsKey(number)) {
            System.out.println("Este número ya existe en la agenda.");
        } else {
            contacts.put(number, name);
            System.out.println("Contacto añadido: " + number + " : " + name);
        }
    }

    // Metodo para borrar un contacto
    public void delete(String number) {
        if (contacts.containsKey(number)) {
            contacts.remove(number);
            System.out.println("Contacto borrado: " + number);
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    // Menú interactivo
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("\n--- Agenda Telefónica ---");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear nuevo contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Guardar y salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (option) {
                case 1:
                    list();
                    break;
                case 2:
                    System.out.print("Ingrese el número: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    create(number, name);
                    break;
                case 3:
                    System.out.print("Ingrese el número del contacto a borrar: ");
                    String numberToDelete = scanner.nextLine();
                    delete(numberToDelete);
                    break;
                case 4:
                    save();
                    System.out.println("Cambios guardados. Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (option != 4);
    }

    public static void main(String[] args) {
        // Especifica la ruta del archivo
        AddressBook addressBook = new AddressBook("contacts.txt");
        addressBook.menu();
    }
}