package com.example.rustaying;



public class Service {

    private long requestID;
    private String requestType;
    private String description;
    private String status;
    private String hourValue;
    private String minuteValue;
    private String ampmValue;
    private String luggageValue;
    private String requestedTimeValet;
    private String requestedTimeBellboy;
    private String requestDate;
    private String inputs;
    private String bathroom;
    private String electronic;
    private String lighting;
    private String checkboxes;
    private String towels;
    private String soap;
    private String bedsheets;
    private String cleaningservice;
    private String requestedTimeMaintenance;
    private String requestedTimeRoomService;
    private String fromWhere;
    private String startingStreet;
    private String startingCityStateZip;
    private String destinationStreet;
    private String destinationCityStateZip;

    private String serviceID;

    private long id;

    private String numberTraveling;
    private String temp1;
    private String temp2;

    private String requestedTimeFoodService;
    private String gardenSalad;
    private String tomatoSoup;
    private String friedChicken;
    private String cheesePizza;
    private String spaghetti;
    private String macAndCheese;
    private String vanillaIceCream;
    private String fruitCake;
    private String coke;
    private String sprite;
    private String appleJuice;
    private String foodPrice;

    private Integer a1;
    private Integer a2;
    private Integer m1;
    private Integer m2;
    private Integer m3;
    private Integer m4;
    private Integer d1;
    private Integer d2;
    private Integer dr1;
    private Integer dr2;
    private Integer dr3;



    //live price calculation
    public Service(Integer a1, Integer a2, Integer m1, Integer m2, Integer m3, Integer m4,
                   Integer d1, Integer d2, Integer dr1, Integer dr2, Integer dr3) {
        this.a1 = a1;
        this.a2 = a2;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.d1 = d1;
        this.d2 = d2;
        this.dr1 = dr1;
        this.dr2 = dr2;
        this.dr3 = dr3;
    }

    public Integer getA1() {
        return a1;
    }

    public void setA1(Integer a1) {
        this.a1 = a1;
    }

    public Integer getA2() {
        return a2;
    }

    public void setA2(Integer a2) {
        this.a2 = a2;
    }

    public Integer getM1() {
        return m1;
    }

    public void setM1(Integer m1) {
        this.m1 = m1;
    }

    public Integer getM2() {
        return m2;
    }

    public void setM2(Integer m2) {
        this.m2 = m2;
    }

    public Integer getM3() {
        return m3;
    }

    public void setM3(Integer m3) {
        this.m3 = m3;
    }

    public Integer getM4() {
        return m4;
    }

    public void setM4(Integer m4) {
        this.m4 = m4;
    }

    public Integer getD1() {
        return d1;
    }

    public void setD1(Integer d1) {
        this.d1 = d1;
    }

    public Integer getD2() {
        return d2;
    }

    public void setD2(Integer d2) {
        this.d2 = d2;
    }

    public Integer getDr1() {
        return dr1;
    }

    public void setDr1(Integer dr1) {
        this.dr1 = dr1;
    }

    public Integer getDr2() {
        return dr2;
    }

    public void setDr2(Integer dr2) {
        this.dr2 = dr2;
    }

    public Integer getDr3() {
        return dr3;
    }

    public void setDr3(Integer dr3) {
        this.dr3 = dr3;}


