package servers.httpServer;
import com.google.gson.JsonObject;
import hotelapp.ThreadSafeHotelHandler;
import java.io.PrintWriter;

import static servers.httpServer.HttpRequest.*;

public class HotelHandler implements HttpHandler{

    ThreadSafeHotelHandler threadSafeHotelHandler;

    @Override
    public void processRequest(HttpRequest request, PrintWriter writer) {
        if(request.method.equals("GET")){
            getHotelInfo(request, writer);
            return;
        }
        send405Response("method", writer);
    }

    private void getHotelInfo(HttpRequest request, PrintWriter writer) {
        JsonObject jsonObject = new JsonObject();

        if (request.params.containsKey("hotelId")) {
            String hotelId = request.params.get("hotelId");
            JsonObject jsonResponse = threadSafeHotelHandler.getHotelInfoJson(hotelId);
            if (jsonResponse != null) {
                HttpRequest.sendSuccessJsonResponse(jsonResponse, writer);
                return;
            }
        }
            send404JsonResponse("hotelId", writer);
    }

    @Override
    public void setAttribute(Object data) {
        this.threadSafeHotelHandler = (ThreadSafeHotelHandler) data;
    }
}
