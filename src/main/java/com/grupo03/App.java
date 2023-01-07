package com.grupo03;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class App {

  private static final String CHECKSUM_VALID = "0dfd4d695450937bdc9e5c78dad102b7973c7db583830dbc5b0b8dc9afc3aebebad19dbc91b5d62e782ef55089237afc3245c1cd99269e1ba32135e3a4b849b7";

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
    String checkSumValidation = getMD5Checksum("HELP.md");
    log.info("CheckSum validation: {}", checkSumValidation);

    if (!Objects.isNull(checkSumValidation) && !CHECKSUM_VALID.equals(checkSumValidation)) {
      log.error("Exit with checksum");
      System.exit(0);
    }
    log.info("Start to project with checkSumValid");
  }


  public static String getMD5Checksum(String filename) {
    byte[] b = createChecksum(filename);
    if (Objects.isNull(b)) {
      return null;
    }

    StringBuilder result = new StringBuilder();
    for (byte value : b) {
      result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
    }
    return result.toString();
  }

  public static byte[] createChecksum(String filename) {
    try (InputStream fis = new FileInputStream(filename)) {

      byte[] buffer = new byte[1024];
      MessageDigest complete = MessageDigest.getInstance("SHA-512");
      int numRead;
      do {
        numRead = fis.read(buffer);
        if (numRead > 0) {
          complete.update(buffer, 0, numRead);
        }
      } while (numRead != -1);
      return complete.digest();
    } catch (Exception exception) {
      log.error("Error: {}", exception.getMessage());
      return new byte[0];
    }
  }
}
