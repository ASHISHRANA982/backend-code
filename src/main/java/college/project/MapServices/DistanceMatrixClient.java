package college.project.MapServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;




@Component
public class DistanceMatrixClient {

    @Value("${distance.matrix.api.url}")
    private String baseUrl;

    @Value("${distance.matrix.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;


    public DistanceMatrixResponse  getDistance(String origin,String destination) {

        String finalUrl = baseUrl
                .replace("ORIGIN", origin)
                .replace("DESTINATION", destination)
                .replace("KEY", apiKey);


        ResponseEntity<DistanceMatrixResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, DistanceMatrixResponse.class);


      return response.getBody();
    }
}
