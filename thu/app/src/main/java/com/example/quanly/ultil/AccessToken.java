package com.example.quanly.ultil;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AccessToken {
    private static final String firebaseMessagingScope =
            "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() {
        try {
            String jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"banhang-f86d4\",\n" +
                    "  \"private_key_id\": \"5eb4eaa965910bb4e802da24fdbd30bbe13a4a3a\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDcIXbRNSC7DLUE\\n5L7Acma5ZSET7oCjvvFrSg2cmJenJSnbenN88WCIdkDHmMAh6IL+g5GR8Sl6N2oW\\nVN34D6ruAAvfzH+WovdVSRnX828saNUPG0IgzSk0Q/rB6PKwrMIO7QBfJmY/6kln\\niGtkTnEDJvq2VZSSboQILDWENhLNpDVng67qRT/cCU90gD3ZjD/mC3+YUQoq2IQl\\nIVMykxuEnlckZOiNyYjiK3I+DtZq/xmqNjVvW/+OJEVZGYHyshJqGIOIZyrh4p/P\\nEGVXGKg2Yr2XMUbLfiDhwc8Ps37He2eARrFdd+Kk4wD0UMoXFIclYcci8448XGED\\n20ASz6FFAgMBAAECggEAUJWhkQmxbzhCJkX0/K+Eonb+inTju84BRAUKgZqs3b/x\\nmEq173XPoHUl1su4/lDHb5PLnREbXYXevMkZrx9RNCppLmcHCCalg5+sqc0QlEHW\\nSV7xsN9aJ6uBJy53dazwp5UkbzC3+sryT0f2BHTg00YYPtqKdqalh/ZjKwC+lm/K\\nDBvCjnVOZ/2AC0brppaEuOJkXVPR+fcmiApjIQVe/bsK2SQSOiptMCXvElf1K82Q\\nDrJX44pjxZeNIrRfzSHXPGsfrF4O96xDKYaCyNH9QAnqHVBfe+rCutZJJmp2seyK\\n2Ayk/kHFsUSt7MA88rxa+0T4yZAzV0um6k8MUeqZqwKBgQD8FvWUoiOxa1QrmKNR\\neWZ1F1n+EASQQoqPgmUF7F0CQkkNLXWu/tC3InWgr0tCLmkb8P7YwgLGsJwXHwa0\\nfBcCo8OZA4uj5u0rcQNCFszcLJ29RXc14nLS4oWSoVVGZbn6xRZCKBCtlqzM2lZ6\\ncX4VGHjBurIPjnlBpsEXR5w9UwKBgQDfi5jCu9fFj4T9RCalbtrjQSH4RrCE4QKJ\\ndFCMxQ0IiDgI+WqMx7R/frRe0hw1Jkg8r+F0Dq0P07ePbTII8Ld+kumN7n4SafSm\\nlFXafb6Hvd5wHcyaT4EnIP230m8spUPTTMWEHk852JrS4j/oL7wVS9b19h6af0Sh\\n04iqnFu8BwKBgQCwLLd6JYo+TtznDuhsYyRYhnZNGRKnYumE+8LwTcx+bKay/+cz\\n4j8pDXsZTgiReu/YKCdigsv/4aNkgTllXyeRn7Ntt02awSNU7ckyIc8PvFqM0QRL\\n8zL98HMbE4cOCL/d4mj42Rk2x4uCKsCvF6vewFh8Q6Z3OsHBQPHG/DPTowKBgQCN\\nXjWT6MDSgimDFOcvtYQdNSJrZSDaV4WIMZODAlZ0r9hn7HsyXlTKT/F3CX6nKfeC\\n0WNNRr6xBJ3PySlcOAaCuBuVE16M/I6oDaShkOW96BfUZTPv3hl6CkYJSr3dyQG0\\njZdXLufBWDbI6CWsCwvxl81wLF48LAHWKPUDl+/3XwKBgQDN4NraYAGvjtNgy+x2\\n/psOJK9h/mRAfC4RYlM/ItRFRFnBryq4e/kKb+W9Sff7EClj+YDrf+q60KCvm4OL\\nzfHCS58w2QEOB1lUiyZg1h+gGbnOTJ4nVr/yI59XZF/nMxWrvvl3SUcArWozAWk1\\nd939oMqVdLzk3uUfavHciZdtMg==\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-b2j57@banhang-f86d4.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"108716567173603610899\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-b2j57%40banhang-f86d4.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}";
            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(firebaseMessagingScope);
            googleCredentials.refresh();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception e) {
            Log.e("AccessToken", "getAccessToken: " + e.getLocalizedMessage());
            return null;
        }
    }

}
