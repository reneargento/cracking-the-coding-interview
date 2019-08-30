package chapter7_object_oriented_design;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Rene Argento on 14/08/19.
 */
public class Exercise4_ParkingLot {

    public enum VehicleSize {
        SMALL, REGULAR, LARGE
    }

    public abstract class Vehicle {
        private String id;
        private String model;
        private String color;
        private String licensePlate;
        protected int numberOfParkingSpotsNeeded;
        protected List<ParkingSpot> parkingSpotsUsed;
        protected VehicleSize vehicleSize;

        public Vehicle(String id, String model, String color, String licensePlate, int numberOfParkingSpotsNeeded) {
            this.id = id;
            this.model = model;
            this.color = color;
            this.licensePlate = licensePlate;
            this.numberOfParkingSpotsNeeded = numberOfParkingSpotsNeeded;
            parkingSpotsUsed = new ArrayList<>();
        }

        // Park vehicle in this spot (among others, potentially)
        public void parkInSpot(ParkingSpot parkingSpot) {
            parkingSpotsUsed.add(parkingSpot);
        }

        public void removeVehicle() {
            parkingSpotsUsed.clear();
        }

        // Checks if the spot is big enough for the vehicle (and is available). This compares the size only.
        // It does not check if it has enough spots.
        public abstract boolean canFitInSpot(ParkingSpot parkingSpot);

        public String getId() {
            return id;
        }

        public String getModel() {
            return model;
        }

        public String getColor() {
            return color;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public int getNumberOfParkingSpotsNeeded() {
            return numberOfParkingSpotsNeeded;
        }

        public List<ParkingSpot> getParkingSpotsUsed() {
            return parkingSpotsUsed;
        }

        public VehicleSize getVehicleSize() {
            return vehicleSize;
        }
    }

    public class Motorcycle extends Vehicle {
        private final static int PARKING_SPOTS_NEEDED = 1;

        public Motorcycle(String id, String model, String color, String licensePlate) {
            super(id, model, color, licensePlate, PARKING_SPOTS_NEEDED);
            vehicleSize = VehicleSize.SMALL;
        }

        @Override
        public boolean canFitInSpot(ParkingSpot parkingSpot) {
            return parkingSpot.isAvailable(); // Motorcycles can fit anywhere
        }
    }

    public class Car extends Vehicle {
        private final static int PARKING_SPOTS_NEEDED = 1;

        public Car(String id, String model, String color, String licensePlate) {
            super(id, model, color, licensePlate, PARKING_SPOTS_NEEDED);
            vehicleSize = VehicleSize.REGULAR;
        }

        @Override
        public boolean canFitInSpot(ParkingSpot parkingSpot) {
            return parkingSpot.isAvailable() &&
                    (parkingSpot.getVehicleSize() == VehicleSize.REGULAR
                            || parkingSpot.getVehicleSize() == VehicleSize.LARGE);
        }
    }

    public class Bus extends Vehicle {
        private final static int PARKING_SPOTS_NEEDED = 5;

        public Bus(String id, String model, String color, String licensePlate) {
            super(id, model, color, licensePlate, PARKING_SPOTS_NEEDED);
            vehicleSize = VehicleSize.LARGE;
        }

        @Override
        public boolean canFitInSpot(ParkingSpot parkingSpot) {
            return parkingSpot.isAvailable() && parkingSpot.getVehicleSize() == VehicleSize.LARGE;
        }
    }

    public class Driver {
        private String name;
        private String drivingLicenseNumber;
        private Vehicle vehicle;

        public Driver(String name, String drivingLicenseNumber, Vehicle vehicle) {
            this.name = name;
            this.drivingLicenseNumber = drivingLicenseNumber;
            this.vehicle = vehicle;
        }

        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }

        public String getName() {
            return name;
        }

        public String getDrivingLicenseNumber() {
            return drivingLicenseNumber;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }
    }

    public class ParkingSpot {
        private VehicleSize vehicleSize;
        private Vehicle vehicle;
        private Level level;
        private int row;
        private int spotNumber;

        public ParkingSpot(VehicleSize vehicleSize, Level level, int row, int spotNumber) {
            this.vehicleSize = vehicleSize;
            this.level = level;
            this.row = row;
            this.spotNumber = spotNumber;
        }

        public boolean canFitVehicle(Vehicle vehicle) {
            return vehicle.canFitInSpot(this);
        }

        public boolean parkVehicle(Vehicle vehicle) {
            if (canFitVehicle(vehicle)) {
                this.vehicle = vehicle;
                return true;
            }
            return false;
        }

        public void removeVehicle() {
            this.vehicle = null;
        }

        public boolean isAvailable() {
            return vehicle == null;
        }

        public VehicleSize getVehicleSize() {
            return vehicleSize;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public Level getLevel() {
            return level;
        }

        public int getRow() {
            return row;
        }

        public int getSpotNumber() {
            return spotNumber;
        }
    }

