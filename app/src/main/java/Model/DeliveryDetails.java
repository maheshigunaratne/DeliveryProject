package Model;

import java.util.List;



public class DeliveryDetails {
    String deliveryID, fromLocation, toLocation, packageID, userID, status, deliveryDate, deliveryAgentID;
    String price;

    public DeliveryDetails(String deliveryID, String fromLocation, String toLocation, String packageID, String userID, String status, String deliveryDate, String deliveryAgentID, String price) {
        this.deliveryID = deliveryID;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.packageID = packageID;
        this.userID = userID;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.deliveryAgentID = deliveryAgentID;
        this.price = price;
    }

    public DeliveryDetails() {
    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAgentID() {
        return deliveryAgentID;
    }

    public void setDeliveryAgentID(String deliveryAgentID) {
        this.deliveryAgentID = deliveryAgentID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
