import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {
        int port = 8080;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        System.out.println("Starting Financial Engine API Server on port " + port + "...");
        server.createContext("/status", new StatusHandler());

        server.start();
        System.out.println("Server is online! Press Ctrl+C in your terminal to turn it off.");
    }

    static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String jsonResponse = "{\"status\": \"UP\", \"system\": \"Financial Approval Governance System API\"}";
            
            exchange.sendResponseHeaders(200, jsonResponse.length());
            
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            
            os.close();
        }
    }
}