    public Service(String requestType, String requestDate, String luggageValue,
                   String requestedTimeBellboy, String fromWhere, String status, long id) {
        this.requestType = requestType;
        this.requestDate = requestDate;
        this.luggageValue = luggageValue;
        this.requestedTimeBellboy = requestedTimeBellboy;
        this.fromWhere = fromWhere;
        this.status = status;
        this.id =id;


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
    public String getStartingStreet() {
        return startingStreet;
    }

    public void setStartingStreet(String startingStreet) {
        this.startingStreet = startingStreet;
    }

    public String getStartingCityStateZip() {
        return startingCityStateZip;
    }

    public void setStartingCityStateZip(String startingCityStateZip) {
        this.startingCityStateZip = startingCityStateZip;
    }

    public String getDestinationStreet() {
        return destinationStreet;
    }

    public void setDestinationStreet(String destinationStreet) {
        this.destinationStreet = destinationStreet;
    }

    public String getDestinationCityStateZip() {
        return destinationCityStateZip;
    }

    public void setDestinationCityStateZip(String destinationCityStateZip) {
        this.destinationCityStateZip = destinationCityStateZip;
    }

    public Service(String requestType, String requestedTimeValet, String requestDate, String answer1, String answer2,
                   String answer3, String answer4, String numberTraveling, String status,
                   String temp1,
                   String temp2, long id) {
        this.requestType = requestType;
        this.requestedTimeValet = requestedTimeValet;
        this.requestDate = requestDate;
        this.startingStreet = answer1;
        this.destinationStreet = answer2;
        this.startingCityStateZip = answer3;
        this.destinationCityStateZip = answer4;
        this.numberTraveling=numberTraveling;
        this.status = status;
        this.id=id;
    }

    public Service(String requestType, String requestDate, String requestedTimeMaintenance, String inputs, String bathroom,
                   String electronic, String lighting, String checkboxes, String status, long id) {
        this.requestType = requestType;
        this.requestDate = requestDate;
        this.inputs = inputs;
        this.bathroom = bathroom;
        this.electronic = electronic;
        this.lighting = lighting;
        this.checkboxes = checkboxes;
        this.requestedTimeMaintenance = requestedTimeMaintenance;
        this.status = status;
        this.id=id;
    }

    public Service(String requestType, String requestDate, String requestedTimeRoomService, String inputs,
                   String towels, String soap, String bedsheets, String cleaningservice,
                   String checkboxes, String status, long id) {
        this.requestType = requestType;
        this.requestDate = requestDate;
        this.inputs = inputs;
        this.checkboxes = checkboxes;
        this.towels = towels;
        this.soap = soap;
        this.bedsheets = bedsheets;
        this.cleaningservice = cleaningservice;
        this.requestedTimeRoomService = requestedTimeRoomService;
        this.status = status;
        this.id=id;
    }

    //foodservice


    public Service(String requestType,
                   String requestDate,
                   String requestedTimeFoodService,
                   String inputs,
                   String gardenSalad,
                   String tomatoSoup,
                   String friedChicken,
                   String cheesePizza,
                   String spaghetti,
                   String macAndCheese,
                   String vanillaIceCream,
                   String fruitCake,
                   String coke,
                   String sprite,
                   String appleJuice,
                   String foodPrice,
                   String status, long id) {
        this.requestType = requestType;
        this.requestDate = requestDate;
        this.inputs = inputs;
        this.requestedTimeFoodService = requestedTimeFoodService;
        this.gardenSalad = gardenSalad;
        this.tomatoSoup = tomatoSoup;
        this.friedChicken = friedChicken;
        this.cheesePizza = cheesePizza;
        this.spaghetti = spaghetti;
        this.macAndCheese = macAndCheese;
        this.vanillaIceCream = vanillaIceCream;
        this.fruitCake = fruitCake;
        this.coke = coke;
        this.sprite = sprite;
        this.appleJuice = appleJuice;
        this.foodPrice = foodPrice;
        this.status = status;
        this.id =id;
    }

    public Service(String requestType, String status){
        this.requestType = requestType;
        this.status=status;
    }


    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getNumberTraveling() {
        return numberTraveling;
    }

    public void setNumberTraveling(String numberTraveling) {
        this.numberTraveling = numberTraveling;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getRequestedTimeFoodService() {
        return requestedTimeFoodService;
    }

    public void setRequestedTimeFoodService(String requestedTimeFoodService) {
        this.requestedTimeFoodService = requestedTimeFoodService;
    }

    public String getGardenSalad() {
        return gardenSalad;
    }

    public void setGardenSalad(String gardenSalad) {
        this.gardenSalad = gardenSalad;
    }

    public String getTomatoSoup() {
        return tomatoSoup;
    }

    public void setTomatoSoup(String tomatoSoup) {
        this.tomatoSoup = tomatoSoup;
    }

    public String getFriedChicken() {
        return friedChicken;
    }

    public void setFriedChicken(String friedChicken) {
        this.friedChicken = friedChicken;
    }

    public String getCheesePizza() {
        return cheesePizza;
    }

    public void setCheesePizza(String cheesePizza) {
        this.cheesePizza = cheesePizza;
    }

    public String getSpaghetti() {
        return spaghetti;
    }

    public void setSpaghetti(String spaghetti) {
        this.spaghetti = spaghetti;
    }

    public String getMacAndCheese() {
        return macAndCheese;
    }

    public void setMacAndCheese(String macAndCheese) {
        this.macAndCheese = macAndCheese;
    }

    public String getVanillaIceCream() {
        return vanillaIceCream;
    }

    public void setVanillaIceCream(String vanillaIceCream) {
        this.vanillaIceCream = vanillaIceCream;
    }

    public String getFruitCake() {
        return fruitCake;
    }

    public void setFruitCake(String fruitCake) {
        this.fruitCake = fruitCake;
    }

    public String getCoke() {
        return coke;
    }

    public void setCoke(String coke) {
        this.coke = coke;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getAppleJuice() {
        return appleJuice;
    }

    public void setAppleJuice(String appleJuice) {
        this.appleJuice = appleJuice;}

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHourValue() {
        return hourValue;
    }

    public void setHourValue(String hourValue) {
        this.hourValue = hourValue;
    }

    public String getMinuteValue() {
        return minuteValue;
    }

    public void setMinuteValue(String minuteValue) {
        this.minuteValue = minuteValue;
    }

    public String getAmpmValue() {
        return ampmValue;
    }

    public void setAmpmValue(String ampmValue) {
        this.ampmValue = ampmValue;
    }

    public String getLuggageValue() {
        return luggageValue;
    }

    public void setLuggageValue(String luggageValue) {
        this.luggageValue = luggageValue;
    }

    public String getRequestedTimeValet() {
        return requestedTimeValet;
    }

    public void setRequestedTimeValet(String requestedTimeValet) {
        this.requestedTimeValet = requestedTimeValet;
    }

    public String getRequestedTimeBellboy() {
        return requestedTimeBellboy;
    }

    public void setRequestedTimeBellboy(String requestedTimeBellboy) {
        this.requestedTimeBellboy = requestedTimeBellboy;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getElectronic() {
        return electronic;
    }

    public void setElectronic(String electronic) {
        this.electronic = electronic;
    }

    public String getLighting() {
        return lighting;
    }

    public void setLighting(String lighting) {
        this.lighting = lighting;
    }

    public String getCheckboxes() {
        return checkboxes;
    }

    public void setCheckboxes(String checkboxes) {
        this.checkboxes = checkboxes;
    }

    public String getTowels() {
        return towels;
    }

    public void setTowels(String towels) {
        this.towels = towels;
    }

    public String getSoap() {
        return soap;
    }

    public void setSoap(String soap) {
        this.soap = soap;
    }

    public String getBedsheets() {
        return bedsheets;
    }

    public void setBedsheets(String bedsheets) {
        this.bedsheets = bedsheets;
    }

    public String getCleaningservice() {
        return cleaningservice;
    }

    public void setCleaningservice(String cleaningservice) {
        this.cleaningservice = cleaningservice;
    }

    public String getRequestedTimeMaintenance() {
        return requestedTimeMaintenance;
    }

    public void setRequestedTimeMaintenance(String requestedTimeMaintenance) {
        this.requestedTimeMaintenance = requestedTimeMaintenance;
    }

    public String getRequestedTimeRoomService() {
        return requestedTimeRoomService;
    }

    public void setRequestedTimeRoomService(String requestedTimeRoomService) {
        this.requestedTimeRoomService = requestedTimeRoomService;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public Service() {
    }
}