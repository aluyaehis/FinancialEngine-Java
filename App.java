import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {
    public static void main(String[] args) throws IOException {
        int port = 8085;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        System.out.println("Starting Financial Engine Production API Server...");

        server.createContext("/status", new StatusHandler());
        server.createContext("/proposals", new ProposalsHandler());

        server.start();
        System.out.println("Server is online on port " + port);
    }

    static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String jsonResponse = "{\"status\": \"UP\"}";
            exchange.sendResponseHeaders(200, jsonResponse.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();
        }
    }

    static class ProposalsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String jsonResponse = "[\n";
            
            String sqlQuery = "SELECT id, description, amount, status FROM proposals";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                 ResultSet rs = stmt.executeQuery()) {
                
                boolean isFirstElement = true;

                while (rs.next()) {
                    if (!isFirstElement) {
                        jsonResponse += ",\n";
                    }
                    
                    int id = rs.getInt("id");
                    String description = rs.getString("description");
                    double amount = rs.getDouble("amount");
                    String status = rs.getString("status");

                    jsonResponse += "  {\n" +
                                    "    \"id\": " + id + ",\n" +
                                    "    \"description\": \"" + description + "\",\n" +
                                    "    \"amount\": " + amount + ",\n" +
                                    "    \"status\": \"" + status + "\"\n" +
                                    "  }";
                    
                    isFirstElement = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                jsonResponse = "{\"error\": \"Database connection breakdown: " + e.getMessage() + "\"}";
            }

            jsonResponse += "\n]";

            exchange.sendResponseHeaders(200, jsonResponse.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();
        }
    }
}






























// import com.sun.net.httpserver.HttpServer;
// import com.sun.net.httpserver.HttpHandler;
// import com.sun.net.httpserver.HttpExchange;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.net.InetSocketAddress;

// public class App {
//     public static void main(String[] args) throws IOException {
//         int port = 8085;

//         HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
//         System.out.println("Starting Financial Engine API Server on port " + port + "...");
//         server.createContext("/status", new StatusHandler());

//         server.start();
//         System.out.println("Server is online! Press Ctrl+C in your terminal to turn it off.");
//     }

//     static class StatusHandler implements HttpHandler {
//         @Override
//         public void handle(HttpExchange exchange) throws IOException {
//             String jsonResponse = "{\"status\": \"UP\", \"system\": \"Financial Approval Governance System API\"}";
            
//             exchange.sendResponseHeaders(200, jsonResponse.length());
            
//             OutputStream os = exchange.getResponseBody();
//             os.write(jsonResponse.getBytes());
            
//             os.close();
//         }
//     }
// }