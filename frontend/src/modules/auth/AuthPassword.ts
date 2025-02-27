export default class AuthPassword {
    password !: String;
    username !: String;

    constructor(jsonObj?: AuthPassword) {
        if(jsonObj) {
            this.password = jsonObj.password;
            this.username = jsonObj.username;
        }
    }
}