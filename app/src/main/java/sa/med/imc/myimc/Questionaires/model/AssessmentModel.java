package sa.med.imc.myimc.Questionaires.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssessmentModel implements Serializable{

        @SerializedName("DateFinished")
        @Expose
        private String dateFinished;
        @SerializedName("Engine")
        @Expose
        private String engine;
        @SerializedName("Items")
        @Expose
        private List<Item> items = null;

        public String getDateFinished() {
            return dateFinished;
        }

        public void setDateFinished(String dateFinished) {
            this.dateFinished = dateFinished;
        }

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

    public class Element implements Serializable{

        @SerializedName("ElementOID")
        @Expose
        private String elementOID;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("ElementOrder")
        @Expose
        private String elementOrder;
        @SerializedName("Map")
        @Expose
        private List<Map> map = null;

        public String getElementOID() {
            return elementOID;
        }

        public void setElementOID(String elementOID) {
            this.elementOID = elementOID;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getElementOrder() {
            return elementOrder;
        }

        public void setElementOrder(String elementOrder) {
            this.elementOrder = elementOrder;
        }

        public List<Map> getMap() {
            return map;
        }

        public void setMap(List<Map> map) {
            this.map = map;
        }

    }

    public class Item implements Serializable {

        @SerializedName("FormItemOID")
        @Expose
        private String formItemOID;
        @SerializedName("ItemResponseOID")
        @Expose
        private String itemResponseOID;
        @SerializedName("Response")
        @Expose
        private String response;
        @SerializedName("ID")
        @Expose
        private String iD;
        @SerializedName("Order")
        @Expose
        private String order;
        @SerializedName("ItemType")
        @Expose
        private String itemType;
        @SerializedName("Elements")
        @Expose
        private List<Element> elements = null;



        public String getFormItemOID() {
            return formItemOID;
        }

        public void setFormItemOID(String formItemOID) {
            this.formItemOID = formItemOID;
        }

        public String getItemResponseOID() {
            return itemResponseOID;
        }

        public void setItemResponseOID(String itemResponseOID) {
            this.itemResponseOID = itemResponseOID;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getID() {
            return iD;
        }

        public void setID(String iD) {
            this.iD = iD;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public List<Element> getElements() {
            return elements;
        }

        public void setElements(List<Element> elements) {
            this.elements = elements;
        }

    }


    public class Map {

        @SerializedName("ElementOID")
        @Expose
        private String elementOID;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("FormItemOID")
        @Expose
        private String formItemOID;
        @SerializedName("ItemResponseOID")
        @Expose
        private String itemResponseOID;
        @SerializedName("Value")
        @Expose
        private String value;
        @SerializedName("Position")
        @Expose
        private String position;

        public String getElementOID() {
            return elementOID;
        }

        public void setElementOID(String elementOID) {
            this.elementOID = elementOID;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFormItemOID() {
            return formItemOID;
        }

        public void setFormItemOID(String formItemOID) {
            this.formItemOID = formItemOID;
        }

        public String getItemResponseOID() {
            return itemResponseOID;
        }

        public void setItemResponseOID(String itemResponseOID) {
            this.itemResponseOID = itemResponseOID;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

    }
}
