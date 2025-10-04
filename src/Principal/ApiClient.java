package Principal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Cliente HTTP para comunicación con los microservicios
 * @author David Urrego
 */
public class ApiClient {
    
    private static final String BASE_URL = "http://localhost";
    
    // Puertos de los microservicios
    public static final int ADMINISTRACION_PORT = 8081;
    public static final int PROPIETARIOS_PORT = 8082;
    public static final int INMUEBLES_PORT = 8083;
    public static final int CONTRATOS_PORT = 8084;
    public static final int PAGOS_PORT = 8085;
    public static final int USUARIOS_PORT = 8086;
    public static final int NOTIFICACIONES_PORT = 8087;
    public static final int MANTENIMIENTO_PORT = 8088;
    public static final int REPORTES_PORT = 8089;
    
    /**
     * Realiza una petición POST a un microservicio
     */
    public static JSONObject post(int port, String endpoint, JSONObject jsonBody) throws Exception {
        URL url = new URL(BASE_URL + ":" + port + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        try {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            // Enviar el cuerpo de la petición
            if (jsonBody != null) {
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }
            
            int responseCode = conn.getResponseCode();
            
            // Leer la respuesta
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }
            
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();
            
            JSONObject result = new JSONObject();
            result.put("statusCode", responseCode);
            
            if (response.length() > 0) {
                try {
                    result.put("data", new JSONObject(response.toString()));
                } catch (Exception e) {
                    result.put("data", response.toString());
                }
            }
            
            return result;
            
        } finally {
            conn.disconnect();
        }
    }
    
    /**
     * Realiza una petición GET a un microservicio
     */
    public static JSONObject get(int port, String endpoint) throws Exception {
        URL url = new URL(BASE_URL + ":" + port + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        try {
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            int responseCode = conn.getResponseCode();
            
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }
            
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();
            
            JSONObject result = new JSONObject();
            result.put("statusCode", responseCode);
            
            if (response.length() > 0) {
                String responseStr = response.toString();
                try {
                    // Intentar parsear como JSONObject
                    result.put("data", new JSONObject(responseStr));
                } catch (Exception e1) {
                    try {
                        // Intentar parsear como JSONArray
                        result.put("data", new JSONArray(responseStr));
                    } catch (Exception e2) {
                        // Si falla, guardar como string
                        result.put("data", responseStr);
                    }
                }
            }
            
            return result;
            
        } finally {
            conn.disconnect();
        }
    }
    
    /**
     * Realiza una petición PUT a un microservicio
     */
    public static JSONObject put(int port, String endpoint, JSONObject jsonBody) throws Exception {
        URL url = new URL(BASE_URL + ":" + port + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        try {
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            if (jsonBody != null) {
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }
            
            int responseCode = conn.getResponseCode();
            
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }
            
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();
            
            JSONObject result = new JSONObject();
            result.put("statusCode", responseCode);
            
            if (response.length() > 0) {
                try {
                    result.put("data", new JSONObject(response.toString()));
                } catch (Exception e) {
                    result.put("data", response.toString());
                }
            }
            
            return result;
            
        } finally {
            conn.disconnect();
        }
    }
    
    /**
     * Realiza una petición DELETE a un microservicio
     */
    public static JSONObject delete(int port, String endpoint) throws Exception {
        URL url = new URL(BASE_URL + ":" + port + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        try {
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");
            
            int responseCode = conn.getResponseCode();
            
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }
            
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();
            
            JSONObject result = new JSONObject();
            result.put("statusCode", responseCode);
            
            if (response.length() > 0) {
                try {
                    result.put("data", new JSONObject(response.toString()));
                } catch (Exception e) {
                    result.put("data", response.toString());
                }
            }
            
            return result;
            
        } finally {
            conn.disconnect();
        }
    }
}

