package college.project.MapServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceMatrixService {

    @Autowired
    private DistanceMatrixClient distanceMatrixClient;

    public String getDistanceOfLocations(String origin,String destination){

        DistanceMatrixResponse response=
                distanceMatrixClient.getDistance(origin,destination);

        if(response!=null && response.getRows()!=null && !response.getRows().isEmpty()){

            DistanceMatrixResponse.Row row=response.getRows().get(0);
            if(!row.getElements().isEmpty()){
                DistanceMatrixResponse.Element element=row.getElements().get(0);

                if(element.getDistance()!=null){
                    System.out.println("distance matrix calling...");
                    return element.getDistance().getText();
                }
            }
        }
        return "N/A";

    }

}
