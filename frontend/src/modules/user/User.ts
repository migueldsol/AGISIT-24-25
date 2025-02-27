export default class User {
    id : Number;
    username !: String;
    password : String;
    email !: String;
    phoneNumber !: String;
    address !: String;
    city !: String;
    country !: String;
    postalCode !: String;

    constructor(jsonObj?: User) {
        if(jsonObj) {
            this.id = jsonObj.id;
            this.username = jsonObj.username;
            this.password = jsonObj.password;
            this.email = jsonObj.email;
            this.phoneNumber = jsonObj.phoneNumber;
            this.address = jsonObj.address;
            this.city = jsonObj.city;
            this.country = jsonObj.country;
            this.postalCode = jsonObj.postalCode;
        }
    }
}