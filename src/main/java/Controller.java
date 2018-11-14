import java.util.ArrayList;

public class Controller {

    private PlaceGUI gui;
    private PlaceDB db;

    public static void main(String[] args) {
        new Controller().startApp();
    }

    private void startApp() {
        db = new PlaceDB();

        ArrayList<Place> allData = db.fetchAllRecords();

        gui = new PlaceGUI(this);
        gui.setListData(allData);
    }
    ArrayList<Place> getAllData(){

        return db.fetchAllRecords();
    }
    String addPlaceToDatabase(Place place){
        return db.addRecord(place);
    }
    void deletePlace(Place place){
        db.delete(place);
    }
}
