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

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
    log.info("CheckSum: {}", getMD5Checksum("HELP.md"));
    System.exit(0);
  }


  public static String getMD5Checksum(String filename) {
    byte[] b = createChecksum(filename);
    if (Objects.isNull(b)) {
      return null;
    }

    String result = "";
    for (int i = 0; i < b.length; i++) {
      result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
    }
    return result;
  }

  public static byte[] createChecksum(String filename) {
    try {
      InputStream fis = new FileInputStream(filename);

      byte[] buffer = new byte[1024];
      MessageDigest complete = MessageDigest.getInstance("MD5");
      int numRead;
      do {
        numRead = fis.read(buffer);
        if (numRead > 0) {
          complete.update(buffer, 0, numRead);
        }
      } while (numRead != -1);
      fis.close();
      return complete.digest();
    } catch (Exception exception) {
      log.error("Error: {}", exception.getMessage());
      return null;
    }
  }
}
