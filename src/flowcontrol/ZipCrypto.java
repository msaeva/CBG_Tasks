package flowcontrol;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCrypto {
    private static final String AES_ALGORITHM = "AES";
    private static final int AES_KEY_SIZE = 128;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Path toEncrypt;
            Path newZip;

            Cipher algorithm = Cipher.getInstance(AES_ALGORITHM);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGenerator.init(AES_KEY_SIZE);
            Key key = keyGenerator.generateKey();

            System.out.print("Enter the first path:");
            toEncrypt = Paths.get(scanner.next());

            System.out.print("Enter the second path:");
            newZip = Paths.get(scanner.next());

            System.out.println("Enter the operation (encrypt or decrypt):");
            String option = scanner.next();

            switch (option.toLowerCase()) {
                case "encrypt":
                    encryptFile(algorithm, key, toEncrypt.toFile(), newZip.toFile());
                    break;
                case "decrypt":
                    decryptFile(algorithm, toEncrypt.toFile(), newZip.toFile());
                    break;
                default:
                    System.out.println("Invalid operation");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void encryptFile(Cipher algorithm, Key key, File zipToDecrypt, File zipToSave) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipToDecrypt));
             ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipToSave))) {

            String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
            addKeyEntry(zipOutputStream, base64Key);

            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                processZipEntry(algorithm, key, zipInputStream, entry, zipOutputStream);
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void addKeyEntry(ZipOutputStream zipOutputStream, String base64Key) throws IOException {
        ZipEntry keyEntry = new ZipEntry("AES_key.txt");
        zipOutputStream.putNextEntry(keyEntry);
        zipOutputStream.write(base64Key.getBytes());
        zipOutputStream.closeEntry();
    }

    private static void processZipEntry(Cipher algorithm, Key key, ZipInputStream input,
                                        ZipEntry originalEntry, ZipOutputStream output) {
        try {
            output.putNextEntry(new ZipEntry(originalEntry.getName()));

            if (!originalEntry.isDirectory()) {
                algorithm.init(Cipher.ENCRYPT_MODE, key);

                byte[] beforeEncryption = input.readAllBytes();
                byte[] encrypted = algorithm.doFinal(beforeEncryption);

                output.write(encrypted);
            }

            output.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(Cipher algorithm, File zipToDecrypt, File zipToSave) {
        try (ZipFile inputZip = new ZipFile(zipToDecrypt);
             ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipToSave))) {

            Enumeration<? extends ZipEntry> entries = inputZip.entries();
            if (entries.hasMoreElements()) {
                ZipEntry keyEntry = entries.nextElement();
                Key key = new SecretKeySpec(inputZip.getInputStream(keyEntry).readAllBytes(), AES_ALGORITHM);

                algorithm.init(Cipher.DECRYPT_MODE, key);

                processDecryption(entries, inputZip, algorithm, zipOutputStream);
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void processDecryption(Enumeration<? extends ZipEntry> entries, ZipFile inputZip,
                                          Cipher algorithm, ZipOutputStream zipOutputStream) {
        try {
            while (entries.hasMoreElements()) {
                ZipEntry encryptedEntry = entries.nextElement();
                byte[] encrypted = inputZip.getInputStream(encryptedEntry).readAllBytes();
                byte[] decrypted = algorithm.doFinal(encrypted);

                ZipEntry decryptedEntry = new ZipEntry(encryptedEntry.getName());
                zipOutputStream.putNextEntry(decryptedEntry);
                zipOutputStream.write(decrypted);
                zipOutputStream.closeEntry();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
