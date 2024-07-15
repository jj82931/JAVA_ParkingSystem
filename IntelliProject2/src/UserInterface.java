import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface {

    static JFrame frame; // JFrame 타입으로 선언
    static JPanel parkPanel; // Declare parkPanel at the class level
    static GridBagConstraints gbc; // GridBagConstraints 전역 변수로 선언
    static JButton[] parkingSpots = new JButton[12]; // 3x4 주차 슬롯 버튼 배열


    public static void main(String[] args) {

        frame = new JFrame("Parking Spot"); // JFrame 객체 생성 및 title 지정.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 올바른 종료 동작 설정
        frame.setSize(1280, 720); // Frame 사이즈는 HD 사이즈로 설정
        frame.setLayout(new GridBagLayout()); // 레이아웃 매니저를 GridBagLayout으로 설정

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;


        JPanel buttonPanel = ButtonPanel();
        constraints.gridx = 0; // 첫 번째 열
        constraints.gridy = 0;
        constraints.weightx = 0.1; // 버튼 패널의 너비 비율
        frame.add(buttonPanel, constraints);

        initializeParkPanel(); // Initialize the parking panel
        constraints.gridx = 1; // 두 번째 열
        constraints.weightx = 0.9; // 주차 패널의 너비 비율
        frame.add(parkPanel, constraints);


        // 프레임 가시화
        frame.setVisible(true);
    }

    private static JPanel ButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridwidth = GridBagConstraints.REMAINDER; // 현재 행의 나머지 공간을 채우도록 설정
        gc.fill = GridBagConstraints.BOTH;           // 컴포넌트를 수평과 수직으로 확장
        gc.weightx = 1;                             // 가로 방향으로 여유 공간을 모두 채우도록 설정
        gc.weighty = 1;                             // 세로 방향으로 여유 공간을 모두 채우도록 설정

        JButton buttonAddParking = getButtonAddParking();
        panel.add(buttonAddParking, gc);
        JButton buttonDeleteParking = getButtonDeleteParking();
        panel.add(buttonDeleteParking, gc);
        JButton buttonListParking = getButtonListParking();
        panel.add(buttonListParking, gc);
        JButton buttonParkCar = getButtonParkCar();
        panel.add(buttonParkCar, gc);

        JButton buttonFindReg = new JButton("5. Find car by registration number");
        panel.add(buttonFindReg, gc);
        JButton buttonRemoveReg = new JButton("6. Remove car by registration number");
        panel.add(buttonRemoveReg, gc);
        JButton buttonFindMake = new JButton("7. Find cars by make");
        panel.add(buttonFindMake, gc);
        JButton buttonReset = new JButton("8. Reset car park");
        panel.add(buttonReset, gc);
        JButton buttonExit = new JButton("9. Exit");
        panel.add(buttonExit, gc);

        return panel;
    }

    private static JButton getButtonAddParking() {
        JButton buttonAddParking = new JButton("1. Add a parking spot");
        buttonAddParking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotID = JOptionPane.showInputDialog(frame, "Enter Slot ID");
                String msg = CarPark.addSlot(slotID);
                if(msg.equals("Parking slot added successfully.")){
                    addSlotPanel(slotID);

                }
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonAddParking;
    }

    private static JButton getButtonDeleteParking() {
        JButton buttonDeleteParking = new JButton("2. Delete a parking spot");
        buttonDeleteParking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotID = JOptionPane.showInputDialog(frame, "Enter Slot ID: ");
                String msg = CarPark.deleteSlot(slotID);
                if(msg.equals("Parking slot deleted successfully.")){
                    deleteSlotPanel(slotID);
                }
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonDeleteParking;
    }

    private static JButton getButtonListParking() {
        JButton buttonListParking = new JButton("3. List all parking spots");
        buttonListParking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = CarPark.showSlots();
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonListParking;
    }

    private static JButton getButtonParkCar() {
        JButton buttonParkCar = new JButton("4. Park a car");
        buttonParkCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotID = JOptionPane.showInputDialog(frame, "What's the slot ID?");
                String carReg = JOptionPane.showInputDialog(frame, "What's the car registration number?");
                String carMake = JOptionPane.showInputDialog(frame, "What's the car make?");
                String carModel = JOptionPane.showInputDialog(frame, "What's the car model?");
                String carYear = JOptionPane.showInputDialog(frame, "What's the car Year?");

                if(slotID == null || carReg == null || carMake == null || carModel == null || carYear == null ||
                slotID.isEmpty() || carReg.isEmpty() || carMake.isEmpty()  || carModel.isEmpty()  || carYear.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "You canceled or input value was empty. Back to main menu.");
                }else{
                    String msg = CarPark.parkCar(slotID, carReg, carMake, carModel,Integer.parseInt(carYear));
                    JOptionPane.showMessageDialog(frame, msg);
                    if(msg.contains("The car parked successfully.")){
                        carParkPanel(slotID, carReg, carMake, carModel, carYear);
                    }
                }
            }
        });

        return buttonParkCar;
    }


    private static void initializeParkPanel() {
        parkPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 1;
        Dimension buttonSize = new Dimension(100, 100); // 버튼크기를 미리 지정

        for (int i = 0; i < 12; i++) {
            parkingSpots[i] = new JButton();  // 주차 슬롯 버튼 생성
            parkingSpots[i].setEnabled(false); // 초기에는 비활성화
            parkingSpots[i].setBackground(Color.white);
            parkingSpots[i].setBorder(null);
            parkingSpots[i].setPreferredSize(buttonSize);
            gbc.gridx = i % 4;
            gbc.gridy = i / 4;
            parkPanel.add(parkingSpots[i], gbc);
        }
    }

    private static void addSlotPanel(String slotID) {

        for (JButton parkingSpot : parkingSpots) {
            if (!parkingSpot.isEnabled()) {  // 비활성화 상태의 버튼 찾기
                parkingSpot.setText(slotID);  // 슬롯 ID 설정
                parkingSpot.setEnabled(true); // 버튼 활성화
                parkingSpot.setVerticalAlignment(SwingConstants.TOP); // 텍스트 위치 버튼에서 위로 배치
                parkingSpot.setBackground(Color.lightGray); // 버튼 색상 변경
                break;  // 빈 슬롯을 찾았으므로 반복 종료
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }

    private static void deleteSlotPanel(String slotID) {

        for (JButton parkingSpot : parkingSpots) {
            if (parkingSpot.getText().equals(slotID)) {
                parkingSpot.setText("");            // 슬롯 ID 제거
                parkingSpot.setEnabled(false);     // 버튼 비활성화
                parkingSpot.setBackground(Color.white); // 버튼 색상을 기본 색으로 변경
                break;  // 일치하는 슬롯을 찾았으므로 반복 종료
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }

    private static void carParkPanel(String slotID, String carReg, String carMake, String carModel, String carYear){
        for (JButton parkingSpot : parkingSpots) {
            if (parkingSpot.getText().equals(slotID)){
                String setButtonText = "<html>" + slotID +
                        "<br/><br/>Registration: " + carReg +
                        "<br/>make: " + carMake +
                        "<br/>model: " + carModel +
                        "<br/>Year: " + carYear +
                        "</html>";

                parkingSpot.setText(setButtonText);
                parkingSpot.setBackground(Color.GREEN);
                break;
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }
}
