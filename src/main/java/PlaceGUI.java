import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlaceGUI extends JFrame{
    private JTextField placeNameText;
    private JTextField elevation;
    private JButton addButton;
    private JList placeList;
    private JButton deleteButton;
    private JPanel mainPanel;

    private Controller controller;

    private DefaultListModel<Place> allPlacesListModel;

    PlaceGUI(Controller controller){

        this.controller=controller;

        allPlacesListModel = new DefaultListModel<Place>();
        placeList.setModel(allPlacesListModel);

        addListeners();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);




    }
    void setListData(ArrayList<Place> data){
        allPlacesListModel.clear();

        if (data!=null){
            for(Place place : data){
                allPlacesListModel.addElement(place);
            }
        }
    }
    private void errorDialog(String msg){
        JOptionPane.showMessageDialog(PlaceGUI.this,msg,"Error",JOptionPane.ERROR_MESSAGE);
    }
    private void addListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String place = placeNameText.getText();

                if (place.isEmpty()){
                    errorDialog("Enter a place name");
                    return;
                }
                double elev;

                try{
                    elev = Double.parseDouble(elevation.getText());
                }catch (NumberFormatException nfe){
                    errorDialog("Enter a number for elevation");
                    return;
                }
                Place placeRecord = new Place(place,elev);
                String result = controller.addPlaceToDatabase(placeRecord);

                if (result.equals(PlaceDB.OK)){
                    placeNameText.setText("");
                    elevation.setText("");

                    ArrayList<Place> allData = controller.getAllData();
                    setListData(allData);
                }else{
                    errorDialog(result);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Place place = (Place) placeList.getSelectedValue();
                if (place==null){
                    JOptionPane.showMessageDialog(PlaceGUI.this,"Please select a place to delete");
                }else{
                    controller.deletePlace(place);
                    ArrayList<Place> places = controller.getAllData();
                    setListData(places);
                }

            }
        });
    }
}
