import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The abstract class representing the blueprint of a Room in CityLodge.
 *
 * @author Harindu Kodituwakku
 */

public abstract class Room {
    String featureSummary;
    private String roomId;
    private int numBedRooms;
    private roomType _roomType;
    private roomStatus _roomStatus;
    private List<HiringRecord> hiringRecords;

    public Room(String roomId, int numBedRooms, String featureSummary, roomType _roomType, roomStatus _roomStatus) {
        this.roomId = roomId;
        this.numBedRooms = numBedRooms;
        this.featureSummary = featureSummary;
        this._roomType = _roomType;
        this._roomStatus = _roomStatus;
        hiringRecords = new ArrayList<>(10);
    }

    public List<HiringRecord> getHiringRecords() {
        return hiringRecords;
    }

    public abstract boolean rent(String customerId, DateTime rentDate, int numOfRentDays);

    public abstract boolean returnRoom(DateTime returnDate);

    public abstract boolean performMaintenance();

    public abstract boolean completeMaintenance(DateTime completionDate);

    public String toString() {

        return String.format("%s:%s:%s:%s:%d:%d", roomId, numBedRooms, _roomType, _roomStatus);
    }

    public String getDetails() {
        //Room is being rented.
        StringBuilder builder = new StringBuilder();
        List<HiringRecord> records = getHiringRecords();

        builder.append(String.format("Room ID:                   %s\n", roomId));
        builder.append(String.format("Number of bedrooms:        %s\n", numBedRooms));
        builder.append(String.format("Type:                      %s\n", _roomType));
        builder.append(String.format("Status:                    %s\n", _roomStatus));
        builder.append(String.format("Feature Summary:           %s\n", featureSummary));
        if (records.size() == 0) {
            builder.append(String.format("RENTAL RECORD          %s\n", "empty"));
        } else {

            builder.append("RENTAL RECORD\n");
            HiringRecord lastRecord = records.get(records.size() - 1);
            builder.append(lastRecord.getDetails());
            if (records.size() >= 2) {
                int i = 0;
                for (HiringRecord record : records) {
                    if (i == records.size() - 1) {
                        i++;
                        continue;
                    }
                    builder.append("------------------------------------------------\n");
                    builder.append(record.getDetails());
                    i++;
                }
            }
        }
        return builder.toString();
    }

    public DateTime calEstimatedReturnDate(DateTime rentDate, int numOfRentDays) {
        DateTime calReturnDate = new DateTime(rentDate, numOfRentDays);
        return calReturnDate;
    }

    public HashMap<String, HiringRecord> getRecordsAsMap() {
        return hiringRecords.stream().collect(HashMap::new,
                (map, record) -> map.put(record.getRecordId(), record),
                (m, u) -> {
                });
    }

    public abstract double calRentalFee(DateTime estimatesReturnDate, DateTime rentDate);

    public abstract double calLateFee(DateTime estimatesReturnDate, DateTime actualReturnDate);

    public roomType get_roomType() {
        return _roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public roomStatus get_roomStatus() {
        return _roomStatus;
    }

    public void set_roomStatus(roomStatus _roomStatus) {
        this._roomStatus = _roomStatus;
    }

    public int getNumBedRooms() {
        return numBedRooms;
    }

    public enum roomType {Standard, Suite}

    public enum roomStatus {Available, Rented, Maintenance}

}