    public class Level {
        private int floor;
        private ParkingSpot[] parkingSpots;
        private List<Vehicle> vehiclesParked;
        private static final int SPOTS_PER_ROW = 10;

        public Level(int floor, int capacity) {
            this.floor = floor;
            parkingSpots = new ParkingSpot[capacity];
            vehiclesParked = new ArrayList<>();

            int row = 0;
            for (int spotNumber = 0; spotNumber < capacity; spotNumber++) {
                if (spotNumber != 0 && spotNumber % SPOTS_PER_ROW == 0) {
                    row++;
                }

                VehicleSize vehicleSize = getRandomVehicleSize();
                parkingSpots[spotNumber] = new ParkingSpot(vehicleSize, this, row, spotNumber);
            }
        }

        public boolean parkVehicle(Driver driver) {
            if (isFull()) {
                return false;
            }
            Vehicle vehicle = driver.getVehicle();

            int parkingSpotIndex = findAvailableSpots(vehicle);
            if (parkingSpotIndex == -1) {
                return false;
            }

            boolean parked = parkStartingAtSpot(parkingSpotIndex, vehicle);
            if (parked) {
                vehiclesParked.add(vehicle);
            }
            return parked;
        }

        // Find spots to park this vehicle. Return index of first spot or -1 on failure.
        private int findAvailableSpots(Vehicle vehicle) {
            int index = -1;

            for (int i = 0; i < parkingSpots.length; i++) {
                if (parkingSpots[i].canFitVehicle(vehicle)) {
                    int spotsNeeded = vehicle.getNumberOfParkingSpotsNeeded();
                    int spotsFound = 1;

                    for (int parkingSpotIndex = i + 1; parkingSpotIndex < parkingSpots.length
                            && spotsNeeded != spotsFound; parkingSpotIndex++) {
                        if (parkingSpots[parkingSpotIndex].getRow() == parkingSpots[i].getRow() &&
                                parkingSpots[parkingSpotIndex].canFitVehicle(vehicle)) {
                            spotsFound++;
                        } else {
                            break;
                        }
                    }

                    if (spotsNeeded == spotsFound) {
                        index = i;
                        break;
                    }
                }
            }

            return index;
        }

        private boolean parkStartingAtSpot(int spotIndex, Vehicle vehicle) {
            int spotsNeeded = vehicle.getNumberOfParkingSpotsNeeded();
            int lastSpotIndex = spotIndex + spotsNeeded - 1;
            List<ParkingSpot> parkingSpotsToPark = new ArrayList<>();

            for (int parkingSpotIndex = spotIndex; parkingSpotIndex <= lastSpotIndex && parkingSpotIndex < parkingSpots.length; parkingSpotIndex++) {
                if (parkingSpots[parkingSpotIndex].canFitVehicle(vehicle)) {
                    parkingSpotsToPark.add(parkingSpots[parkingSpotIndex]);
                } else {
                    return false;
                }
            }

            for (ParkingSpot parkingSpot : parkingSpotsToPark) {
                parkingSpot.parkVehicle(vehicle);
                vehicle.parkInSpot(parkingSpot);
            }
            return true;
        }

        public boolean removeVehicle(Driver driver) {
            Vehicle vehicle = driver.getVehicle();

            if (!vehiclesParked.contains(vehicle)) {
                return false;
            }

            for (ParkingSpot parkingSpot : parkingSpots) {
                if (parkingSpot.getVehicle() == vehicle) {
                    parkingSpot.removeVehicle();
                }
            }
            vehicle.removeVehicle();

            vehiclesParked.remove(vehicle);
            return true;
        }

        public int availableSpots() {
            return parkingSpots.length - vehiclesParked.size();
        }

        public boolean isFull() {
            return availableSpots() == 0;
        }

        public int getFloor() {
            return floor;
        }

        public List<Vehicle> getVehiclesParked() {
            return vehiclesParked;
        }

        private VehicleSize getRandomVehicleSize() {
            int random = new Random().nextInt(3);
            if (random == 0) {
                return VehicleSize.SMALL;
            }
            if (random == 1) {
                return VehicleSize.REGULAR;
            }
            return VehicleSize.LARGE;
        }
    }

    public class ParkingLot {
        private Level[] levels;

        public ParkingLot(int levelsNumber) {
            levels = new Level[levelsNumber];
        }

        public boolean parkVehicle(Driver driver) {
            for (Level level : levels) {
                if (level.parkVehicle(driver)) {
                    return true;
                }
            }
            return false;
        }

        public boolean removeVehicle(Driver driver) {
            for (Level level : levels) {
                if (level.removeVehicle(driver)) {
                    return true;
                }
            }
            return false;
        }

        public Level[] getLevels() {
            return levels;
        }
    }

}
